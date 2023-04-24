package org.example;

public class Task {
    private int id;
    private int arrivingTime;
    private int serviceTime;
    private int finishTime;

    public Task(int id, int arrivingTime, int serviceTime) {
        this.id = id;
        this.arrivingTime = arrivingTime;
        this.serviceTime = serviceTime;
    }

    public int getId() {
        return id;
    }

    public int getArrivingTime() {
        return arrivingTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public String toString() {
        return "Task " + id + " arriving at " + arrivingTime + " and finishing at " + finishTime;
    }
}
