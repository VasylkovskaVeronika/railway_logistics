package Classes.RailroadCars;

import Classes.Interfaces.ElectricNeedable;
import Classes.RailroadCar;
import Classes.RailroadCars.PostCars.MailCar;
import Classes.RailroadCars.PostCars.PostalBaggageCar;

public class RailroadPostOffice extends RailroadCar implements ElectricNeedable {
    MailCar mail;
    PostalBaggageCar baggage;

    public RailroadPostOffice() {

    }
}
