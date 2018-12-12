package run;

import a_improvement.DetectIntersectionAndResolve;
import a_improvement.Improvement;
import a_initialsolution.GreedyAlgorithm;
import a_initialsolution.Zoning;
import auxilliarytools.Tools;
import dataprocessing.ReadData;
import entity.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 01378498 on 2018/12/7.
 */
public class TestZoningAndIntersectionRemoval {
    public static void main(final String[] args) {
        List<City> resultList = new ArrayList<>();
        ReadData rd = new ReadData();
        rd.run("data/cities.csv","data/output1812630.csv");

        Zoning zoning = new Zoning(rd.getCityList());
        List<List<City>> zoneList = zoning.zonePartition(10,10);
        List<List<City>> outputZoneList = zoning.runOneByOne(zoneList,new GreedyAlgorithm());
        Improvement improveMethod = new DetectIntersectionAndResolve();
        //improveMethod.improveRun(outputZoneList.get(98),9);
        improveMethod.improveRunBatch(outputZoneList,9);
        zoning.connectZones(outputZoneList,resultList);
        improveMethod.improveRun(resultList,5);
        Tools.outputResultCSV(resultList);

    }
}
