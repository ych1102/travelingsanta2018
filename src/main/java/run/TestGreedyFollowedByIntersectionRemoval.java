package run;

import a_improvement.DetectIntersectionAndResolve;
import a_improvement.Improvement;
import a_improvement.TwoOpt;
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
public class TestGreedyFollowedByIntersectionRemoval {
    public static void main(final String[] args) {
        List<City> resultList = new ArrayList<>();
        ReadData rd = new ReadData();
        rd.run("data/cities.csv","data/output1812630.csv");

        /*GreedyAlgorithm nal = new GreedyAlgorithm();
        nal.greedyRun(rd.getCityList(),resultList);*/

        Improvement improveMethod = new TwoOpt();
        improveMethod.improveRun(rd.getReadCityList(),50000);
        Tools.outputResultCSV(rd.getReadCityList());

    }
}
