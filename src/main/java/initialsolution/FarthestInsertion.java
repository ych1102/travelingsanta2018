package initialsolution;

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
public class FarthestInsertion {
    private List<City> cityList;

    public FarthestInsertion(List<City> cityList){
        this.cityList = cityList;
    }

    public void farthestInsertion(List<City> resultCityList, int searchZone){
        City currentCity = cityList.get(0);
        cityList.remove(currentCity);
        resultCityList.add(currentCity);
        City secondCity = Tools.findFarthestCity(currentCity,cityList);
        resultCityList.add(secondCity);
        cityList.remove(secondCity);
        long startTime = System.currentTimeMillis();
        int stopController = 0;

        while(cityList.size()>0){
            stopController++;
            if(cityList.size()%500==499){
                System.out.println(cityList.size());
                long endTime = System.currentTimeMillis();
                System.out.println("CPU:"+(endTime-startTime)/1000.0);
            }
            double farthestDist = 0;
            City farthestToInsertCity = null;
            int searchSize = 0;
            for(City toInsertCity:cityList){
                City nearestCity = Tools.findNearestCity(toInsertCity,resultCityList);
                if(nearestCity.getDistanceToPrevCity()>farthestDist){
                    farthestDist = nearestCity.getDistanceToPrevCity();
                    farthestToInsertCity = toInsertCity;
                    farthestToInsertCity.setNearestCity(nearestCity);
                }
                searchSize++;
                if(searchSize>searchZone)break;
            }

            Insertion.insertIntoListByLowestCostSimpleVersion(resultCityList,farthestToInsertCity,farthestToInsertCity.getNearestCity().getLoc());

            cityList.remove(farthestToInsertCity);
            /*if(stopController>100){
                break;
            }*/
        }
        resultCityList.add(resultCityList.get(0));
        System.out.println(Tools.calculateLoopDistance(resultCityList));
        Tools.outputResultCSV(resultCityList);
    }
}
