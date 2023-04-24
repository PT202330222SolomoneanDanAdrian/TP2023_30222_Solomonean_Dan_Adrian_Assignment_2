package org.example;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {

    private ArrayList<Server> servers;

    private ArrayList<Thread> threads;
    private Strategy strategy;
    private int maxNoServers;
    private int maxTasksPerServer;;

    public Scheduler (int maxNoServers, int maxTasksPerServer) {
        this.maxNoServers = maxNoServers;
        this.maxTasksPerServer = maxTasksPerServer;
        servers = new ArrayList<Server>(maxNoServers);
        threads = new ArrayList<Thread>(maxNoServers);
    }

}
