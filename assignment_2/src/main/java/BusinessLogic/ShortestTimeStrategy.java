package BusinessLogic;

import Models.Server;
import Models.Task;

import java.util.List;

public class ShortestTimeStrategy implements Strategy{
    @Override
    public void addTask(List<Server> servers, Task task) {
        Server shortestQueueServer = servers.get(0);
        for (Server server : servers) {
            if (server.getWaitingPeriod() < shortestQueueServer.getWaitingPeriod()) {
                shortestQueueServer = server;
            }
        }

        shortestQueueServer.addTask(task);
    }
}
