package a_improvement;

import auxilliarytools.Tools;
import entity.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 01378498 on 2018/12/11.
 */
public class TwoOpt implements Improvement {
    public void improveRun(List<City> cityList, int maxCount){
        twoOpt(cityList,maxCount);
    }

    public void improveRunBatch(List<List<City>> zoneList, int iteration){
        int count = 0;
        for(List<City> zone:zoneList){
            System.out.println("Zone "+count++);
            twoOpt(zone,iteration);
        }
    }

    public void twoOpt(List<City> cityList, int maxCount){
        double dist = Tools.calculateSubLoopDistanceNoPrime(cityList);
        if(cityList.size()<3)return;
        int count = 0;
        while(count<maxCount){
            int rand1 = (int)(Math.random()*cityList.size());
            int rand2 = (int)(Math.random()*cityList.size());
            while(rand2==rand1)rand2 = (int)(Math.random()*cityList.size());
            Tools.reverseSubSection(cityList,Math.min(rand1,rand2),Math.max(rand1,rand2));
            double tempDist = Tools.calculateSubLoopDistanceNoPrime(cityList);
            if(tempDist>=dist){ //没有优化，返回
                Tools.reverseSubSection(cityList,Math.min(rand1,rand2),Math.max(rand1,rand2));
            }else{
                dist = tempDist;
                System.out.println(" improve occur "+dist+" count "+count+" two rand positions:"+rand1+" "+rand2);
            }
            count++;
        }



    }



}
