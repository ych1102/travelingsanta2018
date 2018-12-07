package entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * Created by 01378498 on 2018/12/5.
 */
@Getter
@Setter
public class City {
    private int id;
    private double X;
    private double Y;
    private boolean isPrimeCity;
    private double distanceToPrevCity; //not consider 10%
    private double insertionCost;
    private int loc;
    private City nearestCity;

    public String toString(){
        return this.getId()+"("+this.getX()+","+this.getY()+")"+(this.isPrimeCity?"primeCity":"");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return id == city.id &&
                Double.compare(city.X, X) == 0 &&
                Double.compare(city.Y, Y) == 0 &&
                isPrimeCity == city.isPrimeCity;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, X, Y, isPrimeCity);
    }
}