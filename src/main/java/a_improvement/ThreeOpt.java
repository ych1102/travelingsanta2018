package a_improvement;

import auxilliarytools.Tools;
import entity.City;
import entity.CityPair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by 01378498 on 2018/12/11.
 */
public class ThreeOpt implements Improvement {
    public void improveRun(List<City> cityList, int maxCount){
        threeOpt(cityList,maxCount);
    }

    public void improveRunBatch(List<List<City>> zoneList, int iteration){

    }


    public void threeOpt(List<City> cityList, int maxCount){
        for (int i = 0; i < cityList.size(); i++) {
            cityList.get(i).setLoc(i);
        }
        for (int i = 0; i < cityList.size()-2; i++) {
            City currentCity = cityList.get(i);
            System.out.println("start to detect city:"+currentCity.getId()+" loc:"+currentCity.getLoc());
            List<CityPair> cityPairList = currentCity.getNearestCityPairList();
            for(CityPair cp1:cityPairList){
                if(cp1.getTargetCity().getLoc()<currentCity.getLoc()+2||cp1.getTargetCity().getLoc()==cityList.size()-1)continue;

                for(CityPair cp2:cp1.getTargetCity().getNearestCityPairList()){
                    if(cp2.getTargetCity().getLoc() < cp1.getTargetCity().getLoc()+2 || cp2.getTargetCity().getLoc()==cityList.size()-1) continue;

                    int pos1 = currentCity.getLoc();
                    int pos2 = cp1.getTargetCity().getLoc();
                    int pos3 = cp2.getTargetCity().getLoc();

                    int pos1suc = (pos1+1)%cityList.size();
                    int pos2suc = (pos2+1)%cityList.size();
                    int pos3suc = (pos3+1)%cityList.size();

                    if(hasImprovement4(cityList,pos1, pos2, pos3, pos1suc, pos2suc, pos3suc)) {
                       System.out.println(pos1+" "+pos1suc+" "+pos2+" "+pos2suc+" "+pos3+" "+pos3suc+" way4");
                        System.exit(1);

                    }


//                    if(hasImprovement1(cityList,pos1, pos2, pos3, pos1suc, pos2suc, pos3suc)){
//                        System.out.println(pos1+" "+pos1suc+" "+pos2+" "+pos2suc+" "+pos3+" "+pos3suc+" way1");
//                        System.exit(1);
//                        reverseSubList1(cityList, pos1, pos2, pos3, pos1suc, pos2suc, pos3suc);
//                    }else if(hasImprovement2(cityList,pos1, pos2, pos3, pos1suc, pos2suc, pos3suc)){
//                        System.out.println(pos1+" "+pos1suc+" "+pos2+" "+pos2suc+" "+pos3+" "+pos3suc+" way2");
//                        System.exit(1);
//                        reverseSubList2(cityList, pos1, pos2, pos3, pos1suc, pos2suc, pos3suc);
//                    }if(hasImprovement3(cityList,pos1, pos2, pos3, pos1suc, pos2suc, pos3suc)){
//                        System.out.println(pos1+" "+pos1suc+" "+pos2+" "+pos2suc+" "+pos3+" "+pos3suc+" way3");
//                        System.exit(1);
//                        reverseSubList3(cityList, pos1, pos2, pos3, pos1suc, pos2suc, pos3suc);
//                    }if(hasImprovement4(cityList,pos1, pos2, pos3, pos1suc, pos2suc, pos3suc)){
//                        System.out.println(pos1+" "+pos1suc+" "+pos2+" "+pos2suc+" "+pos3+" "+pos3suc+" way4");
//                        System.exit(1);
//                        reverseSubList4(cityList, pos1, pos2, pos3, pos1suc, pos2suc, pos3suc);
//                    }
                }
            }
            if(i%20000==10)Tools.writeResultCoordinatesToFile(cityList,"mid_coordinate"+i);
        }
    }

    public boolean hasImprovement1(List<City> cityList, int pos1, int pos2, int pos3, int pos1suc, int pos2suc, int pos3suc){
        double diff = Tools.getEuclideanDistance(cityList.get(pos1),cityList.get(pos2suc)) +
                Tools.getEuclideanDistance(cityList.get(pos1suc),cityList.get(pos3suc)) +
                Tools.getEuclideanDistance(cityList.get(pos2),cityList.get(pos3)) -
                Tools.getEuclideanDistance(cityList.get(pos1),cityList.get(pos1suc)) -
                Tools.getEuclideanDistance(cityList.get(pos2),cityList.get(pos2suc)) -
                Tools.getEuclideanDistance(cityList.get(pos3),cityList.get(pos3suc));
        return diff<0;

    }

    public void reverseSubList1(List<City> cityList, int pos1, int pos2, int pos3, int pos1suc, int pos2suc, int pos3suc){
        if(cityList.size()<3)return;
        //Tools.reverseSubSection(cityList,pos1suc,pos2);

        List<City> newCityList = new ArrayList<>();

        newCityList.addAll(cityList.subList(0,pos1+1));

        List<City> subCityList = cityList.subList(pos2suc,pos3+1);
        newCityList.addAll(subCityList);

        subCityList = cityList.subList(pos1suc,pos2+1);
        Collections.reverse(subCityList);
        newCityList.addAll(subCityList);

        subCityList = cityList.subList(pos3suc,cityList.size());
        newCityList.addAll(subCityList);

        cityList.clear();
        cityList.addAll(newCityList);
    }

