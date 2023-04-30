package BusinessLogic;

import Models.Server;
import Models.Task;

import java.util.List;

public interface Strategy {
    public void addTask(List<Server> servers, Task task);
}
