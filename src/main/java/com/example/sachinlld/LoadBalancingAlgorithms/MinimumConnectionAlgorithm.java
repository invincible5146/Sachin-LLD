package com.example.sachinlld.LoadBalancingAlgorithms;

import java.util.*;

public class MinimumConnectionAlgorithm {
    private Map<String, Integer> servers;

    /**
     * Constructor to intialize values
     * @param serverList
     */
    public MinimumConnectionAlgorithm(List<String> serverList) {
        servers = new HashMap<>();
        for (String server : serverList) {
            servers.put(server, 0);
        }
    }

    /**
     * Method to get next server
     * @return
     */
    public String getNextServer() {
        // Find the minimum number of connections
        int minConnections = Collections.min(servers.values());

        // Get all servers with the minimum number of connections
        List<String> leastLoadedServers = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : servers.entrySet()) {
            if (entry.getValue() == minConnections) {
                leastLoadedServers.add(entry.getKey());
            }
        }

        // Select a random server from the least loaded ones
        Random random = new Random();
        String selectedServer = leastLoadedServers.get(random.nextInt(leastLoadedServers.size()));
        servers.put(selectedServer, servers.get(selectedServer) + 1);
        return selectedServer;
    }

    /**
     * Method to release connect from a server
     * @param server
     */
    public void releaseConnection(String server) {
        if (servers.containsKey(server) && servers.get(server) > 0) {
            servers.put(server, servers.get(server) - 1);
        }
    }

    public static void main(String[] args) {
        List<String> servers = Arrays.asList("Server1", "Server2", "Server3");
        MinimumConnectionAlgorithm loadBalancer = new MinimumConnectionAlgorithm(servers);
        for (int i = 0; i < 6; i++) {
            String server = loadBalancer.getNextServer();
            System.out.println("Request " + (i + 1) + " -> " + server);
            loadBalancer.releaseConnection(server);
        }
    }
}
