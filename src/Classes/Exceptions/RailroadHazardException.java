package Classes.Exceptions;

import Classes.TrainSet;

public class RailroadHazardException extends Exception {
    //String message;
    TrainSet problemSet;
    public RailroadHazardException(TrainSet tSet) {
        //this.message="Attention! Too high speed!";
        this.problemSet=tSet;
    }
    public String toString() {
        return "Attention! Too high speed! Problem with:\n"+
                this.problemSet.toString();
    }
    public void printStackTrace() {
        System.out.println( "\n##################################################"+
                "\nAttention! Too high speed! Problem with:\n"+
                this.problemSet.toString());
    }

}
