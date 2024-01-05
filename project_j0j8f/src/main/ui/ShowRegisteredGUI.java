package ui;

import model.Competitor;
import model.CompetitorList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

// Display panel of registered competitors in a tournament bracket division
public class ShowRegisteredGUI {

    private JFrame showRegistered;
    private JLabel registeredCompetitors;
    private JButton add;
    private JButton remove;
    private CompetitorList competitorList;
    public static final int MAX = CompetitorList.MAX;

    // EFFECTS: Creates a display panel with list of all registered competitors
    public ShowRegisteredGUI(CompetitorList competitorList) {
        this.competitorList = competitorList;
        showRegistered = new JFrame("Registered Competitors");
        showRegistered.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        showRegistered.setSize(300, 500);
        showRegistered.setLayout(new GridLayout(11, 1));
        registeredCompetitors = new JLabel("Registered Competitors");
        showRegistered.add(registeredCompetitors);
        addCompetitors();
        addButtons();
        showRegistered.setVisible(true);
    }

    // EFFECTS: Adds competitor list to JPanel display
    public void addCompetitors() {
        ArrayList<Competitor> list = competitorList.getCompetitorList();
        for (Competitor competitor : list) {
            JLabel competitorLabel = new JLabel(competitor.getName());
            showRegistered.add(competitorLabel);
        }
    }

    // EFFECTS: Adds add and remove button to JPanel display
    public void addButtons() {
        addButton();
        removeButton();
    }

    // REQUIRES: input.equals(null) || input.equals(" ")
    // MODIFIES: CompetitorList
    // EFFECTS: Adds competitor to competitor list and then to JPanel display
    public void addButton() {
        add = new JButton("Add Competitor");
        showRegistered.add(add);
        ActionListener addListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (competitorList.getCompetitorListSize() < MAX) {
                    String input = JOptionPane.showInputDialog("Enter competitor name: ");
                    if (input.equals("")) {
                        JOptionPane.showMessageDialog(null, "Invalid input!",
                                "Invalid input!", JOptionPane.WARNING_MESSAGE);
                    } else {
                        competitorList.addCompetitor(new Competitor(input));
                        JOptionPane.showMessageDialog(null, "Added " + input,
                                input + " added", JOptionPane.INFORMATION_MESSAGE);
                        showRegistered.setVisible(false);
                        new ShowRegisteredGUI(competitorList);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Full!","Full!", JOptionPane.WARNING_MESSAGE);
                }
            }
        };
        add.addActionListener(addListener);
    }

    // REQUIRES: competitorList.getCompetitorListSize() != 0
    // MODIFIES: CompetitorList
    // EFFECTS: Removes competitor from CompetitorList then generates new updated JPanel
    public void removeButton() {
        remove = new JButton("Remove Competitor");
        showRegistered.add(remove);
        ActionListener removeListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (competitorList.getCompetitorListSize() != 0) {
                    ArrayList<Competitor> removed = competitorList.getCompetitorList();
                    String removedCompetitor = removed.get(competitorList.getCompetitorListSize() - 1).getName();
                    competitorList.removeCompetitor();
                    JOptionPane.showMessageDialog(null, removedCompetitor + " removed",
                            removedCompetitor + " removed",
                            JOptionPane.INFORMATION_MESSAGE);
                    showRegistered.setVisible(false);
                    new ShowRegisteredGUI(competitorList);
                } else {
                    JOptionPane.showMessageDialog(null, "No competitors in bracket!",
                            "No competitors in bracket!", JOptionPane.WARNING_MESSAGE);
                }

            }
        };
        remove.addActionListener(removeListener);
    }
}
