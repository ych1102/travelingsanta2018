package entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityPair {
    private City c1;
    private City c2;
    private int index1;
    private int index2;

    private City currentCity;
    private City targetCity;
    private double dist;


    public String toString(){
        return this.c1+"("+this.index1+"),"+this.c2+"("+this.index2+")";
    }
}
