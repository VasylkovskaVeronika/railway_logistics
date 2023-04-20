package Classes.RailroadCars.BasicCargoCarsKinds;

import Classes.Interfaces.ElectricNeedable;
import Classes.Interfaces.Isothermalable;
import Classes.RailroadCars.BasicCargoRailroadCar;



public class IsothermalBasicCargoRailroadCar extends BasicCargoRailroadCar
        implements ElectricNeedable, Isothermalable {
    double temperature; //Celsius
    //хладогент
    //замораживать

    public IsothermalBasicCargoRailroadCar(double t) {
        this.temperature=t;
    }
}
