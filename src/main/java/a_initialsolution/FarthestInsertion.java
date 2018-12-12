package a_initialsolution;

import auxilliarytools.Insertion;
import auxilliarytools.Tools;
import entity.City;

import java.util.List;

/**Starting from a degenerate tour consisting of the two cities with maximum inter-city distance,
 * repeatedly choose the non-tour city with the maximum distance to its nearest neighbor among the tour cities,
 * and insert it as in Nearest Insertion.
 *
 * Created by 01378498 on 2018/12/6.
 */
public class FarthestInsertion implements InitialSolution{
    public void insertRun(List<City> inputCities, List<City> outputCityList){
        farthestInsertionWithRestrictedSearchZone(inputCities,outputCityList,10000);
    }


    /**
     * 1. 首先找到距离0点最远的city，以这两个city为起点
     * 2. 找到离目前tour距离（以目标点和tour-city最近的距离来衡量）最远的city，作为下一个纳入tour的city
     * 3. 寻找插入的最佳位置
     * searchZoneLimit用于步骤2中，限制搜索范围
     * @param inputCities
     * @param searchZoneLimit
     */
    public void farthestInsertionWithRestrictedSearchZone(List<City> inputCities, List<City> outputCityList, int searchZoneLimit){

        City currentCity = inputCities.get(0);
        inputCities.remove(currentCity);
        outputCityList.add(currentCity);
        if(inputCities.size()==0)return; //可能只有一个点
        City secondCity = Tools.findFarthestCity(currentCity,inputCities);
        outputCityList.add(secondCity);
        inputCities.remove(secondCity);
        long startTime = System.currentTimeMillis();
        int stopController = 0;

        while(inputCities.size()>0){
            stopController++;
            if(inputCities.size()%500==499){
                System.out.println(inputCities.size());
                long endTime = System.currentTimeMillis();
                System.out.println("CPU:"+(endTime-startTime)/1000.0);
            }
            double farthestDist = 0;
            City farthestToInsertCity = null;
            int searchZone = 0;
            for(City toInsertCity:inputCities){
                City nearestCity = Tools.findNearestCity(toInsertCity,outputCityList);
                if(nearestCity.getDistanceToPrevCity()>farthestDist){
                    farthestDist = nearestCity.getDistanceToPrevCity();
                    farthestToInsertCity = toInsertCity;
                    farthestToInsertCity.setNearestCity(nearestCity);
                }
                searchZone++;
                if(searchZone>searchZoneLimit)break;
            }

            Insertion.insertIntoListByLowestCostSimpleVersion(outputCityList,farthestToInsertCity,farthestToInsertCity.getNearestCity().getLoc());

            inputCities.remove(farthestToInsertCity);
            /*if(stopController>100){
                break;
            }*/
        }

    }
}
