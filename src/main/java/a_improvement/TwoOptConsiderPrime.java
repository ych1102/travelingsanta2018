package a_improvement;

import auxilliarytools.Tools;
import entity.City;
import entity.CityPair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 01378498 on 2018/12/11.
 */
public class TwoOptConsiderPrime implements Improvement {
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
        if(cityList.get(0).getId()!=0)Tools.startFrom0(cityList);  //让list从0开始
        for (int i = 0; i < cityList.size(); i++) {
            cityList.get(i).setLoc(i);
        }
        for (int i = 0; i < cityList.size()-2; i++) {
            City currentCity = cityList.get(i);
            List<CityPair> cityPairList = currentCity.getNearestCityPairList();
            for(CityPair cp:cityPairList){
                if(cp.getTargetCity().getLoc()<i+2||cp.getTargetCity().getLoc()==cityList.size()-1)continue;
                double diff = hasImprovement(cityList,i,cp.getTargetCity().getLoc());
                if(diff<0){
                    System.out.println("improve: CurrentPos "+i+" neighborPos "+cp.getTargetCity().getLoc()+" improveValue:"+diff);
                    reverseSubList(cityList,i+1,cp.getTargetCity().getLoc());
                }
            }
            //if(i%20000==10)Tools.writeResultCoordinatesToFile(cityList,"mid_coordinate"+i);
        }
    }

    public double hasImprovement(List<City> cityList, int pos1, int pos2){
        int pos1suc = (pos1+1)%cityList.size();
        int pos2suc = (pos2+1)%cityList.size();

        double additionalCostOld = 0;
        double additionalCostNew = 0;

        for(int i=pos1suc;i<=pos2-1;i++){
            if(i%10==0){
                if(!cityList.get(i).isPrimeCity())additionalCostOld+=0.1*Tools.getEuclideanDistance(cityList.get(i),cityList.get(i+1));
                int newCityPosAtThisPos = pos2-(i-pos1suc);
                if(!cityList.get(newCityPosAtThisPos).isPrimeCity())additionalCostNew+=0.1*Tools.getEuclideanDistance(cityList.get(newCityPosAtThisPos),
                        cityList.get(newCityPosAtThisPos-1));
            }
        }
        if(pos1%10==0&&!cityList.get(pos1).isPrimeCity()){
            additionalCostOld+=0.1*Tools.getEuclideanDistance(cityList.get(pos1),cityList.get(pos1suc));
            additionalCostNew+=0.1*Tools.getEuclideanDistance(cityList.get(pos1),cityList.get(pos2));
        }
        if(pos2%10==0&&!cityList.get(pos2).isPrimeCity())additionalCostOld+=0.1*Tools.getEuclideanDistance(cityList.get(pos2),cityList.get(pos2suc));
        if(pos2%10==0&&!cityList.get(pos1suc).isPrimeCity())additionalCostNew+=0.1*Tools.getEuclideanDistance(cityList.get(pos1suc),cityList.get(pos2suc));
        double diff = Tools.getEuclideanDistance(cityList.get(pos1),cityList.get(pos2))
                + Tools.getEuclideanDistance(cityList.get(pos1suc),cityList.get(pos2suc))
                - Tools.getEuclideanDistance(cityList.get(pos1),cityList.get(pos1suc))
                - Tools.getEuclideanDistance(cityList.get(pos2),cityList.get(pos2suc))
                + additionalCostNew - additionalCostOld;
        return diff;
    }

    public void reverseSubList(List<City> cityList, int pos1suc, int pos2){
        if(cityList.size()<3)return;
        Tools.reverseSubSection(cityList,pos1suc,pos2);

    }

}
