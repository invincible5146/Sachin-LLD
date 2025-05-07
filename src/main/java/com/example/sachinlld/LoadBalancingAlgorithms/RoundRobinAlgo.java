package com.example.sachinlld.LoadBalancingAlgorithms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RoundRobinAlgo {
    private final List<String> servers;
    private int currentIndex;

    /**
     * Constructor to intialize values
     * @param servers
     */
    public RoundRobinAlgo(List<String> servers) {
        this.servers =new ArrayList<>();
        this.currentIndex = -1;
        this.servers.addAll(servers);
    }

    /**
     * Method to get the server from the server list
     * @return
     */
    public String getServer() {
        this.currentIndex = (this.currentIndex+1)%this.servers.size();
        return servers.get(this.currentIndex);
    }

    public static void main(String[] args) {
        List<String> servers = new ArrayList<>();
        servers.add("Server 1");
        servers.add("Server 2");
        servers.add("Server 3");
        RoundRobinAlgo roundRobinAlgo = new RoundRobinAlgo(servers);
        System.out.println(roundRobinAlgo.getServer());
        System.out.println(roundRobinAlgo.getServer());
        System.out.println(roundRobinAlgo.getServer());
        System.out.println(roundRobinAlgo.getServer());
    }

}
