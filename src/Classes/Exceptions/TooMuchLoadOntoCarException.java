package Classes.Exceptions;

import Classes.RailroadCar;

public class TooMuchLoadOntoCarException extends Throwable {
    //String message;
    RailroadCar problemCar;
    public TooMuchLoadOntoCarException(RailroadCar c) {
        //this.message="Attention! Too high speed!";
        this.problemCar=c;
    }
    public String toString() {
        return "Impossible to load! Too heavy load! Problem with:\n"+
                this.problemCar.toString();
    }
    public void printStackTrace() {
        System.out.println( "\n##################################################"+
                "\nImpossible to load! Too heavy load! Problem with:\n"+
                this.problemCar.toString());
    }
}
