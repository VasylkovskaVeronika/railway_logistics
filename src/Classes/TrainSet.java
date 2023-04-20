package Classes;

import Classes.Exceptions.CarsLimitExceededException;
import Classes.Exceptions.CollisionException;
import Classes.Exceptions.RailroadHazardException;
import Classes.Exceptions.TooMuchLoadException;
import Classes.Threads.LastStationStopThread;
import Classes.Threads.StationStopThread;
import Classes.Threads.TrainMovingThread;
import Classes.Menuu;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;

public class TrainSet implements Comparable {
    private static int uniqueIdMaker=0;
    int id;
    Locomotive head;
    List<RailroadCar> cars;
    //int maxCarsNumber;
    // double maxLoadWeight; // kg
    double movement; //km
    Route path;

    public TrainSet(Route r, Locomotive l) {
        this.id=uniqueIdMaker++;
        //this.maxCarsNumber=mcn;
        this.cars=new ArrayList<>();
        //this.maxLoadWeight=mlw;
        this.head=l;
        this.movement=0;
        this.path=r;
        this.path.currentconnection=r.connections.get(0);
        this.path.currentconnection.isBusy=true;
    }

    public int getId() {
        return this.id;
    }

    public double getMovement() {
        return movement;
    }
    public void setToZeroMovement() {
        this.movement=0;
    }

    public void expandMovement(double addedDistance) {
        this.movement+=addedDistance;
//        if(this.movement>=this.path.getWholeDistanceToLastStation()) {
//            LastStationStopThread lastStopThread = new LastStationStopThread(this);
//            lastStopThread.start();
//        }
//            if (this.movement >= this.path.getDistanceToNearestStation()) {
//                if(this.path.currentconnection.to.equals(this.head.destination)) {
//                    LastStationStopThread lastStopThread = new LastStationStopThread(this);
//                    lastStopThread.start();
//                }
//                else {
//                    StationStopThread stopThread = new StationStopThread(this);
//                    stopThread.start();
//                    this.path.moveConnection();
//                    TrainMovingThread movingThread=new TrainMovingThread(this);
//                    movingThread.start();
//                }
//            }
//            else {
//                TrainMovingThread movingThread=new TrainMovingThread(this);
//                movingThread.start();
//            }
    }



    public void checkSpeed() throws RailroadHazardException {
        if(this.head.speed>200) {
            throw new RailroadHazardException(this);
        }
    }
    public void changeSpeed(double newSpeed) {
        this.head.speed=newSpeed;
        try {
            checkSpeed();
        }
        catch(RailroadHazardException e){
            e.printStackTrace();
        }
    }

    public void checkCollision() throws CollisionException {
        if(this.path.getNextConnection().isBusy){
            throw new CollisionException(this, this.path.getNextConnection());
        }
    }

    public void reversePath() {
        Route rr=Menuu.changingReversePath(this.path);
        this.path=rr;
        this.movement=0;
        RailwayStation tmp=this.getHead().source;
        this.getHead().source=this.getHead().destination;
        this.getHead().destination=tmp;
        this.path.currentconnection=this.path.connections.get(0);
    }

    public Route getPath() {
        return path;
    }

    public Locomotive getHead() {
        return head;
    }

    public List<RailroadCar> getCars() {
        return cars;
    }

    public void checkLoad(double additionalLoad) throws TooMuchLoadException {
//        double sumLoad=this.cars.stream().collect(groupingBy(RailroadCar::getId,
//                summingDouble(RailroadCar::getTotalWeight))).values().stream().reduce(0.0, (x,y) -> x+y);
        double sumLoad=RailroadCar.sumFullLoadtransported(this.cars);
        if(sumLoad+additionalLoad>this.head.maxLoadWeight) {
            throw new TooMuchLoadException(this);
        }
    }
    public void checkCarLimit() throws CarsLimitExceededException {
        if(this.cars.size()==this.head.maxCarsNumber) {
            throw new CarsLimitExceededException(this);
        }
    }
    public void attachCar(RailroadCar c) {
        try{
            checkLoad(c.getTotalWeight());
            checkCarLimit();
        }
        catch(TooMuchLoadException e) {
            e.printStackTrace();
        }
        catch(CarsLimitExceededException e) {
            e.printStackTrace();
        }
        this.cars.add(c);
    }

    public static void startTransfers(TrainSet train) {
        TrainMovingThread myThread = new TrainMovingThread(train);
        myThread.start();
        //train.notifyAll();
    }

    public String toString() {
        return "Train set number "+id+".\n"+
                "Driven by the locomotive "+head.name+" #"+head.id+".\n"+
                "Containing of "+cars.size()+" railroad cars.\n"+
                "Travelling from "+this.head.source+" to "+this.head.destination+
                ".\n% of the distance completed between the starting and destination railway stations: "+
                path.percentOnWholeWay(movement)+".\n"+
                "summary about railroad cars: "+
                RailroadCar.carsListToString(this.getCars())+".\n"+
                "number of railroad cars: "+
                cars.size()+".\n"+
                "summary about load transported: "+
                RailroadCar.summaryAboutLoad(this.cars)+".\n"+
                "% of the distance completed between the nearest railway stations on your route: "+
                (100-path.percentToNearestStations(movement));
    }
    public static String trainsListToString(List <TrainSet> trains) {
        String res = null;
        trains.sort((TrainSet t1, TrainSet t2)-> (t1.getPath().getLength()>t2.getPath().getLength()? 1:0));
        for (TrainSet t:trains) {
            res+=RailroadCar.carsListToString(t.getCars());
            res+=t.toString();
        }
        res=trains.stream().map(t->t.toString()).collect(Collectors.joining("-", "{", "}"));
        return res;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
