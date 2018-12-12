package opt;

import a_improvement.Improvement;
import auxilliarytools.Tools;
import entity.City;
import entity.CityPair;

import java.util.List;
import java.util.concurrent.Callable;

public class TwoOptOperator implements Callable<TwoOptResult> {
    private List<City> tour;
    private City currentCity;

    private boolean isPrimeConsidered = true;

    public TwoOptOperator(List<City> tour, City currentCity){
        this.tour = tour;
        this.currentCity = currentCity;
    }

    @Override
    public TwoOptResult call() throws Exception {
        double radius = Tools.getEuclideanDistance(currentCity,currentCity.getSucceedingCity());

        double gain = 0;
        for(CityPair cp:currentCity.getNearestCityPairList()){
            if(cp.getTargetCity().getCurrentIndex() <= currentCity.getCurrentIndex()) continue;

            double distC1toC2 = Tools.getEuclideanDistance(currentCity,cp.getTargetCity());
            if(radius > distC1toC2){
                gain = -radius-Tools.getEuclideanDistance(cp.getTargetCity(),cp.getTargetCity().getSucceedingCity())
                        +distC1toC2+Tools.getEuclideanDistance(currentCity.getSucceedingCity(),cp.getTargetCity().getSucceedingCity());

                if(isPrimeConsidered) {
                    int startIndex = (currentCity.getSucceedingCity().getCurrentIndex()/10)*10;
                    if(startIndex < currentCity.getSucceedingCity().getCurrentIndex()){
                        startIndex += 10;
                    }

                    for(int j=startIndex;j<=cp.getTargetCity().getCurrentIndex();j+=10){
                        City cityJ = tour.get(j);
                        if(!cityJ.isPrimeCity()){
                            gain -= 0.1*Tools.getEuclideanDistance(cityJ,cityJ.getSucceedingCity());
                        }

                        City cityK = tour.get(cp.getTargetCity().getCurrentIndex()-(j-currentCity.getCurrentIndex()));
                        if(!cityK.isPrimeCity()){
                            gain += 0.1*Tools.getEuclideanDistance(cityK,cityK.getProceedingCity());
                        }
                    }
                }
            }
        }

        return null;
    }
}
