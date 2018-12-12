package comparator;

import entity.City;

import java.util.Comparator;

/**
 * Created by 01378498 on 2018/10/18.
 * ULD 按照arrOrder从小到大排序
 */
public class ReverseIdCityComparator implements Comparator {
    public int compare(Object arg0, Object arg1) {
        City f0 = (City)arg0;
        City f1 = (City)arg1;
        if(f0.getId()<f1.getId()){
            return 1;
        }else if(f0.getId()>f1.getId()){
            return -1;
        }
        return 0;
    }
}
