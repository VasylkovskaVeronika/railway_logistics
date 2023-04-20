package Classes;

import Classes.RailroadCars.BasicCargoCarsKinds.GaseousBasicCargoRailroadCar;
import Classes.RailroadCars.BasicCargoCarsKinds.IsothermalBasicCargoRailroadCar;
import Classes.RailroadCars.BasicCargoCarsKinds.LiquidBasicCargoRailroadCar;
import Classes.RailroadCars.CattleRailroadCar;
import Classes.RailroadCars.HeavyCargoCarsKinds.ExplosivesHeavyCargoRailroadCar;
import Classes.RailroadCars.HeavyCargoCarsKinds.LiquidToxicHeavyCargoRailroadCar;
import Classes.RailroadCars.HeavyCargoCarsKinds.ToxicHeavyCargoRailroadCar;
import Classes.RailroadCars.PassengerRailroadCar;
import Classes.RailroadCars.PostCars.MailCar;
import Classes.RailroadCars.PostCars.PostalBaggageCar;
import Classes.RailroadCars.RailroadPostOffice;
import Classes.RailroadCars.RailroadRestaurant;
import Classes.Threads.StateRecordingThread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.groupingBy;

public class Menuu {
    static List<RailwayStation> stations=new ArrayList<>();
    static List<Connection> connections=new ArrayList<>();
    //static List<RailroadCar> cars=new ArrayList<>();
    static List<Locomotive> heads=new ArrayList<>();
    List<TrainSet> trains=new ArrayList<>();

    public Locomotive createLocomotive() { //without parameters
        return new Locomotive();
    }
    public Locomotive createLocomotive
            (double s, RailwayStation f, RailwayStation t, double mlw, int mcn, int encn, RailwayStation h) {
        return new Locomotive(s, f, t, mlw, mcn, encn, h);
    }
    public RailwayStation createStation(String n) {
        return new RailwayStation(n);
    }
//    public Connection createConnection() {
//        return new Connection();
//    }
    public GaseousBasicCargoRailroadCar createGaseousRailroadCar(boolean isD, boolean isC, double p) {
        return new GaseousBasicCargoRailroadCar(isD, isC, p);
    }
    public IsothermalBasicCargoRailroadCar createIsothermalRailroadCar(double t) {
        return new IsothermalBasicCargoRailroadCar(t);
    }
    public LiquidBasicCargoRailroadCar createLiquidRailroadCar() {
        return new LiquidBasicCargoRailroadCar();
    }
    public ExplosivesHeavyCargoRailroadCar createExplosivesRailroadCar(boolean rP, double vTNT) {
        return new ExplosivesHeavyCargoRailroadCar(rP, vTNT);
    }
    public ToxicHeavyCargoRailroadCar createToxicRailroadCar(double mf, String sn) {
        return new ToxicHeavyCargoRailroadCar(mf, sn);
    }
    public LiquidToxicHeavyCargoRailroadCar createLiquidToxicRailroadCar
            (boolean hSHJ, String m, double mf, String sn) {
        return new LiquidToxicHeavyCargoRailroadCar(hSHJ, m, mf, sn);
    }
    public PostalBaggageCar createPostBagCar() {
        return new PostalBaggageCar();
    }
    public MailCar createMailCar() {
        return new MailCar();
    }
    public RailroadPostOffice createPostOfficeCar(){
        return new RailroadPostOffice();
    }
    public CattleRailroadCar createCattleCar(int mac, double mfa, double mwa, double ot) {
        return new CattleRailroadCar(mac, mfa, mwa, ot);
    }
    public PassengerRailroadCar createPassengerCar(int ms, int cbs) {
        return new PassengerRailroadCar(ms, cbs);
    }
    public RailroadRestaurant createRestaurantCar(int ms, int cbs) {
        return new RailroadRestaurant(ms, cbs);
    }

