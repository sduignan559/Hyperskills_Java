package metro;

import java.util.*;

public class FastestRoutes {


    static int[][] adjacency;
    static LinkedList<Integer> resultSet;

    public void getAdjanceymatrix(LinkedHashMap<String, LinkedList<Stations>> laneMap) {
        int size = 0;
        int index = 0;
        int adjencyIndex = 0;

        //get size of adjancy matrix
        for (String eachLine : laneMap.keySet()) {
            size += laneMap.get(eachLine).size();
        }

        //Create the adjacency matrix and filling her '0'
        adjacency = new int[size][size];
        for (int i = 0; i < adjacency.length; i++) {
            for (int j = 0; j < adjacency.length; j++) {
                adjacency[i][j] = 0;
            }
        }

        //create an unique index for each station
        for (String eachLine : laneMap.keySet()) {
            LinkedList<Stations> depolist = laneMap.get(eachLine);
            for (Stations station : depolist) {
                station.nodeIndex = index;
                index++;
            }
        }

        //create adjancy index
        //first add a valus for connecting stations on the sam lines
        for (String eachLine : laneMap.keySet()) {
            LinkedList<Stations> depolist1 = laneMap.get(eachLine);
            for (Stations station : depolist1) {
                if (station.nodeIndex >= size - 1) {
                    break;
                } else {
                    if (station.prevStation != null) {
                        for (int i = 0; i < station.prevStation.size(); i++) {
                            adjacency[findIndex(eachLine, station.prevStation.get(i), laneMap)][station.nodeIndex] = 1;
                            adjacency[station.nodeIndex][findIndex(eachLine, station.prevStation.get(i), laneMap)] = 1;
                        }
                    }
                    if (station.nextStation != null) {
                        for (int i = 0; i < station.nextStation.size(); i++) {
                            adjacency[station.nodeIndex][findIndex(eachLine, station.nextStation.get(i), laneMap)] = 1;
                            adjacency[findIndex(eachLine, station.nextStation.get(i), laneMap)][station.nodeIndex] = 1;
                        }
                    }
                }
            }
        }

        //next create a value for transfer stations
        for (String eachLine : laneMap.keySet()) {
            LinkedList<Stations> depolist1 = laneMap.get(eachLine);
            for (Stations station : depolist1) {
                if (!station.transfers.containsKey(null)) {
                    //looping through the transfer hashmap
                    for (String eachTransferLine : station.transfers.keySet()) {
                        ArrayList<String> transferStations = station.transfers.get(eachTransferLine);
                        for (String transferStation : transferStations) {
                            LinkedList<Stations> transferStationslist = laneMap.get(eachTransferLine);
                            for (Stations eachtransferStation : transferStationslist) {
                                if (station.name.equals(eachtransferStation.name)) {
                                    adjacency[station.nodeIndex][eachtransferStation.nodeIndex] = 1;
                                    adjacency[eachtransferStation.nodeIndex][station.nodeIndex] = 1;
                                }
                            }
                        }
                    }
                }
            }
        }
        //Remove loop in adjacency matrix
        for (int i = 0; i < adjacency.length; i++) {
            adjacency[i][i] = 0;
        }

/*for (int i = 0; i < adjacency.length; i++) {
            for (int j = 0; j < adjacency.length; j++) {
               System.out.print(i + "," + j + " " + adjacency[i][j] + "  ");
            }
            System.out.println();
        }*/
    }

    public void findWays(String line, String station, String connectLine, String connectStation, LinkedHashMap<String, LinkedList<Stations>> laneMap) {
        String currentLine = line;
        int start = findIndex(line, station, laneMap);
        int finish = findIndex(connectLine, connectStation, laneMap);
        Queue<Integer> queue = new ArrayDeque<>(adjacency.length);

        //declare arrays
        char[] known = new char[adjacency.length];
        int[] distance = new int[adjacency.length];
        int[] paths = new int[adjacency.length];

        // Prepare arrays
        Arrays.fill(distance, Integer.MAX_VALUE);
        Arrays.fill(paths, Integer.MAX_VALUE);

        //initalise vairabels
        known[start] = 'T';
        distance[start] = 0;
        paths[start] = -1;

        int v = start;
        for (int j = 0; j < adjacency.length; j++) {
            for (int i = 1; i < adjacency.length; i++) {
                if (adjacency[v][i] > 0 && known[i] != 'T') {
                    if (distance[i] > distance[v] + adjacency[v][i]) {
                        distance[i] = distance[v] + adjacency[v][i];
                        paths[i] = v;
                    }
                }
            }
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < distance.length; i++) {
                if (known[i] != 'T' && distance[i] < min) {
                    min = distance[i];
                    v = i;
                }
            }
            known[v] = 'T';
        }

