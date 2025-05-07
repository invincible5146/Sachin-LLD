package com.example.sachinlld.LoadBalancingAlgorithms;

import java.util.*;

public class LeastResponseTimeAlgo {
    private List<String> servers;
    private List<Double> responseTimes;

    public LeastResponseTimeAlgo(List<String> servers) {
        this.servers = servers;
        this.responseTimes = new ArrayList<>(Collections.nCopies(servers.size(), 0.0));
    }

    public String getNextServer() {
        double minResponseTime = Collections.min(responseTimes);
        int minIndex = responseTimes.indexOf(minResponseTime);
        return servers.get(minIndex);
    }

    public void updateResponseTime(String server, double responseTime) {
        int index = servers.indexOf(server);
        if (index != -1) {
            responseTimes.set(index, responseTime);
        }
    }

    // Simulate server response time
    public static double simulateResponseTime() {
        Random random = new Random();
        double delay = 0.1 + (1.0 - 0.1) * random.nextDouble(); // Random between 0.1 and 1.0
        try {
            Thread.sleep((long)(delay * 1000)); // Convert to milliseconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return delay;
    }

    public static void main(String[] args) {
        List<String> servers = Arrays.asList("Server1", "Server2", "Server3");
        LeastResponseTimeAlgo loadBalancer = new LeastResponseTimeAlgo(servers);

        for (int i = 0; i < 6; i++) {
            String server = loadBalancer.getNextServer();
            System.out.println("Request " + (i + 1) + " -> " + server);
            double responseTime = simulateResponseTime();
            loadBalancer.updateResponseTime(server, responseTime);
            System.out.printf("Response Time: %.2fs%n", responseTime);
        }
    }
}
