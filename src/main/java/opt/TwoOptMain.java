package opt;

import a_improvement.Improvement;
import a_improvement.TwoOpt;
import entity.City;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TwoOptMain implements Improvement {
    @Override
    public void improveRun(List<City> cityList, int algorithmParam) {

        TwoOptResult bestResult = null;

        for(int i=0;i<cityList.size();i+=50){
            ExecutorService es = Executors.newFixedThreadPool(50);
            List<Future<TwoOptResult>> resultList = new ArrayList<>();

            for(int j=i;j<Math.min(cityList.size(),i+50);j++){
                TwoOptOperator operator = new TwoOptOperator(cityList, cityList.get(j));
                Future<TwoOptResult> result = es.submit(operator);
                resultList.add(result);
            }

            for(Future<TwoOptResult> result:resultList){
                try {
                    TwoOptResult currentResult = result.get();
                    if(currentResult.save < 0){
                        if(bestResult == null){
                            bestResult = currentResult;
                        }else if(currentResult.save < bestResult.save){
                            bestResult = currentResult;
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }

            es.shutdown();

            if(bestResult != null){
                break;
            }
        }

        if(bestResult != null){
            for(int i=bestResult.city3.getCurrentIndex()-1;i>bestResult.city2.getCurrentIndex();i--){
                cityList.get(i).setSucceedingCity(cityList.get(i-1));
                cityList.get(i).setProceedingCity(cityList.get(i+1));
            }
            bestResult.city1.setSucceedingCity(bestResult.city3);
            bestResult.city3.setProceedingCity(bestResult.city1);
            bestResult.city3.setSucceedingCity(cityList.get(bestResult.city3.getCurrentIndex()-1));
            bestResult.city2.setSucceedingCity(bestResult.city4);
            bestResult.city2.setProceedingCity(cityList.get(bestResult.city2.getCurrentIndex()+1));
            bestResult.city4.setProceedingCity(bestResult.city2);

            City depot = cityList.get(0);
            cityList.clear();
            cityList.add(depot);
            depot.setCurrentIndex(0);
            while(true){
                City currentCity = depot.getSucceedingCity();
                if(currentCity != depot){
                    cityList.add(currentCity);
                    currentCity.setCurrentIndex(cityList.size());
                }else {
                    break;
                }
            }


        }
    }

    @Override
    public void improveRunBatch(List<List<City>> zoneList, int algorithmParam) {

    }
}
