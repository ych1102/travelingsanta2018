package auxilliarytools;

import entity.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 01378498 on 2018/12/7.
 */
public class Removal {
    public static List<City> removeByInterval(List<City> tour, int start, int end){
        List<City> removedList = new ArrayList<>();
        while(start<end && start<tour.size() && tour.size()>0){
            System.out.println("remove:"+tour.get(start));
            removedList.add(tour.get(start));
            tour.remove(start);

            end--;
        }
        return removedList;
    }

}
