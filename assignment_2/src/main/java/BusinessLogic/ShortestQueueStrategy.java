package BusinessLogic;

import Models.Server;
import Models.Task;

import java.util.List;

public class ShortestQueueStrategy implements Strategy {
    @Override
    public void addTask(List<Server> servers, Task task) {
        Server shortestServer = servers.get(0);

        for (Server server : servers) {
            if (server.getBlockingQueueSize() < shortestServer.getBlockingQueueSize()) {
                shortestServer = server;
            }
        }

        shortestServer.addTask(task);
    }
}