    public boolean hasImprovement2(List<City> cityList, int pos1, int pos2, int pos3, int pos1suc, int pos2suc, int pos3suc){
        double diff = Tools.getEuclideanDistance(cityList.get(pos1),cityList.get(pos3)) +
                Tools.getEuclideanDistance(cityList.get(pos1suc),cityList.get(pos2suc)) +
                Tools.getEuclideanDistance(cityList.get(pos2),cityList.get(pos3suc)) -
                Tools.getEuclideanDistance(cityList.get(pos1),cityList.get(pos1suc)) -
                Tools.getEuclideanDistance(cityList.get(pos2),cityList.get(pos2suc)) -
                Tools.getEuclideanDistance(cityList.get(pos3),cityList.get(pos3suc));
        return diff<0;

    }

    public void reverseSubList2(List<City> cityList, int pos1, int pos2, int pos3, int pos1suc, int pos2suc, int pos3suc){
        if(cityList.size()<3)return;
        //Tools.reverseSubSection(cityList,pos1suc,pos2);

        List<City> newCityList = new ArrayList<>();

        newCityList.addAll(cityList.subList(0,pos1+1));

        List<City> subCityList = cityList.subList(pos2suc,pos3+1);
        Collections.reverse(subCityList);
        newCityList.addAll(subCityList);

        subCityList = cityList.subList(pos1suc,pos2+1);
        newCityList.addAll(subCityList);

        subCityList = cityList.subList(pos3suc,cityList.size());
        newCityList.addAll(subCityList);

        cityList.clear();
        cityList.addAll(newCityList);
    }

    public boolean hasImprovement3(List<City> cityList, int pos1, int pos2, int pos3, int pos1suc, int pos2suc, int pos3suc){
        double diff = Tools.getEuclideanDistance(cityList.get(pos1),cityList.get(pos2)) +
                Tools.getEuclideanDistance(cityList.get(pos1suc),cityList.get(pos3)) +
                Tools.getEuclideanDistance(cityList.get(pos3suc),cityList.get(pos2suc)) -
                Tools.getEuclideanDistance(cityList.get(pos1),cityList.get(pos1suc)) -
                Tools.getEuclideanDistance(cityList.get(pos2),cityList.get(pos2suc)) -
                Tools.getEuclideanDistance(cityList.get(pos3),cityList.get(pos3suc));
        return diff<0;

    }

    public void reverseSubList3(List<City> cityList, int pos1, int pos2, int pos3, int pos1suc, int pos2suc, int pos3suc){
        if(cityList.size()<3)return;
        //Tools.reverseSubSection(cityList,pos1suc,pos2);

        List<City> newCityList = new ArrayList<>();

        newCityList.addAll(cityList.subList(0,pos1+1));

        List<City> subCityList = cityList.subList(pos1suc,pos2+1);
        Collections.reverse(subCityList);
        newCityList.addAll(subCityList);

        subCityList = cityList.subList(pos2suc,pos3+1);
        Collections.reverse(subCityList);
        newCityList.addAll(subCityList);

        subCityList = cityList.subList(pos3suc,cityList.size());
        newCityList.addAll(subCityList);

        cityList.clear();
        cityList.addAll(newCityList);
    }

    public boolean hasImprovement4(List<City> cityList, int pos1, int pos2, int pos3, int pos1suc, int pos2suc, int pos3suc){
        double diff = Tools.getEuclideanDistance(cityList.get(pos1),cityList.get(pos2suc)) +
                Tools.getEuclideanDistance(cityList.get(pos1suc),cityList.get(pos3)) +
                Tools.getEuclideanDistance(cityList.get(pos2),cityList.get(pos3suc)) -
                Tools.getEuclideanDistance(cityList.get(pos1),cityList.get(pos1suc)) -
                Tools.getEuclideanDistance(cityList.get(pos2),cityList.get(pos2suc)) -
                Tools.getEuclideanDistance(cityList.get(pos3),cityList.get(pos3suc));
        return diff<0;

    }

    public void reverseSubList4(List<City> cityList, int pos1, int pos2, int pos3, int pos1suc, int pos2suc, int pos3suc){
        if(cityList.size()<3)return;
        //Tools.reverseSubSection(cityList,pos1suc,pos2);

        List<City> newCityList = new ArrayList<>();

        newCityList.addAll(cityList.subList(0,pos1+1));

        List<City> subCityList = cityList.subList(pos2suc,pos3+1);
        newCityList.addAll(subCityList);

        subCityList = cityList.subList(pos1suc,pos2+1);
        newCityList.addAll(subCityList);

        subCityList = cityList.subList(pos3suc,cityList.size());
        newCityList.addAll(subCityList);

        cityList.clear();
        cityList.addAll(newCityList);
    }
}
