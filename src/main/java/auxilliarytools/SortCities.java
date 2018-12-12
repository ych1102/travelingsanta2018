package auxilliarytools;

import comparator.ReverseIdCityComparator;
import entity.City;

import java.util.Collections;
import java.util.List;

/**
 * Created by 01378498 on 2018/12/7.
 */
public class SortCities {
    /**
     * 把city按序号除1以外，按逆序排序
     * @param cityList
     */
    public static void sortByReverseOrder(List<City> cityList){
        City city0 = cityList.get(0);
        cityList.remove(0);
        Collections.sort(cityList,new ReverseIdCityComparator());
        cityList.add(0,city0);
    }
}
