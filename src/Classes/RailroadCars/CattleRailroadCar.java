package Classes.RailroadCars;

import Classes.Interfaces.ElectricNotOrNeedable;
import Classes.RailroadCar;

public class CattleRailroadCar extends RailroadCar implements ElectricNotOrNeedable {
    int maxAnimalCount;
    boolean isDesinfected; // law requirement
    double minFeedAmount; // kg, law requirement
    double minWaterAmount; // l, law requirement
    double outsideTemp;

    public CattleRailroadCar(int mac, double mfa, double mwa, double ot) {
        this.isDesinfected=true;
        this.maxAnimalCount=mac;
        this.outsideTemp=ot;
        this.minFeedAmount=mfa;
        this.minWaterAmount=mwa;
    }


    public boolean isElectricDependent() {
        return (this.outsideTemp>30)? true: false;
    }
}
