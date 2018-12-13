package a_improvement;

import auxilliarytools.Tools;
import entity.City;
import entity.CityPair;

import java.util.ArrayList;
import java.util.Iterator;
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
        for (int i = 0; i < cityList.size(); i++) {
            cityList.get(i).setLoc(i);
        }
        for (int i = 0; i < cityList.size()-2; i++) {
            City currentCity = cityList.get(i);
            List<CityPair> cityPairList = currentCity.getNearestCityPairList();
            for(CityPair cp:cityPairList){
                if(cp.getTargetCity().getLoc()<i+2||cp.getTargetCity().getLoc()==cityList.size()-1)continue;
                if(hasImprovement(cityList,i,cp.getTargetCity().getLoc())){
                    System.out.println("improve: CurrentPos "+i+" neighborPos "+cp.getTargetCity().getLoc());
                    reverseSubList(cityList,i+1,cp.getTargetCity().getLoc());
                }
            }
            if(i%20000==10)Tools.writeResultCoordinatesToFile(cityList,"mid_coordinate"+i);
        }
    }

    public boolean hasImprovement(List<City> cityList, int pos1, int pos2){
        int pos1suc = (pos1+1)%cityList.size();
        int pos2suc = (pos2+1)%cityList.size();
        double diff = Tools.getEuclideanDistance(cityList.get(pos1),cityList.get(pos2)) +
                Tools.getEuclideanDistance(cityList.get(pos1suc),cityList.get(pos2suc)) -
                Tools.getEuclideanDistance(cityList.get(pos1),cityList.get(pos1suc)) -
                Tools.getEuclideanDistance(cityList.get(pos2),cityList.get(pos2suc));
        return diff<0;

    }

    public void reverseSubList(List<City> cityList, int pos1suc, int pos2){
        if(cityList.size()<3)return;
        Tools.reverseSubSection(cityList,pos1suc,pos2);

    }

}