    //generation
    public void generateStationList() {
        //stations
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        for (int i=0; i<100; i++) {
            stations.add(new RailwayStation(random.ints(leftLimit, rightLimit + 1)
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString()));
            if(i>2) {
//                int anotherStationNumber=0; //second station is always connected with the first one
//                //for(int j=0; j<i; j++) {
//                if (i > 1) { //any further station is connected randomly
                    int anotherStationNumber=i-1;//random.nextInt(i - 1);
                    Connection potensialToConn=new Connection(stations.get(i).getName(),
                            stations.get(anotherStationNumber).getName(),
                            Math.floor(Math.random() * (2 - 1 + 1) + 1));
                    int anotherSecondStationNumber=random.nextInt(i - 2);
                    Connection potensialBackConn=new Connection(stations.get(anotherSecondStationNumber).getName(),
                            stations.get(i).getName(), Math.floor(Math.random() * (2 - 1 + 1) + 1));
                //}
                    connections.add(potensialToConn);
                    connections.add(potensialBackConn);
            }
        }
        //for the first three stations
        for(int i=0; i< 3; i++) {
            int anotherStationNumber=(int)Math.floor(Math.random() * (stations.size()-1 - 3 + 1) + 3);
            Connection potensialToConn=new Connection(stations.get(i).getName(),
                    stations.get(anotherStationNumber).getName(),
                    Math.floor(Math.random() * (2 - 1 + 1) + 1));
            int anotherSecondStationNumber=(int)Math.floor(Math.random() * (stations.size()-1 - 3 + 1) + 3);
            Connection potensialBackConn=new Connection(stations.get(anotherSecondStationNumber).getName(),
                    stations.get(i).getName(), Math.floor(Math.random() * (2 - 1 + 1) + 1));
            //}
            if(Connection.notContainEqualOrOppositeConnection(potensialToConn, connections) &&
                    Connection.notContainEqualOrOppositeConnection(potensialBackConn, connections) &&
                    anotherStationNumber!=anotherSecondStationNumber) {
                connections.add(potensialToConn);
                connections.add(potensialBackConn);
            }
            else {
                i--;
            }
        }
        //test stations
        stations.forEach(System.out::println);
    }
//    public void generateConnectionList() {
//        Random random = new Random();
//        //connections
////        while(Menu.isAnyStationWithoutConnection(connections, stations)) {
////            RailwayStation st1=stations.get(random.nextInt(stations.size()));
////            RailwayStation st2=stations.get(random.nextInt(stations.size()));
////            if(st1!=st2) {
////                connections.add(new Connection(st1.getName(), st2.getName(),
////                        Math.floor(Math.random() * (25 - 5 + 1) + 5)));  //max-25km, min-5km
////            }
////        }
//        for(int i=0; i< stations.size(); i++) {
//            for(int j=0; j< stations.size(); j++) {
//                if(i!=j) {
//                    connections.add(new Connection(stations.get(j).getName(),
//                            stations.get(i).getName(), Math.floor(Math.random() * (25 - 5 + 1) + 5)));
//                    //max-25km, min-5km
//                    connections.add(new Connection(stations.get(i).getName(),
//                            stations.get(j).getName(), Math.floor(Math.random() * (25 - 5 + 1) + 5)));
//                }
//            }
//        }
//        for(int i=1; i< stations.size(); i++) {
//            connections.add(new Connection(stations.get(i).getName(),
//                    stations.get(i-1).getName(), Math.floor(Math.random() * (10 - 5 + 1) + 5)));  //max-25km, min-5km
//        }
//        for(int i=0; i< stations.size(); i++) {
//            if(i!=random.nextInt(stations.size()-1)) {
//                connections.add(new Connection(stations.get(random.nextInt(stations.size())).getName(),
//                        stations.get(i).getName(), Math.floor(Math.random() * (10 - 5 + 1) + 5)));  //max-25km, min-5km
//
//            }
//        }
//        //test connections
//        connections.forEach(System.out::println);
//    }
    public void generateLocomotiveList() {
        Random random = new Random();
        for (int i=0; i<25; i++) {
            int j=random.nextInt(stations.size()-1);
            int mcn=(int)Math.floor(Math.random() * (10 - 5 + 1) + 5);
            if(j!=i) {
                heads.add(new Locomotive(Math.floor(Math.random() * (200 - 150 + 1) + 150), stations.get(i),
                        stations.get(j), Math.floor(Math.random() * (200000 - 100000 + 1) + 100000),
                        mcn, random.nextInt(mcn), stations.get(random.nextInt(stations.size() - 1))));
            }
            else {
                i--;
            }
        }
        //test heads
        heads.forEach(System.out::println);
    }
    public List<RailroadCar> generateRailroadCarsListForTrainSet() {
        Random random = new Random();
        List<RailroadCar> c=new ArrayList<>();
            switch (random.nextInt(1)) {
                case 0: //passenger train set
                    for(int i=0; i<7;i++) {
                        switch (random.nextInt(2)) {
                            case 0:
                                c.add(new PassengerRailroadCar(54, 0));
                                break;
                            case 1:
                                c.add(new RailroadRestaurant(81, 0));
                                break;
                            case 2:
                                c.add(new RailroadPostOffice());
                                c.add(new MailCar());
                                c.add(new PostalBaggageCar());
                                break;
                        }
                    }
                    break;
                case 1: //cargo train set
                    for(int i=0; i<10;i++) {
                        switch (random.nextInt(6)) {
                            case 0:
                                c.add(new ToxicHeavyCargoRailroadCar(80.5, "P"));
                                break;
                            case 1:
                                c.add(new LiquidToxicHeavyCargoRailroadCar(false, "Fluorocarbon",
                                        99.9, "HF"));
                                break;
                            case 2:
                                c.add(new ExplosivesHeavyCargoRailroadCar(false, 1000));
                                break;
                            case 3:
                                c.add(new GaseousBasicCargoRailroadCar(true, false, 101));
                                break;
                            case 4:
                                c.add(new IsothermalBasicCargoRailroadCar(25.5));
                                break;
                            case 5:
                                c.add(new LiquidBasicCargoRailroadCar());
                                break;
                            case 6:
                                c.add(new CattleRailroadCar(25, 15, 15, 20));
                                break;
                        }
                    }
                    break;
            }
       c.forEach(car -> {car.setGrossWeight(Math.floor(Math.random() * (20000 - 15000 + 1) + 15000));
               car.setCurrentWeight(Math.floor(Math.random() * (car.grossWeight - 15000 + 1) + 15000));});
        return c;
    }
    public void generateTrainSetList(){
        Random random = new Random();
        for(int i=0; i<25; i++) {
            Locomotive l=heads.get(random.nextInt(heads.size()-2));
            Route r= Route.findShortestWay(connections, l.source, l.destination);
            TrainSet t=new TrainSet(r, l);
            generateRailroadCarsListForTrainSet().forEach(c-> t.attachCar(c));
            trains.add(t);
        }
        //test trains
        trains.forEach(System.out::println);
    }
    public void generation() {
        this.generateStationList();
        //this.generateConnectionList();
        this.generateLocomotiveList();
        this.generateTrainSetList();
    }
    public void startTransfers() {
//        TrainSet.startTransfers(this.trains.get(0));
//        TrainSet.startTransfers(this.trains.get(1));
        this.trains.forEach(t->TrainSet.startTransfers(t));
    }
    public static Route changingReversePath(Route pr) {
        Route rr=Route.findAnyRoute(connections, new Route(),
                pr.connections.get(pr.connections.size()-1).to, pr.connections.get(0).from);
        return rr;
    }

