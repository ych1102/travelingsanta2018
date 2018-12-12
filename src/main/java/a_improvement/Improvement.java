package a_improvement;

import entity.City;

import java.util.List;

/**
 * Created by 01378498 on 2018/12/10.
 */
public interface Improvement {
    void improveRun(List<City> cityList, int algorithmParam);
    void improveRunBatch(List<List<City>> zoneList, int algorithmParam);


}
