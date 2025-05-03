package com.example.sachinlld.ConsistentHashing;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.util.*;

@Slf4j
public class ConsistentHashing {
    private final int numberOfReplicas; // number of virtual nodes per server
    private final TreeMap<Long,String> ring; //Hash ring storing virtual nodes
    private final Set<String> servers; //physical servers

    //Constructor to initialize the values like replicas,ring and unique servers
    public ConsistentHashing(int numberOfReplicas, List<String> servers) {
        this.numberOfReplicas = numberOfReplicas;
        this.ring = new TreeMap<>();
        this.servers = new HashSet<>();

        for (String server:servers) {
            addServer(server);
        }
    }

    /**
     * Method to calculate the hash of the key using MD5 algorithm
     * @param key
     * @return
     */
    private Long calculateHash(String key) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(key.getBytes());
            byte[] hash = digest.digest();
            return ((long) (hash[0] & 0xFF) << 24) |
                    ((long) (hash[1] & 0xFF) << 16) |
                    ((long) (hash[2] & 0xFF) << 8) |
                    ((long) (hash[3] & 0xFF));
        } catch (Exception e) {
            log.error("Error calculating hash", e);
            return null;
        }
    }

    /**
     * Method to add server and its virtual nodes
     * @param server
     */
    public void addServer(String server) {
        //add this server to physcial servers
        servers.add(server);
        //Now add this server to the consistent hash ring virtually that number of times it should be replicate
        for(int i=0; i<numberOfReplicas; i++) {
            Long hash = calculateHash(server);
            if (hash==null) {
                continue;
            }
            ring.put(hash, server);
        }
    }

    /**
     * Method to remove server and its replica nodes
     * @param server
     */
    public void removeServer(String server) {
        boolean isRemoved = servers.remove(server);
        if (!isRemoved) {
            log.info("Server {} is not removed", server);
            return;
        }
        //now as the server is removed physciall, remove it from the ring as well
        for(int i=0; i<numberOfReplicas; i++) {
            Long hash = calculateHash(server);
            if (hash==null) {
                continue;
            }
            ring.remove(hash);
        }
    }

    /**
     * Method to get the server
     * @param server
     * @return
     */
    public String getServer(String server) {
        //if no server is there return null
        if (ring.isEmpty()) {
            log.info("No server is there in the ring");
            return null;
        }
        Long hash = calculateHash(server);
        if (hash==null) {
            return null;
        }
        // Find the closest server in a clockwise direction
        Map.Entry<Long, String> entry = ring.ceilingEntry(hash);
        if (entry==null) {
            // If we exceed the highest node, wrap around to the first node
            entry = ring.firstEntry();
        }
        return entry.getValue();
    }

    public static void main(String[] args) {
        List<String> servers = Arrays.asList("S0", "S1", "S2", "S3", "S4", "S5");
        ConsistentHashing ch = new ConsistentHashing(3,servers);

        // Step 2: Assign requests (keys) to servers
        System.out.println("UserA is assigned to: " + ch.getServer("UserA"));
        System.out.println("UserB is assigned to: " + ch.getServer("UserB"));

        // Step 3: Add a new server dynamically
        ch.addServer("S6");
        System.out.println("UserA is now assigned to: " + ch.getServer("UserA"));

        // Step 4: Remove a server dynamically
        ch.removeServer("S2");
        System.out.println("UserB is now assigned to: " + ch.getServer("UserB"));
    }
}
