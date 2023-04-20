package Classes.Threads;

import Classes.*;

import java.util.ArrayList;
import java.util.List;

public class StationStopThread extends Thread{
    TrainSet train;
    String monitor=new String();

    public StationStopThread(TrainSet t) {
        this.train = t;
    }
    public void run() {
        synchronized (monitor) {
            System.out.println("\n^^^^^^^^^^^^^^^^^^^^^^^^^Regular station stop^^^^^^^^^^^^^^^^^^^^^^^^^\n" +
                    "Train number "+train.getId() + " stopped at the station " +
                    train.getPath().getCurrentconnection().getFrom());
            //train.changeSpeed(0);
            try {
                this.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("stop thread ended");
            this.interrupt();
        }
    }
//    public static void main(String[] args) {
//
////        List<Connection> stations = new ArrayList<Connection>();
////        stations.add(new Connection("Starting point", "Middle point", 10));
////        stations.add(new Connection("Middle point", "Finish point", 15));
////        Route r = new Route(stations);
////        Locomotive l = new Locomotive(50, stations.get(0).getFrom(), stations.get(stations.size() - 1).getTo(),
////                150000, 10, 5, new RailwayStation("Home"));
////        TrainSet train = new TrainSet(r, l);
////        TrainMovingThread myThread = new TrainMovingThread(train);
////        myThread.start();
//        // Starting the thread
//        //myThread.start();
//    }
}
