package Classes.Threads;

import Classes.RailroadCar;
import Classes.TrainSet;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class StateRecordingThread extends Thread{
    Path stateFile;
    List<TrainSet> allTrains=new ArrayList<>();
    String monitor=new String();

    public StateRecordingThread(List<TrainSet> t) {
        this.allTrains=t;
    }
    public void run() {
        synchronized (monitor) {
            try {
                StationStopThread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try (var out=new PrintWriter("AppState.txt", StandardCharsets.UTF_8)) {
                writeTrain(out, allTrains);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void writeTrain(PrintWriter out, List<TrainSet> t) {
        out.println(TrainSet.trainsListToString(t));
    }
}
