package kidsafetyalertsystem;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

interface Observer {
    void update(String alertMessage);
}

class DangerAlert {
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String alertMessage) {
        for (Observer observer : observers) {
            observer.update(alertMessage);
        }
    }

    public void detectDanger(String dangerType) {
        notifyObservers("🚨 Urgent! " + dangerType);
    }
}

class ParentAlert implements Observer {
    private String parentName;

    public ParentAlert(String parentName) {
        this.parentName = parentName;
    }

    @Override
    public void update(String alertMessage) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(parentName + " Alert");
            JOptionPane.showMessageDialog(frame, 
                "📩 Alert for " + parentName + ":\n" + alertMessage, 
                parentName + " Alert", 
                JOptionPane.WARNING_MESSAGE);
        });
    }
}

public class KidSafetyAlertSystem extends JFrame {
    private DangerAlert dangerAlert;

    public KidSafetyAlertSystem() {
        dangerAlert = new DangerAlert();

        ParentAlert mom = new ParentAlert("Mom");
        ParentAlert dad = new ParentAlert("Dad");

        dangerAlert.addObserver(mom);
        dangerAlert.addObserver(dad);

        JButton animalButton = new JButton("Animal Inside House");
        animalButton.addActionListener(e -> dangerAlert.detectDanger("Animal detected inside the house!"));

        JButton fallButton = new JButton("Kid Fell While Playing");
        fallButton.addActionListener(e -> dangerAlert.detectDanger("Kid fell while playing!"));

        JButton strangerButton = new JButton("Stranger Approaching");
        strangerButton.addActionListener(e -> dangerAlert.detectDanger("A stranger is approaching the child!"));

        JButton exitButton = new JButton("Emergency Exit Required");
        exitButton.addActionListener(e -> dangerAlert.detectDanger("Urgent exit required for safety!"));

        JButton closeButton = new JButton("Exit");
        closeButton.addActionListener(e -> System.exit(0));

        setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2, 10, 10));
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        buttonPanel.add(animalButton);
        buttonPanel.add(fallButton);
        buttonPanel.add(strangerButton);
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.CENTER);
        add(closeButton, BorderLayout.SOUTH);

        setTitle("Kid Safety Alert System");
        setSize(600, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new KidSafetyAlertSystem().setVisible(true));
    }
}
