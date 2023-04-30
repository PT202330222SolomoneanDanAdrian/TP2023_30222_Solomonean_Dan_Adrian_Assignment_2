package Models;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {

    private Integer serverId;
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    public Integer maxTaskPerServer;

    public Server(Integer maxNoTasks, Integer serverId) {
        this.tasks = new ArrayBlockingQueue<>(maxNoTasks);
        this.waitingPeriod = new AtomicInteger(0);
        this.maxTaskPerServer = maxNoTasks;
        this.serverId = serverId;
    }

    public void addTask(Task task) {
        try {
            tasks.put(task);
            waitingPeriod.addAndGet(task.getServiceTime());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public Integer getWaitingPeriod() {
        return waitingPeriod.get();
    }

    public void setWaitingPeriod(Integer waitingPeriod) {
        this.waitingPeriod.set(waitingPeriod);
    }

    public Integer getBlockingQueueSize() {
        return tasks.size();
    }

    public void decreaseWaitingPeriod(Integer time) {
        waitingPeriod.addAndGet(-time);
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }

            try {
                Task task = tasks.take();
                task.decreaseServiceTime();
                decreaseWaitingPeriod(task.getServiceTime());

                if (task.getServiceTime() > 0) {
                    tasks.put(task);
                }
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public Task[] getTasks() {
        BlockingQueue<Task> tasks = new LinkedBlockingQueue<>(this.tasks);
        Task[] tasksArray = tasks.toArray(new Task[tasks.size()]);

        return tasksArray;
    }
}

