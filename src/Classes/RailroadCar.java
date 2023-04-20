package Classes;

import Classes.Exceptions.NoPlacesLeftException;
import Classes.Exceptions.TooMuchLoadOntoCarException;
import Classes.Interfaces.ElectricNeedable;
import Classes.Interfaces.ElectricNotOrNeedable;
import Classes.RailroadCars.PassengerRailroadCar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;

public abstract class RailroadCar {
    int id;
    private static int uniqueIdMaker=0;
    double netWeight; // kg, empty car
    double grossWeight; // kg, full car
    double currentWeight;
    //shipper
    //boolean travelsEmpty; // to discover final weight for this travel

    public RailroadCar() {
        this.id=uniqueIdMaker++;
        //this.travelsEmpty=te;
        this.currentWeight=netWeight;
    }

    public boolean getElectricDependent() {
        return (this instanceof ElectricNeedable ||
                (this instanceof ElectricNotOrNeedable) && ((ElectricNotOrNeedable) this).isElectricDependent());
    }
    public double getTotalWeight() {
        return currentWeight;
    }

    public void checkCargoOntoCar(double weight) throws NoPlacesLeftException, TooMuchLoadOntoCarException {
        if(this.isPassenger()) {
            PassengerRailroadCar.checkPlaces((PassengerRailroadCar) this, (int)(weight/80));
        }
        if(this.currentWeight+weight>grossWeight) {
            throw new TooMuchLoadOntoCarException(this);
        }
    }
    public void loadCargoOntoCar(double weight) {
        try {
            checkCargoOntoCar(weight);
        }
        catch (NoPlacesLeftException e) {
            e.printStackTrace();
        }
        catch (TooMuchLoadOntoCarException e) {
            e.printStackTrace();
        }
        this.currentWeight+=weight;
    }

    public static double sumFullLoadtransported(List<RailroadCar> cars) {
        return cars.stream().collect(groupingBy(RailroadCar::getId,
                summingDouble(RailroadCar::getTotalWeight))).values().stream().reduce(0.0, (x,y) -> x+y);
    }
    public static double sumLoadtransported(List<RailroadCar> cars) {
        return cars.stream().collect(groupingBy(RailroadCar::getId,
                summingDouble(RailroadCar::getTotalWeight))).values().stream().reduce(0.0, (x,y) -> x+y);
    }
    public static int peopleCountByLoadtransported(List<RailroadCar> cars) {
        return (int)(cars.stream().filter(c -> c.isPassenger()).collect(groupingBy(RailroadCar::getId,
                summingDouble(RailroadCar::getTotalWeight))).values().stream().reduce(0.0, (x,y) -> x+y)/80);
    }
    public static String summaryAboutLoad(List<RailroadCar> cars) {
        if(cars.get(0).isPassenger()) {
            return "number of people based on the goods transported: "+ RailroadCar.peopleCountByLoadtransported(cars)
                    +".\n"+ "summary load transported: "+ RailroadCar.sumLoadtransported(cars)+".\n";
        }
        else {
            return "summary load transported: "+ RailroadCar.sumLoadtransported(cars)+".\n";
        }
    }

    public void setGrossWeight(double grossWeight) {
        this.grossWeight = grossWeight;
    }
    public void setCurrentWeight(double currentWeight) {
        this.currentWeight=currentWeight;
    }

    public int getId() {
        return id;
    }
    public boolean isPassenger() {
        return this instanceof PassengerRailroadCar;
    }

    public String toString() { //this.getClass().toString() ?
        return this.id+" "+this.getClass().getCanonicalName()+" "
                +this.getTotalWeight()+"kg\n";
    }
    public static String carsListToString(List <RailroadCar> cars) {
        String res = null;
        res=cars.stream().sorted(Comparator.comparingDouble(RailroadCar::getTotalWeight)).map(c->c.toString())
                .collect(Collectors.joining("-", "{", "}"));;
        return res;
    }
}
