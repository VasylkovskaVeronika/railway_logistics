package Classes.Exceptions;

import Classes.RailroadCar;

public class NoPlacesLeftException extends Throwable {
    RailroadCar problemCar;
    public NoPlacesLeftException(RailroadCar c) {
        //this.message="Attention! Too high speed!";
        this.problemCar=c;
    }
    public String toString() {
        return "Impossible to load! No places left! Problem with:\n"+
                this.problemCar.toString();
    }
    public void printStackTrace() {
        System.out.println( "\n##################################################"+
                "\nImpossible to load! Too heavy load! Problem with:\n"+
                this.problemCar.toString());
    }
}