    public static void writeTrainSetSummary(TrainSet train) {
        System.out.println(train.toString());
    }
    public void startWritingInfoToFile() {
        StateRecordingThread t=new StateRecordingThread(trains);
        t.run();
    }

    //removal
    public void removeTrainSet(TrainSet t) {
        trains.removeIf(tr-> tr.getId()==t.getId());
    }
    public void removeLocomotive(Locomotive l) {
        trains.removeIf(t-> t.getHead().id==l.id);
        heads.removeIf(h-> l.id==h.id);
    }
    public void removeCarriagesListOfTrainSet(TrainSet t) {
        trains.forEach(tr-> {if(tr.getId()==t.getId()) tr.cars.clear();});
    }
    public void removeCarriage(RailroadCar c) {
        trains.forEach(t-> t.cars.removeIf(car->c.id==car.id));
    }
    public void removeConnection(Connection con) {
        trains.removeIf(t-> t.getPath().connections.stream().
                filter(c -> c.getFrom().name.equals(con.from.name) && c.getTo().name.equals(con.to.name)).count()>0);
        connections.removeIf(c -> c.getFrom().name.equals(con.from.name) && c.getTo().name.equals(con.to.name));
    }
    public void removeStation(RailwayStation st) {
        connections.forEach(c-> {
            if(c.getFrom().name.equals(st.name))
                removeConnection(c);
            if(c.getTo().name.equals(st.name))
                removeConnection(c);
        });
        stations.removeIf(s->st.name.equals(s.name));
    }

//    public static boolean stationWithoutConnection(List<Connection> cs, RailwayStation s) {
//        return (cs.stream().filter(c->(c.from==s || c.to==s)).count()>0)? false: true;
//    }
//    public static boolean isAnyStationWithoutConnection(List<Connection> cs, List<RailwayStation> ss) {
//        return (ss.stream().filter(s -> stationWithoutConnection(cs, s)).count()>0)? true: false;
//    }

}
