package auxilliarytools;

import dataprocessing.MyFile;
import entity.City;
import entity.CityPair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 01378498 on 2018/12/5.
 */
public class Tools {

    public static long startTime = System.currentTimeMillis();

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

    public static CityPair findNearestCityPair(List<City> firstTour, List<City> secondTour){
        double shortestDist = 10000;
        CityPair cp = new CityPair();
        for (int i = 0; i < firstTour.size(); i++) {
            City firstCity = firstTour.get(i);
            City nearestToFirstCity = findNearestCity(firstCity,secondTour);
            if(nearestToFirstCity.getDistanceToPrevCity()<shortestDist){
                shortestDist = nearestToFirstCity.getDistanceToPrevCity();
                cp.setC1(firstCity);
                cp.setC2(nearestToFirstCity);
                cp.setIndex1(i);
                cp.setIndex2(nearestToFirstCity.getLoc());
                //System.out.println("tempCP:"+cp);
            }
        }
        return cp;
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

    public static double calculateSubLoopDistanceNoPrime(List<City> cityList){
        double distance = 0;
        for (int i = 0; i < cityList.size(); i++) {
            City city = cityList.get(i%cityList.size());
            City cityNext = cityList.get((i+1)%cityList.size());
            distance += Tools.getEuclideanDistance(city,cityNext);
        }
        return distance;
    }

    /**
     * 输出为kaggle要求的格式，city0为第一个，也为最后一个
     * @param cityList
     */
    public static void outputResultCSV(List<City> cityList){
        City firstCity = cityList.get(0);
        while(firstCity.getId()!=0){
            cityList.remove(0);
            cityList.add(cityList.size(),firstCity);
            firstCity = cityList.get(0);
        }

        cityList.add(cityList.get(0));
        double totalDist = calculateLoopDistance(cityList);


        StringBuffer sb = new StringBuffer();
        sb.append("Path\n");
        for(City c:cityList){
            sb.append(c.getId()+"\n");
        }

        String fileName = "data/output"+Math.round(totalDist)+".csv";

        try{
            MyFile.createFileDeleteIfExist(fileName);
            MyFile.writeTxtFile(sb.toString());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        writeResultCoordinatesToFile(cityList,"data/finalCoordinate"+Math.round(totalDist));
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
        if(resultList.size()==0)return;
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
        sb.append("\n");
        for(City city:resultList){
            sb.append(city.getId()+",");
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

    public static double[] zoneBound(List<City> cityList){
        double leftBound = 10000;
        double rightBound = 0;
        double lowerBound = 10000;
        double upBound = 0;
        for(City c:cityList){
            if(c.getX()<leftBound)leftBound = c.getX();
            if(c.getX()>rightBound)rightBound = c.getX();
            if(c.getY()<lowerBound)lowerBound = c.getY();
            if(c.getY()>upBound)upBound = c.getY();
        }
        return new double[]{leftBound,rightBound,lowerBound,upBound};
    }

    public static boolean intersect(City a, City b, City c, City d){
        if(!lineIntersectSide(a, b, c, d))
            return false;
        if(!lineIntersectSide(c, d, a, b))
            return false;

        return true;
    }

    public static boolean lineIntersectSide(City a, City b, City c, City d) {
        // A(x1, y1), B(x2, y2)的直线方程为：
        // f(x, y) =  (y - y1) * (x1 - x2) - (x - x1) * (y1 - y2) = 0

        double fC = (c.getY() - a.getY()) * (a.getX() - b.getX()) - (c.getX() - a.getX()) * (a.getY() - b.getY());
        double fD = (d.getY() - a.getY()) * (a.getX() - b.getX()) - (d.getX() - a.getX()) * (a.getY() - b.getY());

        if (fC * fD > 0)
            return false;

        return true;
    }

    public static boolean fallInFrame(City city, double leftBd, double rightBd, double lwBd, double uppBd){
        return (city.getX()>=leftBd&&city.getX()<=rightBd&&city.getY()>=lwBd&&city.getY()<=uppBd);
    }

    public static void reverseSubSection(List<City> cityList, int startPos, int endPos){
        while(startPos<endPos){
            City moveCity = cityList.get(endPos);
            moveCity.setLoc(startPos);
            cityList.remove(endPos);
            cityList.add(startPos++,moveCity);
        }
    }

    public static void startFrom0(List<City> cityList){
        City firstCity = cityList.get(0);
        while(firstCity.getId()!=0){
            cityList.remove(0);
            cityList.add(cityList.size(),firstCity);
            firstCity = cityList.get(0);
        }
    }
}
