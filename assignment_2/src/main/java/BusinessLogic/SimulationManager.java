package BusinessLogic;

import GUI.SimulationFrame;
import Models.Server;
import Models.Task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulationManager implements Runnable {

    public Integer timeLimit = 200;
    public Integer maxProcessingTime = 9;
    public Integer minProcessingTime = 3;
    public Integer maxArrivalTime = 100;
    public Integer minArrivalTime = 10;
    public Integer numberOfServers = 20;
    public Integer numberOfClients = 1000;
    public Integer maxTasksPerServer = 10;
    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_QUEUE;
    public Scheduler scheduler;
    public List<Task> generatedTasks;

    public SimulationManager() {
        this.scheduler = new Scheduler(numberOfServers, maxTasksPerServer, selectionPolicy);
        this.generatedTasks = Collections.synchronizedList(new ArrayList<>());
        this.generateNRandomTasks();
    }

    public void generateNRandomTasks() {
        int arrivalTime;
        int serviceTime;

        for (Integer i = 0; i < numberOfClients; i++) {
            arrivalTime = generateRandomValue(minArrivalTime, maxArrivalTime);
            serviceTime = generateRandomValue(minProcessingTime, maxProcessingTime);

            var task = new Task(i, arrivalTime, serviceTime);
            generatedTasks.add(task);
        }

        this.sortTasksByArrivalTime(generatedTasks);
    }

    public int generateRandomValue(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    public void sortTasksByArrivalTime(List<Task> tasks) {
        tasks.sort(Comparator.comparingInt(Task::getArrivalTime));
    }

    public String printStack() {
        StringBuilder stackBuilder = new StringBuilder("Waiting clients: ");

        for (Task task : generatedTasks) {
            stackBuilder.append("(").append(task.getId()).append(",").append(task.getArrivalTime()).append(",").append(task.getServiceTime()).append(");");
        }

        stackBuilder.append("\n");

        for (Server server : scheduler.getServers()) {
            stackBuilder.append("Queue ").append(server.getServerId()).append(": ");

            if (server.getBlockingQueueSize() == 0) {
                stackBuilder.append("closed ");
            } else {
                Task[] tasks = server.getTasks();
                for (int i = 0; i < server.getBlockingQueueSize(); i++) {
                    stackBuilder.append("(").append(tasks[i].getId()).append(",").append(tasks[i].getArrivalTime()).append(",").append(tasks[i].getServiceTime()).append(");");
                }
            }

            stackBuilder.append("\n");
        }

        return stackBuilder.toString();
    }

    @Override
    public void run() {
        String log = "";

        for (int currentTime = 0; currentTime < timeLimit; currentTime++) {
            List<Task> tasksToBeRemoved = new ArrayList<>();

            for (Task task : generatedTasks) {
                if (task.getArrivalTime().equals(currentTime)) {
                    scheduler.dispatchTask(task);
                    tasksToBeRemoved.add(task);
                } else if (task.getArrivalTime() > currentTime) {
                    break;
                }
            }
            generatedTasks.removeAll(tasksToBeRemoved);

            String display = "Time " + currentTime + "\n" + printStack();
            log = log + display;
            System.out.println(display);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }

        File file = new File("log.txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (FileWriter fileWriter = new FileWriter("log.txt")) {
            fileWriter.write(log);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SimulationManager simulationManager = new SimulationManager();
        new Thread(simulationManager).start();
    }
}
