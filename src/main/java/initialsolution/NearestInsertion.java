package initialsolution;

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
    private List<City> cityList;

    public NearestInsertion(List<City> cityList){
        this.cityList = cityList;
    }

    public void nearestInsertion(List<City> resultCityList){
        City currentCity = cityList.get(0);
        cityList.remove(currentCity);
        resultCityList.add(currentCity);
        City secondCity = Tools.findNearestCity(currentCity,cityList);
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
            double shortestDist = 10000;
            City nearestToInsertCity = null;
            for(City toInsertCity:cityList){
                City nearestCity = Tools.findNearestCity(toInsertCity,resultCityList);
                if(nearestCity.getDistanceToPrevCity()<shortestDist){
                    shortestDist = nearestCity.getDistanceToPrevCity();
                    nearestToInsertCity = toInsertCity;
                    nearestToInsertCity.setNearestCity(nearestCity);
                }
            }

            Insertion.insertIntoListByLowestCostSimpleVersion(resultCityList,nearestToInsertCity,nearestToInsertCity.getNearestCity().getLoc());

            cityList.remove(nearestToInsertCity);
            /*if(stopController>100){
                break;
            }*/
        }
        resultCityList.add(resultCityList.get(0));
        System.out.println(Tools.calculateLoopDistance(resultCityList));
        Tools.outputResultCSV(resultCityList);
    }

    public void nearestInsertionSimple(List<City> resultCityList){
        City currentCity = cityList.get(0);
        cityList.remove(currentCity);
        resultCityList.add(currentCity);
        City secondCity = Tools.findNearestCity(currentCity,cityList);
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
            double shortestDist = 10000;
            City nearestToInsertCity = null;
            int searchZone = 100;
            for(City toInsertCity:cityList){
                City nearestCity = Tools.findNearestCity(toInsertCity,resultCityList);
                if(nearestCity.getDistanceToPrevCity()<shortestDist){
                    shortestDist = nearestCity.getDistanceToPrevCity();
                    nearestToInsertCity = toInsertCity;
                    nearestToInsertCity.setNearestCity(nearestCity);
                }
                searchZone--;
                if(searchZone<0)break;
            }

            Insertion.insertIntoListByLowestCostSimpleVersion(resultCityList,nearestToInsertCity,nearestToInsertCity.getNearestCity().getLoc());

            cityList.remove(nearestToInsertCity);
            /*if(stopController>100){
                break;
            }*/
        }
        resultCityList.add(resultCityList.get(0));
        System.out.println(Tools.calculateLoopDistance(resultCityList));
        Tools.outputResultCSV(resultCityList);
    }

    public void nearestInsertionSimplest(List<City> resultCityList){
        City currentCity = cityList.get(0);
        cityList.remove(currentCity);
        resultCityList.add(currentCity);
        City secondCity = Tools.findNearestCity(currentCity,cityList);
        resultCityList.add(secondCity);
        cityList.remove(secondCity);
        long startTime = System.currentTimeMillis();
        int stopController = 0;
        while(cityList.size()>0){
            stopController++;
            if(cityList.size()%10000==1){
                System.out.println(cityList.size());
                long endTime = System.currentTimeMillis();
                System.out.println("CPU:"+(endTime-startTime)/1000.0);
            }
            City nearestToInsertCity = cityList.get(0);
            City nearestCity = Tools.findNearestCity(nearestToInsertCity,resultCityList);
            nearestToInsertCity.setNearestCity(nearestCity);

            Insertion.insertIntoListByLowestCostSimpleVersion(resultCityList,nearestToInsertCity,nearestToInsertCity.getNearestCity().getLoc());

            cityList.remove(nearestToInsertCity);
            /*if(stopController>100){
                break;
            }*/
        }
        resultCityList.add(resultCityList.get(0));
        System.out.println(Tools.calculateLoopDistance(resultCityList));
        Tools.outputResultCSV(resultCityList);
    }

}
