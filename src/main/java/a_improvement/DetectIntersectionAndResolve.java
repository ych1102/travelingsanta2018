package a_improvement;

import auxilliarytools.Tools;
import entity.City;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 * Created by 01378498 on 2018/12/11.
 */
public class DetectIntersectionAndResolve implements Improvement{
    public void improveRun(List<City> cityList, int iteration){
        detectIntersectionAndResolve(cityList,iteration);
    }

    public void improveRunBatch(List<List<City>> zoneList, int iteration){
        for(List<City> zone:zoneList){
            detectIntersectionAndResolve(zone,iteration);
        }
    }

    /**
     * 只修复距离gapBetweenCity以外的交叉线段
     * @param cityList
     * @param iteration
     */
    public void detectIntersectionAndResolve(List<City> cityList, int iteration){
        //System.out.println("detectIntersectionAndResolve city size "+cityList.size());
        //Tools.writeResultCoordinatesToFile(cityList,"data/coordinateBeforeIntersectionRemoval");
        if(cityList.size()<300)iteration = 3;
        int count = 0;
        for(int iter=0;iter<iteration;iter++){
            int i = 0;
            while(i+1<cityList.size()){
                City c1 = cityList.get(i);
                City c2 = cityList.get(i+1);

                for (int j = i+2; j < cityList.size()-1; j++) {
                    City c3 = cityList.get(j);
                    City c4 = cityList.get(j+1);
                    if(Tools.intersect(c1,c2,c3,c4)){
                        Tools.reverseSubSection(cityList,i+1,j);
                        if(cityList.size()>10000) {
                            System.out.println("largeData indicator iter:"+iter+" i "+i+" j "+j+" count "+count);
                            if(count>100)return;
                        }
                        count++;
                    }
                }

                i++;
            }

        }

        //Tools.writeResultCoordinatesToFile(cityList,"data/coordinateAfterIntersectionRemoval");
    }


}
