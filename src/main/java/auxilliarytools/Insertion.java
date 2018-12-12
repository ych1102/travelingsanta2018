package auxilliarytools;

import entity.City;

import java.util.List;

/**
 * Created by 01378498 on 2018/12/6.
 */
public class Insertion {

    public static void insertListIntoTour(List<City> cityListToInsert, List<City> tour){
        for(City c:cityListToInsert){
            insertIntoListByLowestCostEuclidean(tour,c);
        }
    }

    public static void insertIntoListByLowestCostManhattan(List<City> toInsertList, City toBeInserted){
        double minIncrementCost = Tools.getManhattanDistance(toInsertList.get(0),toBeInserted) +
                Tools.getManhattanDistance(toInsertList.get(toInsertList.size()-1),toBeInserted) -
                Tools.getManhattanDistance(toInsertList.get(0),toInsertList.get(toInsertList.size()-1));
        int insertPosition = toInsertList.size();
        for (int i = 0; i < toInsertList.size()-1; i++) {
            City cityI = toInsertList.get(i);
            City cityJ = toInsertList.get(i+1);
            double incrementCost = Tools.getManhattanDistance(cityI,toBeInserted) +
                    Tools.getManhattanDistance(cityJ,toBeInserted) -
                    Tools.getManhattanDistance(cityI,cityJ);
            if(incrementCost<minIncrementCost){
                minIncrementCost = incrementCost;
                insertPosition = i+1;
            }
        }
        toInsertList.add(insertPosition,toBeInserted);
    }

    public static void insertIntoListByLowestCostEuclidean(List<City> toInsertList, City toBeInserted){
        double minIncrementCost = Tools.getEuclideanDistance(toInsertList.get(0),toBeInserted) +
                Tools.getEuclideanDistance(toInsertList.get(toInsertList.size()-1),toBeInserted) -
                Tools.getEuclideanDistance(toInsertList.get(0),toInsertList.get(toInsertList.size()-1));
        int insertPosition = toInsertList.size();
        for (int i = 0; i < toInsertList.size()-1; i++) {
            City cityI = toInsertList.get(i);
            City cityJ = toInsertList.get(i+1);
            double incrementCost = Tools.getEuclideanDistance(cityI,toBeInserted) +
                    Tools.getEuclideanDistance(cityJ,toBeInserted) -
                    Tools.getEuclideanDistance(cityI,cityJ);
            if(incrementCost<minIncrementCost){
                minIncrementCost = incrementCost;
                insertPosition = i+1;
            }
        }
        toInsertList.add(insertPosition,toBeInserted);
    }

    public static void insertIntoListByLowestCostSimpleVersion(List<City> toInsertList, City toBeInserted, int locOfNearestCityInList){
        double minIncrementCost = Tools.getManhattanDistance(toInsertList.get(0),toBeInserted) +
                Tools.getManhattanDistance(toInsertList.get(toInsertList.size()-1),toBeInserted) -
                Tools.getManhattanDistance(toInsertList.get(0),toInsertList.get(toInsertList.size()-1));
        int insertPosition = toInsertList.size();
        /*System.out.println("loc:"+locOfNearestCityInList);
        System.out.println(toInsertList);
        System.out.println(toBeInserted)*/;
        if(locOfNearestCityInList==0){  //是0点
            City cityI = toInsertList.get(0);
            City cityJ = toInsertList.get(1);
            double incrementCost = Tools.getEuclideanDistance(cityI,toBeInserted) +
                    Tools.getEuclideanDistance(cityJ,toBeInserted) -
                    Tools.getEuclideanDistance(cityI,cityJ);
            if(incrementCost<minIncrementCost){
                insertPosition = 1;
            }
        }else if(locOfNearestCityInList==toInsertList.size()-1){ //在队尾
            City cityI = toInsertList.get(toInsertList.size()-2);
            City cityJ = toInsertList.get(toInsertList.size()-1);
            double incrementCost = Tools.getEuclideanDistance(cityI,toBeInserted) +
                    Tools.getEuclideanDistance(cityJ,toBeInserted) -
                    Tools.getEuclideanDistance(cityI,cityJ);
            if(incrementCost<minIncrementCost){
                insertPosition = toInsertList.size()-1;
            }
        }else{
            City cityI = toInsertList.get(locOfNearestCityInList);
            City cityJ = toInsertList.get(locOfNearestCityInList-1);
            double incrementCost = Tools.getEuclideanDistance(cityI,toBeInserted) +
                    Tools.getEuclideanDistance(cityJ,toBeInserted) -
                    Tools.getEuclideanDistance(cityI,cityJ);
            if(incrementCost<minIncrementCost){
                minIncrementCost = incrementCost;
                insertPosition = locOfNearestCityInList;
            }

            cityI = toInsertList.get(locOfNearestCityInList);
            cityJ = toInsertList.get(locOfNearestCityInList+1);
            incrementCost = Tools.getEuclideanDistance(cityI,toBeInserted) +
                    Tools.getEuclideanDistance(cityJ,toBeInserted) -
                    Tools.getEuclideanDistance(cityI,cityJ);
            if(incrementCost<minIncrementCost){
                minIncrementCost = incrementCost;
                insertPosition = locOfNearestCityInList+1;
            }
        }

        toInsertList.add(insertPosition,toBeInserted);
    }
}
