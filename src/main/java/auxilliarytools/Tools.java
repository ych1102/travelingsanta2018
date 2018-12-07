package auxilliarytools;

import dataprocessing.MyFile;
import entity.City;

import java.util.List;

/**
 * Created by 01378498 on 2018/12/5.
 */
public class Tools {
    public static boolean isPrime(int n){
        if (n <= 3) {
            return n > 1;
        }
        if(n%6!=1 && n%6!=5) return false;
        double n_sqrt = Math.floor(Math.sqrt((double)n));
        for(int i=5;i<=n_sqrt;i+=6)
        {
            if(n%(i)==0 | n%(i+2)==0) return false;
        }
        return true;

    }

    public static double getManhattanDistance(City city, City city1){
        double x = city.getX();
        double y = city.getY();
        double x1 = city1.getX();
        double y1 = city1.getY();
        return Math.abs(x-x1) + Math.abs(y-y1);
    }

    public static double getEuclideanDistance(City city, City city1){
        double x = city.getX();
        double y = city.getY();
        double x1 = city1.getX();
        double y1 = city1.getY();
        return Math.sqrt((x-x1)*(x-x1) + (y-y1)*(y-y1));
    }

    /**
     * 最近的city返回，带着distanceToPrevCity存有俩city之间的距离
     * @param currentCity
     * @param cityList
     * @return
     */
    public static City findNearestCity(City currentCity, List<City> cityList){
        double shortestDist = getEuclideanDistance(currentCity,cityList.get(0));
        City nearestCity = cityList.get(0);
        for (int i = 1; i < cityList.size(); i++) {
            City cityI = cityList.get(i);
            double dist = getEuclideanDistance(currentCity,cityI);
            if(dist<shortestDist){
                shortestDist = dist;
                nearestCity = cityI;
                nearestCity.setLoc(i);
            }
        }
        nearestCity.setDistanceToPrevCity(shortestDist);
        return nearestCity;
    }

    public static City findFarthestCity(City currentCity, List<City> cityList){
        double farthestDist = getEuclideanDistance(currentCity,cityList.get(0));
        City farthestCity = cityList.get(0);
        for (int i = 1; i < cityList.size(); i++) {
            City cityI = cityList.get(i);
            double dist = getEuclideanDistance(currentCity,cityI);
            if(dist>farthestDist){
                farthestDist = dist;
                farthestCity = cityI;
                farthestCity.setLoc(i);
            }
        }
        farthestCity.setDistanceToPrevCity(farthestDist);
        return farthestCity;
    }

    public static City findNearestCityConsiderPrime(City currentCity, List<City> cityList){
        double shortestDist = getManhattanDistance(currentCity,cityList.get(0));
        int index = (int)Math.floor(Math.random()*cityList.size());
        shortestDist += getManhattanDistance(cityList.get(0),cityList.get(index));
        City nearestCity = cityList.get(0);
        for (int i = 1; i < cityList.size(); i++) {
            City cityI = cityList.get(i);
            double dist = getManhattanDistance(currentCity,cityI);
            //cityList.remove(cityI);
            double secondGradientShortest = 0;
            for (int j = 0; j < Math.min(cityList.size(),100); j++) {
                if(j==i)continue;
                double secondDist = getManhattanDistance(cityI,cityList.get(j));
                if(secondDist<secondGradientShortest)secondGradientShortest = secondDist;
            }
            if(cityI.isPrimeCity()){
                dist += secondGradientShortest;
            }else {
                dist += 1.1*secondGradientShortest;
            }
            //cityList.add(i,cityI);
            if(dist<shortestDist){
                shortestDist = dist;
                nearestCity = cityI;
            }
        }
        nearestCity.setDistanceToPrevCity(shortestDist);
        return nearestCity;
    }

    public static double calculateLoopDistanceWrong(List<City> cityList){
        double distance = 0;
        int primeCount = 0;
        for (int i = 0; i < cityList.size()-1; i++) {
            City city = cityList.get(i);
            City cityNext = cityList.get(i+1);
            if(city.isPrimeCity())primeCount=10;
            if(primeCount>0){
                distance += Tools.getEuclideanDistance(city,cityNext);
            }else{
                distance += 1.1*Tools.getEuclideanDistance(city,cityNext);
            }
            primeCount--;
        }
        return distance;
    }
    public static double calculateLoopDistance(List<City> cityList){
        double distance = 0;
        for (int i = 0; i < cityList.size()-1; i++) {
            City city = cityList.get(i);
            City cityNext = cityList.get(i+1);
            if(i%10==0&&!city.isPrimeCity()){
                distance += 1.1*Tools.getEuclideanDistance(city,cityNext);
            }else{
                distance += Tools.getEuclideanDistance(city,cityNext);
            }
        }
        return distance;
    }
    public static void outputResultCSV(List<City> cityList){
        StringBuffer sb = new StringBuffer();
        sb.append("Path\n");
        for(City c:cityList){
            sb.append(c.getId()+"\n");
        }

        String fileName = "data/output1812630.csv";

        try{
            MyFile.createFileDeleteIfExist(fileName);
            MyFile.writeTxtFile(sb.toString());
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void writeToFile(StringBuffer sb, String fileName){

        try{
            MyFile.createFileDeleteIfExist(fileName);
            MyFile.writeTxtFile(sb.toString());
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void writeResultCoordinatesToFile(List<City> resultList, String fileName){
        StringBuffer sb = new StringBuffer();
        for(City city:resultList){
            sb.append(city.getX()+",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("\n");
        for(City city:resultList){
            sb.append(city.getY()+",");
        }
        sb.deleteCharAt(sb.length() - 1);
        try{
            MyFile.createFileDeleteIfExist(fileName);
            MyFile.writeTxtFile(sb.toString());
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
