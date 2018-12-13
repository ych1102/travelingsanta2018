package dataprocessing;

import auxilliarytools.Tools;
import entity.City;
import entity.CityPair;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.*;

/**
 * Created by 01378498 on 2018/12/5.
 */
@Getter
@Setter
public class ReadData {
    private List<City> cityList;
    private Map<Integer, City> cityMap;
    private List<City> readCityList;

    private Map<String, Double> distMap;  // <id_id1,dist>
    private double[][] distArray;

    public void run(String rawData, String preGeneratedTour){
        readCsv(rawData);
        readCityFromCsv(preGeneratedTour);

        for(int i=0;i<cityList.size();i+=10000){
            calculateFarthestAndNearestCity(i,Math.min(i+10000,cityList.size()));
        }

        //calculateDistanceArray();
        //calculateDistanceMap();
    }

    public void readCsv(String fileName) {

        try {
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
            reader.readLine();  //首行不读
            cityList = new ArrayList<>();
            cityMap = new HashMap<>();

            while ((line = reader.readLine()) != null) {
                String[] columns = StringUtils.splitByWholeSeparatorPreserveAllTokens(line, ",");
                City c = new City();
                c.setId(Integer.parseInt(columns[0]));
                c.setX(Double.parseDouble(columns[1]));
                c.setY(Double.parseDouble(columns[2]));
                c.setPrimeCity(Tools.isPrime(c.getId()));
                cityList.add(c);
                cityMap.put(c.getId(),c);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void readCityFromCsv(String fileName){
        try {
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
            reader.readLine();  //首行不读
            readCityList = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                if(line.trim().equals(""))break;
                String[] columns = StringUtils.splitByWholeSeparatorPreserveAllTokens(line, ",");
                City c = cityMap.get(Integer.parseInt(columns[0]));
                readCityList.add(c);
            }
            readCityList.remove(readCityList.size()-1);  //移除最后一个city0
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public void calculateDistanceMap(){
        distMap = new HashMap<>();
        for (int i = 0; i < cityList.size(); i++) {
            for (int j = i+1; j < cityList.size(); j++) {
                City city = cityList.get(i);
                City city1 = cityList.get(j);
                double dist = Tools.getManhattanDistance(city,city1);
                distMap.put(city.getId()+"_"+city1.getId(),dist);
                distMap.put(city1.getId()+"_"+city.getId(),dist);
            }
            System.out.println(i);
        }
    }

    public void calculateDistanceArray(){
        distArray = new double[cityList.size()][cityList.size()];

        for (int i = 0; i < cityList.size(); i++) {
            City city = cityList.get(i);

            distArray[city.getId()][city.getId()] = 0;

            for (int j = i+1; j < cityList.size(); j++) {
                City city1 = cityList.get(j);
                double dist = Tools.getManhattanDistance(city,city1);
                distArray[city.getId()][city1.getId()] = dist;
                distArray[city1.getId()][city.getId()] = dist;
            }
        }
    }

    public void calculateFarthestAndNearestCity(int startIndex, int endIndex){
        String fileName1 = "data/farestcities"+startIndex+"_"+endIndex+".txt";
        String fileName2 = "data/nearestcities"+startIndex+"_"+endIndex+".txt";

        File file = new File(fileName1);
        if(file.exists()){
            try {
                String line;
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName1)));
//                for(int i=startIndex;i< endIndex;i++){
//                    City cityI = cityList.get(i);
//                    line = reader.readLine();
//                    String[] columns = StringUtils.splitByWholeSeparatorPreserveAllTokens(line, ",");
//
//                    for(int j=0;j<columns.length;j+=2){
//                        if(columns[j].equals("")) break;
//
//                        CityPair cp = new CityPair();
//                        cp.setCurrentCity(cityI);
//                        cp.setTargetCity(cityList.get(Integer.parseInt(columns[j])));
//                        cp.setDist(Double.parseDouble(columns[j+1]));
//                        cityI.getFarestCityPairList().add(cp);
//                    }
//                }

                reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName2)));
                for(int i=startIndex;i< endIndex;i++){
                    City cityI = cityList.get(i);
                    line = reader.readLine();  //首行不读
                    String[] columns = StringUtils.splitByWholeSeparatorPreserveAllTokens(line, ",");
                    for(int j=0;j<columns.length;j+=2){
                        if(columns[j].equals("")) break;

                        CityPair cp = new CityPair();
                        cp.setCurrentCity(cityI);
                        cp.setTargetCity(cityList.get(Integer.parseInt(columns[j])));
                        cp.setDist(Double.parseDouble(columns[j+1]));
                        cityI.getNearestCityPairList().add(cp);
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            StringBuilder sb1 = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();

            for(int i=startIndex; i < endIndex; i++){
                //for(int i=0; i < cityList.size(); i++){
                if(i % 1000 == 0) System.out.println("one line  "+i);

                City cityI = cityList.get(i);

                List<CityPair> cpList = new ArrayList();

                for(int j=0;j<cityList.size();j++){
                    if(i == j) continue;

                    City cityJ = cityList.get(j);
                    double dist = Tools.getEuclideanDistance(cityI, cityJ);
                    CityPair cp = new CityPair();
                    cp.setCurrentCity(cityI);
                    cp.setTargetCity(cityJ);
                    cp.setDist(dist);

                    cpList.add(cp);
                }

                Collections.sort(cpList, new Comparator<CityPair>() {
                    @Override
                    public int compare(CityPair o1, CityPair o2) {
                        if(o1.getDist() < o2.getDist()){
                            return -1;
                        }else if(o1.getDist() > o2.getDist()){
                            return 1;
                        }else {
                            return 0;
                        }
                    }
                });

                for(int j=cpList.size()-1;j>cpList.size()-101;j--){
                    sb1.append(cpList.get(j).getTargetCity().getId()+","+cpList.get(j).getDist()+",");
                }
                sb1.append("\n");

                for(int j=0;j<100;j++){
                    sb2.append(cpList.get(j).getTargetCity().getId()+","+cpList.get(j).getDist()+",");
                }
                sb2.append("\n");
            }

            try {
                MyFile.createTxtFile(fileName1);
                MyFile.writeTxtFile(sb1.toString());
                MyFile.createTxtFile(fileName2);
                MyFile.writeTxtFile(sb2.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}