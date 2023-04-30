package BusinessLogic;

import Models.Server;
import Models.Task;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private List<Server> servers = new ArrayList<>();
    private Integer maxNoServers;
    private Integer maxTasksPerServer;
    private Strategy strategy;

    public Scheduler(Integer maxNoServers, Integer maxTasksPerServer, SelectionPolicy policy) {
        this.maxNoServers = maxNoServers;
        this.maxTasksPerServer = maxTasksPerServer;
        //this.strategy = new ShortestTimeStrategy();
        changeStrategy(policy);

        for (int i = 0; i < maxNoServers; i++) {
            var server = new Server(maxTasksPerServer, i);
            servers.add(server);

            new Thread(server).start();
        }
    }

    public void changeStrategy(SelectionPolicy policy) {
        if (policy == SelectionPolicy.SHORTEST_QUEUE) {
            strategy = new ShortestQueueStrategy();
        }

        if (policy == SelectionPolicy.SHORTEST_TIME) {
            strategy = new ShortestTimeStrategy();
        }
    }

    public void dispatchTask(Task task) {
        strategy.addTask(servers, task);
    }

    public List<Server> getServers() {
        return servers;
    }
}
