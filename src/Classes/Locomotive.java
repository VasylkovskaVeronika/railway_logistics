package Classes;

public class Locomotive {
    int id;
    private static int uniqueIdMaker=0;
    String name;
    RailwayStation home;
    double speed; // km/h
    RailwayStation source;
    RailwayStation destination;
    int electricNeedCarsNumber;
    double maxLoadWeight; // kg
    int maxCarsNumber;

    public Locomotive() {
        this.id=uniqueIdMaker++;
        this.speed=70;
        this.name="\"Locomotive #"+this.id+"\"";
    }
    public Locomotive(double s, RailwayStation f, RailwayStation t, double mlw, int mcn, int encn, RailwayStation h) {
        this.id=uniqueIdMaker++;
        this.name="\"Locomotive #"+this.id+"\"";
        this.speed=s;
        this.source=f;
        this.home=h;
        this.destination=t;
        this.maxCarsNumber=mcn;
        this.electricNeedCarsNumber=encn;
        this.maxLoadWeight=mlw;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public RailwayStation getDestination() {
        return destination;
    }

    public int getId() {
        return id;
    }
}
