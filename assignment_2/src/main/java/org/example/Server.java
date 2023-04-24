package org.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;

    private int id;


    public Server(BlockingQueue<Task> tasks, AtomicInteger waitingPeriod) {
        this.tasks = tasks;
        this.waitingPeriod = waitingPeriod;
    }

    public BlockingQueue<Task> getTasks() {
        return tasks;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public void setTasks(BlockingQueue<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        try {
            tasks.put(task);
            waitingPeriod.addAndGet(task.getServiceTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

    }
}

