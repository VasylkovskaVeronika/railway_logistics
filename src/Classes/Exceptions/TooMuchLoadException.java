package Classes.Exceptions;

import Classes.TrainSet;

public class TooMuchLoadException extends Exception{
    //String message;
    TrainSet problemSet;
    public TooMuchLoadException(TrainSet tSet) {
        //this.message="Attention! Too high speed!";
        this.problemSet=tSet;
    }
    public String toString() {
        return "Impossible to attach! Too heavy load! Problem with:\n"+
                this.problemSet.toString();
    }
    public void printStackTrace() {
        System.out.println( "\n##################################################"+
                "\nImpossible to attach! Too heavy load! Problem with:\n"+
                this.problemSet.toString());
    }
}
