package com.example.sachinlld.LoadBalancingAlgorithms;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class IPHashAlgorithm {
    private List<String> servers;

    public IPHashAlgorithm(List<String> servers) {
        this.servers = servers;
    }

    public String getNextServer(String clientIp) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(clientIp.getBytes());
            byte[] digest = md.digest();

            // Convert first 4 bytes of hash to an int (optional: full digest can be used)
            int hashValue = 0;
            for (int i = 0; i < 4; i++) {
                hashValue = (hashValue << 8) | (digest[i] & 0xFF);
            }

            // Use positive index
            int index = Math.abs(hashValue) % servers.size();
            return servers.get(index);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found");
        }
    }

    public static void main(String[] args) {
        List<String> servers = Arrays.asList("Server1", "Server2", "Server3");
        IPHashAlgorithm loadBalancer = new IPHashAlgorithm(servers);

        List<String> clientIps = Arrays.asList("192.168.0.1", "192.168.0.2", "192.168.0.3", "192.168.0.4");
        for (String ip : clientIps) {
            String server = loadBalancer.getNextServer(ip);
            System.out.println("Client " + ip + " -> " + server);
        }
    }
}
