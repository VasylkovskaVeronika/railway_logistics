package Classes.Threads;

import Classes.*;

import java.util.ArrayList;
import java.util.List;

public class LastStationStopThread extends Thread {
    TrainSet train;
    String monitor=new String();

    public LastStationStopThread(TrainSet t) {
        this.train = t;
    }

    public void run() {
        synchronized (monitor) {
            System.out.println("\n~~~~~~~~~~~~~~~~~~Destination station stop~~~~~~~~~~~~~~~~~~~~~~\n" +
                    "Train number "+train.getId()+" came to the destination " + train.getHead().getDestination());
            try {
                this.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //System.out.println(train + "\n Test part last station Thread ended");
//            Route.reverseRoute(train.getPath());
//            System.out.println("\n-------------------------------------------------------------\n" +
//                    train + "\n Starts moving by reversed route " + train.getHead().getDestination());
            this.interrupt();
        }
    }

//    public static void main(String[] args) {
//
//
//        // Starting the thread
//        //myThread.start();
//    }
}
