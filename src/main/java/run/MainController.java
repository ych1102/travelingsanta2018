package run;

import a_improvement.DestructionAndRebuild;
import a_improvement.Improvement;
import a_improvement.RemoveLongArcAndRebuild;
import a_initialsolution.FarthestInsertion;
import a_initialsolution.Zoning;
import auxilliarytools.Tools;
import dataprocessing.ReadData;
import entity.City;
import a_initialsolution.GreedyAlgorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 01378498 on 2018/12/5.
 */
public class MainController {
    public static void main(final String[] args) {
        List<City> resultList = new ArrayList<>();
        ReadData rd = new ReadData();
        rd.run("data/cities.csv","data/output1812630.csv");

        //SortCities.sortByReverseOrder(rd.getCityList());

        /*GreedyAlgorithm nal = new GreedyAlgorithm(rd.getCityList());
        nal.greedyRun(resultList);*/
        /*DestructionAndRebuild des = new DestructionAndRebuild();
        des.minorDestroyAndRebuild(rd.getReadCityList(),50,10);*/
        /*RemoveLongArcAndRebuild rar = new RemoveLongArcAndRebuild();
        rar.removeLongArcAndRebuild(rd.getReadCityList(),2000);*/

        Zoning zoning = new Zoning(rd.getCityList());
        List<List<City>> zoneList = zoning.zonePartition(10,7);
        List<List<City>> outputZoneList = zoning.runOneByOne(zoneList,new GreedyAlgorithm());
        zoning.connectZones(outputZoneList,resultList);
        Tools.outputResultCSV(resultList);

    }
}
