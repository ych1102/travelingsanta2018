package a_improvement;

import auxilliarytools.Insertion;
import auxilliarytools.Removal;
import auxilliarytools.Tools;
import entity.City;

import java.util.ArrayList;
import java.util.List;

/**
 * 找出长度大于distThreshold的arc，把两边的city都删掉，重新找最小成本的位置插入
 * Created by 01378498 on 2018/12/7.
 */
public class RemoveLongArcAndRebuild implements Improvement{
    public void improveRun(List<City> cityList, int distThreshold){
        removeLongArcAndRebuild(cityList,distThreshold);
    }

    public void improveRunBatch(List<List<City>> zoneList, int distThreshold){
        for(List<City> zone:zoneList){
            removeLongArcAndRebuild(zone,distThreshold);
        }
    }

    public void removeLongArcAndRebuild(List<City> cityList, int distThreshold){

        System.out.println("before remove:"+cityList.size());
        List<City> toBeInsertList = new ArrayList<>();
        int index = cityList.size()-1;
        while(index>0){

            City city = cityList.get(index);
            City cityNext = cityList.get(index-1);
            double distance =  Tools.getEuclideanDistance(city,cityNext);
            System.out.println(city+" "+cityNext+" dist "+distance+" "+distThreshold);

            if(distance>distThreshold){
                toBeInsertList.add(city);
                toBeInsertList.add(cityNext);
                cityList.remove(index);
                cityList.remove(index-1);
                index -= 2;
            }else index--;
        }
        System.out.println("size of removed cities :"+toBeInsertList.size());

        System.out.println("after remove:"+cityList.size());
        Insertion.insertListIntoTour(toBeInsertList,cityList);
        /*long endTime = System.currentTimeMillis();
        System.out.println("CPU:"+(endTime-Tools.startTime)/1000.0);*/
    }
}
