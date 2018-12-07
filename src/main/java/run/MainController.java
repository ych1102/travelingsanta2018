package run;

import initialsolution.FarthestInsertion;
import dataprocessing.ReadData;
import entity.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 01378498 on 2018/12/5.
 */
public class MainController {
    public static void main(final String[] args) {
        List<City> resultList = new ArrayList<>();
        ReadData rd = new ReadData();
        rd.run();

        //GreedyAlgorithm nal = new GreedyAlgorithm(rd.getCityList());

        //nal.greedyRun(resultList);
        /*nal.nearestInsertionSimple(resultList);*/
        FarthestInsertion far = new FarthestInsertion(rd.getCityList());
        far.farthestInsertion(resultList,100);
        //Tools.writeResultCoordinatesToFile(resultList,"data/coordinates");
        //System.out.println(Tools.calculateLoopDistance(rd.getReadCityList()));
    }
}
