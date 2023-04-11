package metro;

import java.util.*;

public class ShortestRoutes {
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
                    if(station.prevStation != null) {
                        for (int i = 0; i < station.prevStation.size(); i++) {
                            adjacency[findIndex(eachLine, station.prevStation.get(i), laneMap)][station.nodeIndex] = 1;
                            adjacency[station.nodeIndex][findIndex(eachLine, station.prevStation.get(i), laneMap)] = 1;
                        }
                    }
                    if(station.nextStation != null)
                    {
                        for(int i = 0; i<station.nextStation.size(); i++) {
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

/*   for (int i = 0; i < adjacency.length; i++) {
            for (int j = 0; j < adjacency.length; j++) {
               System.out.print(i + "," + j + " " + adjacency[i][j] + "  ");
            }
            System.out.println();
        }*/
    }

        public void findWays (String line, String station, String connectLine, String connectStation, LinkedHashMap<String, LinkedList<Stations>> laneMap) {
            String currentLine = line;
            int start = findIndex(line, station, laneMap);
            int finish = findIndex(connectLine, connectStation, laneMap);



                Queue<Integer> queue = new ArrayDeque<>(adjacency.length);
                queue.add(start);

                char[] used = new char[adjacency.length];
                int[] paths = new int[adjacency.length];
                used[start] = 'T';
                paths[start] = -1;


                while (!queue.isEmpty()) {
                    int v = queue.poll();
                    for (int i = 0; i < adjacency.length; i++) {
                        for (int j = 0; j < adjacency.length; j++) {

                            if (used[i] != 'T' && adjacency[v][i] == 1) {
                                used[i] = 'T';
                                queue.add(i);
                                paths[i] = v;
                            }
                        }
                    }
                }

                List<Integer> currentPath = new ArrayList<>();
                for (int v = finish; v != -1; v = paths[v]) {
                    currentPath.add(v);
                }

                for (int i = currentPath.size() - 1; i >= 0; i--) {
                    if (!currentLine.equals(findIndexLine(currentPath.get(i), laneMap))) {
                        currentLine = findIndexLine(currentPath.get(i), laneMap);
                        System.out.println("Transition to line " + currentLine);
                    }

                    System.out.println(findStationName(currentPath.get(i), laneMap));
                }
            }
        


    /**
     * Auxiliary method for searching
     */
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

    String findStationName(int index, LinkedHashMap<String, LinkedList<Stations>> laneMap)
    {
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

    String findIndexLine(int index, LinkedHashMap<String, LinkedList<Stations>> laneMap)
    {
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
/*

    public String findLine(String nodeIndex, LinkedHashMap<String, LinkedList<Stations>> laneMap) {
        for (String eachLine : laneMap.keySet()) {

                    }
                }
        System.out.println("ERROR finding name line!");
        return "ERROR";
    }

    public int findStationName(String line, String station, LinkedHashMap<String, LinkedList<Stations>> laneMap) {
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
        System.out.println("ERROR finding name station!");
        return -1;
    }
*/

}


