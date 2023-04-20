package Classes.RailroadCars.HeavyCargoCarsKinds;

import Classes.Interfaces.Explosivesable;
import Classes.RailroadCars.HeavyCargoRailroadCar;

public class ExplosivesHeavyCargoRailroadCar  extends HeavyCargoRailroadCar implements Explosivesable {
    boolean doesRequirePhlegmatizer; //phlegmatizer - sedative for some explosives
    double volumeInTNTequivalent;

    public ExplosivesHeavyCargoRailroadCar(boolean rP, double vTNT){
        this.doesRequirePhlegmatizer=rP;
        this.volumeInTNTequivalent=vTNT;
    }
}
