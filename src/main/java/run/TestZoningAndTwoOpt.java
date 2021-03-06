package run;

import a_improvement.DetectIntersectionAndResolve;
import a_improvement.Improvement;
import a_improvement.TwoOpt;
import a_improvement.TwoOptConsiderPrime;
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
public class TestZoningAndTwoOpt {
    public static void main(final String[] args) {
        List<City> resultList = new ArrayList<>();
        ReadData rd = new ReadData();
        rd.run("data/cities.csv","data/output1583619.csv");

        //Zoning zoning = new Zoning(rd.getCityList());
        //List<List<City>> zoneList = zoning.zonePartition(10,10);
        //List<List<City>> outputZoneList = zoning.runOneByOne(zoneList,new GreedyAlgorithm());
        //zoning.connectZones(outputZoneList,resultList);
        resultList = rd.getReadCityList();
        Improvement improveMethod = new TwoOptConsiderPrime();
        improveMethod.improveRun(resultList,5);
        improveMethod.improveRun(resultList,5);
        Tools.outputResultCSV(resultList);

    }
}
