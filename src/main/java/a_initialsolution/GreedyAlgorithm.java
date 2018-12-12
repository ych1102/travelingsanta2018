package a_initialsolution;

import auxilliarytools.Tools;
import entity.City;

import java.util.List;

/**
 * Created by 01378498 on 2018/12/5.
 */
public class GreedyAlgorithm implements InitialSolution{

    public void insertRun(List<City> inputCities, List<City> outputCityList){
        greedyRun(inputCities,outputCityList);
    }
    public void greedyRun(List<City> inputCities, List<City> outputCityList){
        if(inputCities.size()==0)return;
        City currentCity = inputCities.get(0);
        inputCities.remove(currentCity);
        int stopController = 0;
        while(inputCities.size()>0){
            stopController++;
            outputCityList.add(currentCity);
            City nearestCity = Tools.findNearestCity(currentCity,inputCities);
            currentCity = nearestCity;
            inputCities.remove(nearestCity);
            if(inputCities.size()%1000==999){
                System.out.println(inputCities.size());
                long endTime = System.currentTimeMillis();
                System.out.println("CPU:"+(endTime-Tools.startTime)/1000.0);
            }
            if(stopController>1000000)break;
        }
        outputCityList.add(currentCity);

    }

    public void greedyWithPrimeRun(List<City> inputCities,List<City> resultCityList){
        City currentCity = inputCities.get(0);
        inputCities.remove(currentCity);
        int stepCount = 0;
        while(inputCities.size()>0){
            resultCityList.add(currentCity);
            City nearestCity = stepCount%10==9?Tools.findNearestCityConsiderPrime(currentCity,inputCities):Tools.findNearestCity(currentCity,inputCities);
            currentCity = nearestCity;
            inputCities.remove(nearestCity);
            if(inputCities.size()%1000==0)System.out.println(inputCities.size());
            stepCount++;
        }
        resultCityList.add(currentCity);

    }


}
