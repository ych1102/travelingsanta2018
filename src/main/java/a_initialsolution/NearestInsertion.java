package a_initialsolution;

import auxilliarytools.Insertion;
import auxilliarytools.Tools;
import entity.City;

import java.util.List;

/** Starting from a degenerate tour consisting of the two closest cities,
 * repeatedly choose the non-tour city with the minimum distance to its nearest neighbor among the tour cities,
 * and insert it in between the two consecutive tour cities for which
 * such an insertion causes the minimum increase in total tour length.
 *
 * Created by 01378498 on 2018/12/6.
 */
public class NearestInsertion {
    public void insertRun(List<City> inputCities, List<City> outputCityList){
        nearestInsertionWithRestrictedSearchZone(inputCities,outputCityList,10000);
    }

    public void nearestInsertion(List<City> inputCityList, List<City> resultCityList){
        City currentCity = inputCityList.get(0);
        inputCityList.remove(currentCity);
        resultCityList.add(currentCity);
        City secondCity = Tools.findNearestCity(currentCity,inputCityList);
        resultCityList.add(secondCity);
        inputCityList.remove(secondCity);
        long startTime = System.currentTimeMillis();
        int stopController = 0;
        while(inputCityList.size()>0){
            stopController++;
            if(inputCityList.size()%500==499){
                System.out.println(inputCityList.size());
                long endTime = System.currentTimeMillis();
                System.out.println("CPU:"+(endTime-startTime)/1000.0);
            }
            double shortestDist = 10000;
            City nearestToInsertCity = null;
            for(City toInsertCity:inputCityList){
                City nearestCity = Tools.findNearestCity(toInsertCity,resultCityList);
                if(nearestCity.getDistanceToPrevCity()<shortestDist){
                    shortestDist = nearestCity.getDistanceToPrevCity();
                    nearestToInsertCity = toInsertCity;
                    nearestToInsertCity.setNearestCity(nearestCity);
                }
            }

            Insertion.insertIntoListByLowestCostSimpleVersion(resultCityList,nearestToInsertCity,nearestToInsertCity.getNearestCity().getLoc());

            inputCityList.remove(nearestToInsertCity);
            /*if(stopController>100){
                break;
            }*/
        }

    }

    /**
     * 1. 首先找到距离0点最近的city，以这两个city为起点
     * 2. 找到离目前tour距离（以目标点和tour-city最近的距离来衡量）最近的city，作为下一个纳入tour的city
     * 3. 寻找插入的最佳位置
     * searchZoneLimit用于步骤2中，限制搜索范围
     * @param resultCityList
     * @param searchZoneLimit
     */
    public void nearestInsertionWithRestrictedSearchZone(List<City> inputCityList, List<City> resultCityList, int searchZoneLimit){
        City currentCity = inputCityList.get(0);
        inputCityList.remove(currentCity);
        resultCityList.add(currentCity);
        City secondCity = Tools.findNearestCity(currentCity,inputCityList);
        resultCityList.add(secondCity);
        inputCityList.remove(secondCity);
        long startTime = System.currentTimeMillis();
        int stopController = 0;
        while(inputCityList.size()>0){
            stopController++;
            if(inputCityList.size()%500==499){
                System.out.println(inputCityList.size());
                long endTime = System.currentTimeMillis();
                System.out.println("CPU:"+(endTime-startTime)/1000.0);
            }
            double shortestDist = 10000;
            City nearestToInsertCity = null;
            int searchZone = 0;
            for(City toInsertCity:inputCityList){
                City nearestCity = Tools.findNearestCity(toInsertCity,resultCityList);
                if(nearestCity.getDistanceToPrevCity()<shortestDist){
                    shortestDist = nearestCity.getDistanceToPrevCity();
                    nearestToInsertCity = toInsertCity;
                    nearestToInsertCity.setNearestCity(nearestCity);
                }
                searchZone++;
                if(searchZone>searchZoneLimit)break;
            }

            Insertion.insertIntoListByLowestCostSimpleVersion(resultCityList,nearestToInsertCity,nearestToInsertCity.getNearestCity().getLoc());

            inputCityList.remove(nearestToInsertCity);
            /*if(stopController>100){
                break;
            }*/
        }

    }

}
