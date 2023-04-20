package Classes.Threads;

import Classes.*;
import Classes.Exceptions.CollisionException;

import java.util.ArrayList;
import java.util.List;


public class TrainMovingThread extends Thread {
    TrainSet train;
    String monitor=new String();

    public TrainMovingThread(TrainSet t) {
        this.train=t;
        System.out.println("\n->->->->->->->->->->Transfer starting for train: ->->->->->->->->->->\n");
        System.out.println(this.train+"\n");
    }
    public void run() {
        synchronized (monitor) {
            while (train.getMovement() < train.getPath().getWholeDistanceToLastStation()) {
//            System.out.println(train.getPath().getWholeDistanceToLastStation());
//            System.out.println(train.getMovement());
                System.out.println(train.getPath().percentToNearestStations(train.getMovement()));
                if (train.getPath().percentToNearestStations(train.getMovement()) < 10) {
                    if (train.getPath().getCurrentconnection().getTo().getName().
                            equals(train.getHead().getDestination().getName())) {
                        LastStationStopThread lastStopThread = new LastStationStopThread(train);
                        lastStopThread.start();
                        try {
                            lastStopThread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        train.reversePath();
                        System.out.println("\n<-<-<-<-<-<-<-<-<-<-Start moving by reversed route<-<-<-<-<-<-<-<-<-<-\n"+
                                "Train number: "+train.getId()+"\n On the way to "+train.getHead().getDestination());
                        //break;
                    } else {
                        try {train.checkCollision(); }
                        catch (CollisionException e) {
                            e.printStackTrace();
                            train.getPath().getNextConnection().addNewWaiting(train);
                            System.out.println("\n************Collision prevention stop*************\n"+
                                    "Train number: "+train.getId()+"\n On the way to "+train.getHead().getDestination()+
                                    "\n Stopped due to the busy connection: "+this.train.getPath().getNextConnection());
                            this.interrupt();
                            break;
                        }
                        double leftToStationDistance=train.getPath().restToNearestDistance(train.getMovement());
                        train.expandMovement(leftToStationDistance);
                        train.getPath().moveConnection();
                        StationStopThread stopThread = new StationStopThread(train);
                        stopThread.start();
                        try {
                            stopThread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //train.changeSpeed(15); // train at some starting speed after a complete stop
                    }
                } else {
                    try {
                        TrainMovingThread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    train.changeSpeed((Math.random() < 0.5) ?
                            train.getHead().getSpeed() + train.getHead().getSpeed() * 0.03 :
                            train.getHead().getSpeed() - train.getHead().getSpeed() * 0.03);
                    train.expandMovement(train.getHead().getSpeed() / 3600);
//                    System.out.println("\n---------------------Speed change-----------------------\n");
//                    System.out.println("\n Train set number "+train.getId()+
//                            "\nNow speed is " + train.getHead().getSpeed() +
//                            "\n percent to the next station is " +
//                            train.getPath().percentToNearestStations(train.getMovement()) +
//                            "\n distance to the next station is " +
//                            train.getPath().restToNearestDistance(train.getMovement()));
                }
            }
            System.out.println("\n Test part moving Thread ended");
            this.interrupt();
        }
    }

//    public static void main(String[] args)
//    {
//
////        List<Connection> stations = new ArrayList<Connection>();
////        stations.add(new Connection("Starting point", "Middle point", 0.1));
////        stations.add(new Connection("Middle point", "Finish point", 0.1));
////        Route r=new Route(stations);
////        Locomotive l=new Locomotive(50, stations.get(0).getFrom(), stations.get(stations.size()-1).getTo(),
////                150000, 10, 5, new RailwayStation("Home"));
////        TrainSet train =new TrainSet(r, l);
////        TrainMovingThread myThread = new TrainMovingThread(train);
////        myThread.start();
//        // Starting the thread
//        //myThread.start();
//    }
}
