package run;

import a_improvement.Improvement;
import a_improvement.ThreeOpt;
import a_improvement.TwoOpt;
import auxilliarytools.Tools;
import dataprocessing.ReadData;
import entity.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 01378498 on 2018/12/7.
 */
public class TestThreeOpt {
    public static void main(final String[] args) {
        List<City> resultList = new ArrayList<>();
        ReadData rd = new ReadData();
        rd.run("data/cities.csv","data/output1569417.csv");

        resultList = rd.getReadCityList();
        Improvement improveMethod = new ThreeOpt();
        for (int i = 0; i < 10; i++) {
            improveMethod.improveRun(resultList,5);
        }

        Tools.outputResultCSV(resultList);

    }
}
