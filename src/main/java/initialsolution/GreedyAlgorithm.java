package initialsolution;

import auxilliarytools.Insertion;
import auxilliarytools.Tools;
import entity.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 01378498 on 2018/12/5.
 */
public class GreedyAlgorithm {
    private List<City> cityList;

    public GreedyAlgorithm(List<City> cityList){
        this.cityList = cityList;
    }

    public void greedyRun(List<City> resultCityList){
        City currentCity = cityList.get(0);
        cityList.remove(currentCity);
        int stopController = 0;
        while(cityList.size()>0){
            stopController++;
            resultCityList.add(currentCity);
            City nearestCity = Tools.findNearestCity(currentCity,cityList);
            currentCity = nearestCity;
            cityList.remove(nearestCity);
            if(cityList.size()%1000==0)System.out.println(cityList.size());
            if(stopController>1000)break;
        }
        resultCityList.add(currentCity);
        resultCityList.add(resultCityList.get(0));
        System.out.println(Tools.calculateLoopDistance(resultCityList));
        Tools.outputResultCSV(resultCityList);
    }

    public void greedyWithPrimeRun(List<City> resultCityList){
        City currentCity = cityList.get(0);
        cityList.remove(currentCity);
        int stepCount = 0;
        while(cityList.size()>0){
            resultCityList.add(currentCity);
            City nearestCity = stepCount%10==9?Tools.findNearestCityConsiderPrime(currentCity,cityList):Tools.findNearestCity(currentCity,cityList);
            currentCity = nearestCity;
            cityList.remove(nearestCity);
            if(cityList.size()%1000==0)System.out.println(cityList.size());
            stepCount++;
        }
        resultCityList.add(currentCity);
        resultCityList.add(resultCityList.get(0));
        System.out.println(Tools.calculateLoopDistance(resultCityList));
        Tools.outputResultCSV(resultCityList);
    }


}
