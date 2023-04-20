package Classes.Exceptions;

import Classes.Connection;
import Classes.TrainSet;

public class CollisionException extends Exception {
    TrainSet problemSet;
    Connection problemCon;
    public CollisionException(TrainSet tSet, Connection c) {
        this.problemSet=tSet;
        this.problemCon=c;
    }
    public String toString() {
        return "Attention! Collision with another train set on connection: "+this.problemCon+"! Problem with:\n"+
                this.problemSet.toString();
    }
    public void printStackTrace() {
        System.out.println( "\n##################################################"+
                "\nAttention! Collision with another train set on connection: "+this.problemCon+"! Problem with:\n"+
                this.problemSet.toString());
    }
}
