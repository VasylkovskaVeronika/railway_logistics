package Classes.RailroadCars;

import Classes.Exceptions.NoPlacesLeftException;
import Classes.Interfaces.ElectricNeedable;
import Classes.RailroadCar;

public class PassengerRailroadCar extends RailroadCar implements ElectricNeedable {
    int maxSeatsNumber;
    int currentlyBookedSeats;

    public PassengerRailroadCar(int msn, int cbs) {
        this.maxSeatsNumber=msn;
        this.currentlyBookedSeats=cbs;
        this.setGrossWeight(maxSeatsNumber*80);
        this.setCurrentWeight(currentlyBookedSeats*80);
    }
    public static void checkPlaces(PassengerRailroadCar c, int newlyBooked) throws NoPlacesLeftException {
        if(c.currentlyBookedSeats+newlyBooked>c.maxSeatsNumber) {
            throw new NoPlacesLeftException(c);
        }
    }
}
