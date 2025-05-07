package com.example.sachinlld.LoadBalancingAlgorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WeightedRoundRobinAlgo {
    private final List<String> servers;
    private final List<Integer>weights;
    private int currentIndex;
    private int currentWeight;

    /**
     * Constructor to initialize values
     * @param servers
     * @param weights
     */
    public WeightedRoundRobinAlgo(List<String> servers, List<Integer> weights) {
        this.servers = servers;
        this.weights = weights;
        this.currentIndex=-1;
        this.currentWeight=0;
    }

    /**
     * Method to get the server with high weightage
     * @return
     */
    public String getServer() {
        while (true) {
            this.currentIndex = (this.currentIndex + 1) % servers.size();
            //if this is first time we are getting the server
            if (this.currentIndex==0) {
                //decrease the current weight of the current server
                this.currentWeight-=1;
                //if decreasing the weight becomes less than zero, means we have exhauseted this server
                //find the server with high weight again
                if (this.currentWeight<=0) {
                    this.currentWeight = Collections.max(weights);
                }
            }
            //if the server with high weight is still less than the server with current weight return the maximum one
            if (this.currentWeight<=weights.get(this.currentIndex)) {
                return servers.get(currentIndex);
            }
        }
    }

    public static void main(String[] args) {
        List<String>servers=new ArrayList<>();
        servers.add("Server 1");
        servers.add("Server 2");
        servers.add("Server 3");
        List<Integer>weights = new ArrayList<>();
        weights.add(5);
        weights.add(1);
        weights.add(1);
        WeightedRoundRobinAlgo algo=new WeightedRoundRobinAlgo(servers,weights);
        for(int i=0;i<7;i++) {
            System.out.println(algo.getServer());
        }
    }
}
