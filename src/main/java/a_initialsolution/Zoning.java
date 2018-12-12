package a_initialsolution;

import a_improvement.Improvement;
import a_improvement.RemoveLongArcAndRebuild;
import auxilliarytools.Tools;
import entity.City;
import entity.CityPair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 01378498 on 2018/12/7.
 */
public class Zoning {
    private List<City> cityList;

    public Zoning(List<City> cityList){
        this.cityList = cityList;
    }

    public List<List<City>> zonePartition(int zoneNumX, int zoneNumY){
        double[] bounds = Tools.zoneBound(cityList);
        List<List<City>> zoneList = new ArrayList<>();
        int stepSizeX = (int)((bounds[1]-bounds[0])/zoneNumX);
        int stepSizeY = (int)((bounds[3]-bounds[2])/zoneNumY);

        for(int i=0;i<zoneNumX;i++){
            //int xLeft = stepSizeX*i+(int)(bounds[0]);
            //int xRight = stepSizeX*(i+1)+(int)(bounds[0]);
            for (int j = 0; j < zoneNumY; j++) {
                //int yBottom = stepSizeY*j+(int)(bounds[1]);
                //int yCeiling = stepSizeY*(j+1)+(int)(bounds[1]);
                zoneList.add(new ArrayList<>());
            }
        }
        System.out.println(bounds[0]+" " + bounds[1]+ " "+bounds[2]+" "+bounds[3]+" bounds");
        for(City c:cityList){
            int i = (int)Math.floor((c.getX()-bounds[0])/stepSizeX);
            int j = (int)Math.floor((c.getY()-bounds[2])/stepSizeY);
            if(i==zoneNumX)i=i-1;
            if(j==zoneNumY)j=j-1;
            zoneList.get(i*zoneNumY+j).add(c);
            //System.out.println(c+" "+(i*zoneNumY+j)+" i "+i+" j "+j);
        }
        return zoneList;
    }

    public List<List<City>> runOneByOne(List<List<City>> zoneList, InitialSolution initialSolution){
        List<List<City>> outputZoneResult = new ArrayList<>();
        int count = 0;
        for(List<City> cities:zoneList){
            System.out.println("runOneByOne city size: ("+count+"):"+ cities.size());
            List<City> outputList = new ArrayList<>();
            outputZoneResult.add(outputList);
            initialSolution.insertRun(cities,outputList);
            //System.out.println("result:"+outputList);
            count++;
            //if(count>0)break;
        }
        return outputZoneResult;
    }

    public void connectZones(List<List<City>> zoneList, List<City> resultList){
        int count=0;
        List<City> zone1 = zoneList.get(0);
        Tools.writeResultCoordinatesToFile(zone1,"data/coordinate"+(count++));
        Improvement improveMethod = new RemoveLongArcAndRebuild();
        //improveMethod.improveRun(zone1,300);
        while(zoneList.size()>1){
            zone1 = zoneList.get(0);
            List<City> zone2 = zoneList.get(1);

            //improveMethod.improveRun(zone2,300);
            if(count<5)Tools.writeResultCoordinatesToFile(zone2,"data/coordinate"+(count++));

            CityPair cp = Tools.findNearestCityPair(zone1,zone2);
            //System.out.println("tour1:"+zone1);
            //System.out.println("tour2:"+zone2);
            //System.out.println(cp);

            int insertPosition = cp.getIndex1()+1;
            for(int j=cp.getIndex2();j<zone2.size();j++){
                zone1.add(insertPosition++,zone2.get(j));
            }
            for(int j=0;j<cp.getIndex2();j++){
                zone1.add(insertPosition++,zone2.get(j));
            }
            //improveMethod.improveRun(zone1,300);
            //System.out.println("after connection:"+zone1);
            if(count<5){
                Tools.writeResultCoordinatesToFile(zone1,"data/coordinate"+(count++));
            }else System.out.println(" ********* ZONE Connecting ********** No. "+(count++)+" tour size:"+zone1.size());
            zoneList.remove(1);

            //if(count>1)break;
        }
        resultList.addAll(zoneList.get(0));
    }
}
