package Classes;

import Classes.Threads.TrainMovingThread;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Connection {
    RailwayStation from;
    RailwayStation to;
    double distance;
    boolean isBusy;
    Queue<TrainSet> waitingToRelease;

    public Connection(String nameF, String nameT, double distance) {
        this.from=new RailwayStation(nameF);
        this.to=new RailwayStation(nameT);
        this.distance=distance;
        this.isBusy=false;
        this.waitingToRelease=new PriorityQueue<>();
    }

    public RailwayStation getFrom() {
        return from;
    }

    public RailwayStation getTo() {
        return to;
    }

    public double getDistance() {
        return distance;
    }

    public Queue<TrainSet> getWaitingToRelease() {
        return waitingToRelease;
    }

   public void addNewWaiting(TrainSet t) {
        this.waitingToRelease.add(t);
   }

    public void release() {
        if(waitingToRelease.isEmpty()) {
            isBusy=false;
        }
        else {
            TrainSet firstInQueue=waitingToRelease.poll();
            firstInQueue.getPath().moveConnection();
            TrainMovingThread t=new TrainMovingThread(firstInQueue);
            t.start();
        }
    }
    public static boolean notContainConnectionWithStation(Connection con, List<Connection> conL) {
        long sc=conL.stream().
                filter(c -> c.getFrom().name.equals(con.to.name)).count();
        return sc==0;
    }
    public static boolean notContainEqualOrOppositeConnection(Connection con, List<Connection> conL) {
        long sc=conL.stream().
                filter(c -> c.getFrom().name.equals(con.from.name) && c.getTo().name.equals(con.to.name)).count();
        Connection opC=oppositeConnection(con);
        long sco=conL.stream().
                filter(c -> c.getFrom().name.equals(opC.from.name) && c.getTo().name.equals(opC.to.name)).count();
        return sc==0 && sco==0;
    }
    public static Connection oppositeConnection(Connection c) {
        Connection opC=new Connection(c.to.name, c.from.name, c.distance);
       return opC;
    }

    @Override
    public String toString() {
        return "from=" + from + " to=" + to;
    }
}
