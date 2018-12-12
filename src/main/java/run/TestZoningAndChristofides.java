package run;

import a_initialsolution.GreedyAlgorithm;
import a_initialsolution.Zoning;
import auxilliarytools.Tools;
import christofidesalgorithm.Christofides;
import dataprocessing.ReadData;
import entity.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 01378498 on 2018/12/7.
 */
public class TestZoningAndChristofides {
    public static void main(final String[] args) {
        List<City> resultList = new ArrayList<>();
        ReadData rd = new ReadData();
        rd.run("data/cities.csv","data/output1812630.csv");

        Zoning zoning = new Zoning(rd.getCityList());
        List<List<City>> zoneList = zoning.zonePartition(10,10);
        List<List<City>> outputZoneList = zoning.runOneByOne(zoneList,new GreedyAlgorithm());
        for(int j=0;j<outputZoneList.size();j++){
            List<City> zone = outputZoneList.get(j);
            Christofides christofides = new Christofides(false);
            double[] x = new double[zone.size()];
            double[] y = new double[zone.size()];
            for (int i = 0; i < zone.size(); i++) {
                x[i] = zone.get(i).getX();
                y[i] = zone.get(i).getY();
            }
            int[] route = christofides.solve(x,y);
            List<City> temp2 = new ArrayList<>();
            for (int i = 0; i < route.length; i++) {
                temp2.add(zone.get(route[i]));
            }
            outputZoneList.set(j,temp2);
            System.out.println("zone "+j);
        }
        zoning.connectZones(outputZoneList,resultList);
        Tools.outputResultCSV(resultList);

    }
}
