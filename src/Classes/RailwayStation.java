package Classes;

public class RailwayStation {
    String name;

    public RailwayStation(String n) {
        this.name=n;
    }
    public String getName() {
        return name;
    }
    public String toString() {
        return "railway station \""+this.name+"\"";
    }
}
