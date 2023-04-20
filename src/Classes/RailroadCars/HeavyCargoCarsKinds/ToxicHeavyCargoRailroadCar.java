package Classes.RailroadCars.HeavyCargoCarsKinds;

import Classes.Interfaces.Toxicable;
import Classes.RailroadCars.HeavyCargoRailroadCar;

public class ToxicHeavyCargoRailroadCar extends HeavyCargoRailroadCar implements Toxicable {
    //boolean isHermetic; //requirement for such cars ?
    double maxFill; //maximum percentage of filling the car with toxic stuff, it can't be completely filled
    String SubstanceName;

    public ToxicHeavyCargoRailroadCar(double mf, String sn) {
        this.maxFill=mf;
        this.SubstanceName=sn;
    }

    public void setSubstanceName(String substanceName) {
        SubstanceName = substanceName;
    }

    public String getSubstanceName() {
        return SubstanceName;
    }

    public void setMaxFill(double maxFill) {
        this.maxFill = maxFill;
    }

    public double getMaxFill() {
        return maxFill;
    }
}
