package Classes;

import Classes.Exceptions.NonExistingStationException;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;

public class Route {
    //Map<String, Double> stations; // station Name, distance to the next one
    List<Connection> connections;
    Connection currentconnection;

    public Route() {
        this.connections=new ArrayList<>();
        this.currentconnection=null;
    }

    public Route(List<Connection> conlist) {
        this.connections=conlist;
        this.currentconnection=this.connections.get(0);
    }
    public double getDistanceToNearestStation() { //????
        return currentconnection.distance;
    }
    public double getWholeDistanceToLastStation() {
        double wholeDistance=0;
        for (Connection c: connections
             ) {
            wholeDistance+=c.distance;
        }
        return wholeDistance;
    }
    public Connection getNextConnection() {
        if(this.connections.indexOf(currentconnection)==this.connections.size()-1) {
            Route testR=this;
            Menuu.changingReversePath(testR);
            return testR.connections.get(0);
        }
        else {
            return this.connections.get(this.connections.indexOf(currentconnection)+1);
        }
    }
    public void moveConnection() {
        this.currentconnection.release();
        this.currentconnection=getNextConnection();
        this.currentconnection.isBusy=true;
    }
    public RailwayStation nextStation() {
        return currentconnection.to;
    }
    public double restToNearestDistance(double distance) { // how much is left to the nearest station
        double absD=0;
        if(currentconnection==null) {
            this.currentconnection=this.connections.get(0);
        }
        Connection from=connections.get(0);
        while(!from.equals(currentconnection)) {
            absD+=from.distance;
            from=connections.get(connections.indexOf(from)+1);
        }
        absD+=currentconnection.distance;
        return absD-distance;
    }
    public int percentToNearestStations(double distance) { // percent of distance left to the nearest station
        return (int)((restToNearestDistance(distance)*100)/currentconnection.distance);
    }
    public double percentOnWholeWay(double distance) {
        return ((100*distance)/getWholeDistanceToLastStation());
    }
    public Connection getCurrentconnection() {
        return currentconnection;
    }
//    public static void reverseRoute(Route r) {
////        Collections.reverse(r.connections);
////        RailwayStation tmp;
////        for (Connection c:r.connections
////             ) {
////            tmp=c.from;
////            c.from=c.to;
////            c.to=tmp;
////        }
//    }
    public static void checkIsStationExists(List<Connection> allPossibleConnections, RailwayStation st)
            throws NonExistingStationException {
        List<Connection> connectionsFrom=allPossibleConnections.stream().filter(c -> c.getFrom()==st).toList();
        List<Connection> connectionsTo=allPossibleConnections.stream().filter(c -> c.getTo()==st).toList();
        if(connectionsFrom.size()<0) {
            throw new NonExistingStationException(st.name);

        }
        else if (connectionsTo.size()<0) {
            throw new NonExistingStationException(st.name);

        }
    }
    public static Route findShortestWay(List<Connection> allPossibleConnections, RailwayStation from, RailwayStation to)
            {
        try {
            checkIsStationExists(allPossibleConnections, from);
            checkIsStationExists(allPossibleConnections, to);
        }
        catch(NonExistingStationException e) {
            e.printStackTrace();
        }
        List<Route> rs=new ArrayList<>();
        Route r=new Route();
        //List<Route> allR=findAllRoutes(rs, allPossibleConnections, r, from, to);
        //return allR.stream().sorted(Comparator.comparingDouble(Route::getLength)).limit(2).toList().get(0);
        return findAnyRoute(allPossibleConnections, r, from, to);
//                List<Connection> stations = new ArrayList<Connection>();
//        stations.add(new Connection("Starting point", "Middle point", 0.1));
//        stations.add(new Connection("Middle point", "Finish point", 0.1));
//        return new Route(stations);
    }
    // to find all possible routes from A to B
    public static List<Route> findAllRoutes(List<Route> routes, List<Connection> allCon,
                                            Route cr, RailwayStation from, RailwayStation to) {
        List<Connection> approprCon=
                allCon.stream().filter(c->c.getFrom().getName().equals(from.getName())).toList();
        if(approprCon.size()>0) { //means this connection is not dead end one
            for (Connection c : approprCon
            ) {
                if (c.getTo() == to) {
                    cr.connections.add(c);
                    routes.add(cr);
                    cr.connections.clear();
                } else {
                    Route r = cr;
                    r.connections.add(c);
                    findAllRoutes(routes, allCon, r, c.getTo(), to);
                }
            }
        }
        return routes;
    }
    public static Route findAnyRoute(List<Connection> allCon,
                                            Route cr, RailwayStation from, RailwayStation to) {
        List<Connection> approprCon=
                allCon.stream().filter(c->c.getFrom().getName().equals(from.getName())).toList();
        if(approprCon.size()>0) { //means this connection is not dead end one
            List<Connection> rightToDestCon=
                    approprCon.stream().filter(c->c.getTo().getName().equals(to.getName())).toList();
            if(!rightToDestCon.isEmpty()){
//            for (Connection c : approprCon
//            ) {
//                if (c.getTo() == to) {
                    cr.connections.add(rightToDestCon.get(0));
                    return cr;
//                }
            }
            else {
                int counter=0;
                while(!Connection.notContainConnectionWithStation(approprCon.get(counter), cr.connections)) {
                    if(counter< approprCon.size()-1)
                        counter++;
                    else {
                        Connection newlyDiscoveredRoad=new Connection(from.getName(), to.getName(),
                                Math.floor(Math.random() * (2 - 1 + 1) + 1));
                        //approprCon.add(newlyDiscoveredRoad);
                        allCon.add(newlyDiscoveredRoad);
                        return findAnyRoute(allCon, cr, from, to);
                    }
                }
                cr.connections.add(approprCon.get(counter));
                return findAnyRoute(allCon, cr, approprCon.get(counter).getTo(), to);
            }
        }
        return null;
    }
    public double getLength() {
        return connections.stream().collect(groupingBy((Connection::getFrom), summingDouble(Connection::getDistance))).
                values().stream().reduce(0.0, (x,y) -> x+y);
    }


    //    public RailwayStation getSource() {
//        return new RailwayStation(stations.keySet().stream().toList().get(0));
//    }
//    public RailwayStation getDest() {
//        return new RailwayStation(stations.keySet().stream().toList().get(stations.size()-1));
//    }
}
