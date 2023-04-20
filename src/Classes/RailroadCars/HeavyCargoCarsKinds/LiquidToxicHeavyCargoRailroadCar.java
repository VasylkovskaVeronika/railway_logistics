package Classes.RailroadCars.HeavyCargoCarsKinds;

import Classes.Interfaces.ElectricNotOrNeedable;
import Classes.Interfaces.Liquidable;
import Classes.RailroadCars.BasicCargoCarsKinds.LiquidBasicCargoRailroadCar;

public class LiquidToxicHeavyCargoRailroadCar extends ToxicHeavyCargoRailroadCar
implements Liquidable, ElectricNotOrNeedable {
    boolean hasSteamHeatingJacket; // used sometimes to keep in liquid aggregate condition
    String insideMaterial; //tank interior material, for acids for example

    public LiquidToxicHeavyCargoRailroadCar(boolean hSHJ, String m, double mf, String sn) {
        super(mf, sn);
        this.hasSteamHeatingJacket=hSHJ;
        this.insideMaterial=m;
    }

    @Override
    public boolean isElectricDependent() {
        return this.hasSteamHeatingJacket ? true : false;
    }
}
