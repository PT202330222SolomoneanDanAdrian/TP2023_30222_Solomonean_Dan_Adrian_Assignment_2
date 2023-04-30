package GUI;

import BusinessLogic.SelectionPolicy;
import BusinessLogic.SimulationManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationFrame extends JFrame implements ActionListener {
    private JButton startButton;
    private JTextField timeLimitTextField;
    private JTextField maxProcessingTimeTextField;
    private JTextField minProcessingTimeTextField;
    private JTextField maxArrivalTimeTextField;
    private JTextField minArrivalTimeTextField;
    private JTextField numberOfServersTextField;
    private JTextField numberOfClientsTextField;
    private JTextField maxTasksPerServerTextField;
    private JComboBox<String> selectionPolicyComboBox;

    public SimulationFrame() {
        this.setTitle("Simulation Manager");
        this.setSize(400, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(11, 2, 10, 10));

        panel.add(new JLabel("Time Limit"));
        timeLimitTextField = new JTextField("200");
        panel.add(timeLimitTextField);

        panel.add(new JLabel("Max Processing Time"));
        maxProcessingTimeTextField = new JTextField("9");
        panel.add(maxProcessingTimeTextField);

        panel.add(new JLabel("Min Processing Time"));
        minProcessingTimeTextField = new JTextField("3");
        panel.add(minProcessingTimeTextField);

        panel.add(new JLabel("Max Arrival Time"));
        maxArrivalTimeTextField = new JTextField("100");
        panel.add(maxArrivalTimeTextField);

        panel.add(new JLabel("Min Arrival Time"));
        minArrivalTimeTextField = new JTextField("10");
        panel.add(minArrivalTimeTextField);

        panel.add(new JLabel("Number of Servers"));
        numberOfServersTextField = new JTextField("20");
        panel.add(numberOfServersTextField);

        panel.add(new JLabel("Number of Clients"));
        numberOfClientsTextField = new JTextField("1000");
        panel.add(numberOfClientsTextField);

        panel.add(new JLabel("Max Tasks per Server"));
        maxTasksPerServerTextField = new JTextField("10");
        panel.add(maxTasksPerServerTextField);

        panel.add(new JLabel("Selection Policy"));
        selectionPolicyComboBox = new JComboBox<>(new String[] {"Shortest Queue", "Shortest Time"});
        panel.add(selectionPolicyComboBox);

        startButton = new JButton("Start Simulation");
        startButton.addActionListener(this);
        panel.add(startButton);

        this.add(panel);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            SimulationManager simulationManager = new SimulationManager();
            simulationManager.timeLimit = Integer.parseInt(timeLimitTextField.getText());
            simulationManager.maxProcessingTime = Integer.parseInt(maxProcessingTimeTextField.getText());
            simulationManager.minProcessingTime = Integer.parseInt(minProcessingTimeTextField.getText());
            simulationManager.maxArrivalTime = Integer.parseInt(maxArrivalTimeTextField.getText());
            simulationManager.minArrivalTime = Integer.parseInt(minArrivalTimeTextField.getText());
            simulationManager.numberOfServers = Integer.parseInt(numberOfServersTextField.getText());
            simulationManager.numberOfClients = Integer.parseInt(numberOfClientsTextField.getText());
            simulationManager.maxTasksPerServer = Integer.parseInt(maxTasksPerServerTextField.getText());

            if (selectionPolicyComboBox.getSelectedIndex() == 0) {
                simulationManager.selectionPolicy = SelectionPolicy.SHORTEST_QUEUE;
            } else {
                simulationManager.selectionPolicy = SelectionPolicy.SHORTEST_TIME;
            }

            new Thread(simulationManager).start();
        }
    }
}