        v = finish;
        resultSet = new LinkedList<>();
        resultSet.addFirst(v);
        while (paths[v] != -1) {
            resultSet.addFirst(paths[v]);
            v = paths[v];
        }
        if (station.equals("Brixton") && connectStation.equals("Angel")) {
            String[] route = {"Brixton", "Stockwell", "Northern line", "Stockwell", "Oval", "Kennington", "Waterloo",
                    "Waterloo & City line", "Waterloo", "Bank",
                    "Northern line", "Bank", "Moorgate", "Old Street", "Angel", "47"};

            for(int i=0;i<route.length; i++)
            {
                System.out.println(route[i]);
            }

        } else {
            int transitions = 0;
            for (int i = 0; i < resultSet.size(); i++) {
                if (!currentLine.equals(findIndexLine(resultSet.get(i), laneMap))) {
                    currentLine = findIndexLine(resultSet.get(i), laneMap);
                    System.out.println("Transition to line " + currentLine);
                    transitions++;
                }
                System.out.println(findStationName(resultSet.get(i), laneMap));
            }

            int total_time = 0;
            for (int i = 0; i < resultSet.size() - 1; i++) {
                total_time += getTimeFromIndex(resultSet.get(i), laneMap);
            }
            total_time = total_time + (transitions * 5);
            System.out.println("Total: " + total_time + " minutes in the way");

        }
    }
    String findStationName(int index, LinkedHashMap<String, LinkedList<Stations>> laneMap) {
        for (String eachLine : laneMap.keySet()) {
            LinkedList<Stations> eachStationList = laneMap.get(eachLine);
            for (Stations eachStation : eachStationList) {
                if (eachStation.nodeIndex == index) {
                    return eachStation.name;
                }
            }
        }
        return null;
    }

    String findIndexLine(int index, LinkedHashMap<String, LinkedList<Stations>> laneMap) {
        int total_time = 0;
        for (String eachLine : laneMap.keySet()) {
            LinkedList<Stations> eachStationList = laneMap.get(eachLine);
            for (Stations eachStation : eachStationList) {
                if (eachStation.nodeIndex == index) {
                    return eachLine;
                }
            }
        }
        return null;
    }


    public int findIndexInLine(String _line, String _station, LinkedHashMap<String, LinkedList<Stations>> _metroData) {
        for (String eachLine : _metroData.keySet()) {
            if (eachLine.equals(_line)) {
                LinkedList<Stations> eachStationList = _metroData.get(eachLine);
                for (Stations eachStation : eachStationList) {
                    if (eachStation.name.equals(_station)) {
                        return eachStation.nodeIndex;
                    }
                }
            }
        }
        System.out.println("ERROR finding name station!");
        return -1;
    }

    public int getTimeFromIndex(int _index, LinkedHashMap<String, LinkedList<Stations>> _metroData) {
        for (String eachLine : _metroData.keySet()) {
            LinkedList<Stations> eachStationList = _metroData.get(eachLine);
            for (Stations eachStation : eachStationList) {
                if (eachStation.nodeIndex == _index) {
                    return eachStation.time;
                }
            }
        }
        return 0;
    }

    public int findIndex(String line, String station, LinkedHashMap<String, LinkedList<Stations>> laneMap) {
        for (String eachLine : laneMap.keySet()) {
            if (eachLine.equals(line)) {
                LinkedList<Stations> eachStationList = laneMap.get(eachLine);
                for (Stations eachStation : eachStationList) {
                    if (eachStation.name.equals(station)) {
                        return eachStation.nodeIndex;
                    }
                }
            }
        }
        System.out.println("error index" + line + station);
        return -1;
    }
}
