package a_improvement;

import auxilliarytools.Insertion;
import auxilliarytools.Removal;
import auxilliarytools.Tools;
import entity.City;

import java.util.List;

/**
 * 随机remove一小段cityList，重新insert（按最小成本one-by-one）
 * Created by 01378498 on 2018/12/7.
 */
public class DestructionAndRebuild {
    public void minorDestroyAndRebuild(List<City> cityList, int randSizeLimit, int iterLimit){
        int iterCount = 0;
        long startTime = System.currentTimeMillis();
        while(iterCount<iterLimit){
            int start = (int)Math.round(Math.random()*cityList.size());
            int end = start + (int)(Math.random()*randSizeLimit);
          /*  start = 2;
            end = 4;*/
            List<City> toBeInsertList = Removal.removeByInterval(cityList,start,end);
            System.out.println("after remove:"+cityList);
            Insertion.insertListIntoTour(toBeInsertList,cityList);
            iterCount++;
            long endTime = System.currentTimeMillis();
            System.out.println("CPU:"+(endTime-startTime)/1000.0);
        }
    }

}
