package dataprocessing;

import auxilliarytools.Tools;
import entity.City;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void run(){
        readCsv();
        //readCityFromCsv();
        //calculateDistanceMap();
    }

    public void readCsv() {

        try {
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("data/cities.csv")));
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

    public void readCityFromCsv(){
        try {
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("data/output1812630.csv")));
            reader.readLine();  //首行不读
            readCityList = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                String[] columns = StringUtils.splitByWholeSeparatorPreserveAllTokens(line, ",");
                City c = cityMap.get(Integer.parseInt(columns[0]));
                readCityList.add(c);
            }

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
}