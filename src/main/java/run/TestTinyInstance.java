package run;

import a_improvement.DetectIntersectionAndResolve;
import a_improvement.Improvement;
import a_improvement.RemoveLongArcAndRebuild;
import a_initialsolution.FarthestInsertion;
import a_initialsolution.GreedyAlgorithm;
import a_initialsolution.Zoning;
import auxilliarytools.Tools;
import christofidesalgorithm.Christofides;
import dataprocessing.ReadData;
import entity.City;

import javax.sound.midi.Soundbank;
import javax.tools.Tool;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 01378498 on 2018/12/7.
 */
public class TestTinyInstance {
    public static void main(final String[] args) {
        List<City> resultList = new ArrayList<>();
        ReadData rd = new ReadData();
        rd.run("data/cities.csv","data/output1812630.csv");


        List<City> tempList = new ArrayList<>();
        for(int i=100;i<105;i++){
            tempList.add(rd.getCityList().get(i));
        }
        tempList.add(rd.getCityList().get(0));
        for(int i=10;i<15;i++){
            tempList.add(rd.getCityList().get(i));
        }
        for(City c:tempList) System.out.println("1: "+c);
        //List<City> temp2 = Removal.removeByInterval(tempList,0,8);
        //for(City c:temp2) System.out.println("2: "+c);
        //for(City c:tempList) System.out.println("3: "+c);

        //Tools.outputResultCSV(tempList);
/*
        double r = Math.random()*5;
        System.out.println(r);
        int iaa = (int)(r);
        System.out.println(iaa);
*/


       /* Tools.writeResultCoordinatesToFile(tempList,"data/coordinates1");
        *//*DestructionAndRebuild des = new DestructionAndRebuild();
        des.minorDestroyAndRebuild(tempList,5,1);*//*
        RemoveLongArcAndRebuild rar = new RemoveLongArcAndRebuild();
        rar.removeLongArcAndRebuild(tempList,2000);
        Tools.writeResultCoordinatesToFile(tempList,"data/coordinates2");*/


        /*Zoning zoning = new Zoning(tempList);
        List<List<City>> zoneList = zoning.zonePartition(2,2);
        List<List<City>> outputZoneList = zoning.runOneByOne(zoneList,new FarthestInsertion());
        Improvement improveMethod = new RemoveLongArcAndRebuild();
        improveMethod.improveRunBatch(zoneList,1000);
        zoning.connectZones(outputZoneList,resultList);
        Tools.outputResultCSV(resultList);*/

        /*DetectIntersectionAndResolve det = new DetectIntersectionAndResolve();
        System.out.println(tempList);
        Tools.reverseSubSection(tempList,1,4);
        System.out.println(tempList);*/

        Christofides christofides = new Christofides(true);
        double[] x = new double[tempList.size()];
        double[] y = new double[tempList.size()];
        for (int i = 0; i < tempList.size(); i++) {
            x[i] = tempList.get(i).getX();
            y[i] = tempList.get(i).getY();
        }
        int[] route = christofides.solve(x,y);
        List<City> temp2 = new ArrayList<>();
        for (int i = 0; i < route.length; i++) {
            temp2.add(tempList.get(route[i]));
            System.out.println(tempList.get(route[i]));
        }
        Tools.outputResultCSV(temp2);

    }
}
