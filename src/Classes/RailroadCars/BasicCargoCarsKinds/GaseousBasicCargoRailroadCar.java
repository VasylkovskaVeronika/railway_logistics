package Classes.RailroadCars.BasicCargoCarsKinds;

import Classes.Interfaces.ElectricNotOrNeedable;
import Classes.Interfaces.Gaseousable;
import Classes.RailroadCars.BasicCargoRailroadCar;

public class GaseousBasicCargoRailroadCar extends BasicCargoRailroadCar
        implements ElectricNotOrNeedable, Gaseousable {
    boolean isDissolved; // is the gas transported in some solution
    boolean isCooled; // whether the gas needs to be cooled
    double pressure; //measured in kPa, which pressure needs to maintained for a gas to safe travel

    public GaseousBasicCargoRailroadCar(boolean isD, boolean isC, double p) {
        //super(te);
        this.isCooled=isC;
        this.isDissolved=isD;
        this.pressure=p;
    }

    @Override
    public boolean isElectricDependent() {
        // electricity for cooling or/and maintaining pressure is needed
        return (this.isCooled || this.pressure > 101.325)? true: false;
    }
}
