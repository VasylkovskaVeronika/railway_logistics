package Classes.RailroadCars;

import Classes.Interfaces.ElectricNeedable;
import Classes.RailroadCar;

public class RailroadRestaurant extends PassengerRailroadCar implements ElectricNeedable {
    public RailroadRestaurant(int msn, int cbs) {
        super(msn, cbs);
    }
}
