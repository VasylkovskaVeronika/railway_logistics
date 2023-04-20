package Classes.Exceptions;

import Classes.TrainSet;

public class NonExistingStationException extends Throwable {
    //String message;
    String problemStationName;
    public NonExistingStationException(String statName) {
        //this.message="Attention! Too high speed!";
        this.problemStationName=statName;
    }
    public String toString() {
        return "Impossible to find the route! Non-existing station! Problem with:\n"+
                this.problemStationName;
    }
    public void printStackTrace() {
        System.out.println( "\n##################################################"+
                "\nImpossible to find the route! Non-existing station! Problem with:\n"+
                this.problemStationName);
    }
}
