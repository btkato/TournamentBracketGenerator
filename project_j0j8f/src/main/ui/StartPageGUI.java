package ui;

import model.CompetitorList;
import model.EventLog;
import model.Tournament;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

//Starting frame of the tournament bracket generator
public class StartPageGUI {

    private JFrame startFrame;
    private JFrame tournamentFrame;
    private JLabel label;
    private JPanel start;
    private JPanel bracket;
    private JButton load;
    private JButton restart;
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 500;
    public static final String JSON_STORE = "./data/Tournament.json";
    private Tournament tournament;
    private CompetitorList competitorList;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private BracketGeneratorGUI bracketGeneratorGUI;

    // EFFECTS: Creates the start page of the Tournament Bracket Generator
    public StartPageGUI() {
        label = new JLabel();
        start = new JPanel();
        startFrame = new JFrame();
        bracket = new JPanel();
        tournamentFrame = new JFrame();

        competitorList = new CompetitorList();
        tournament = new Tournament("Tournament");
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);

        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.setSize(WIDTH, HEIGHT);
        startFrame.setTitle("Welcome");
        startFrame.pack();
        startFrame.setLocationRelativeTo(null);
        startFrame.setBounds(new Rectangle(WIDTH, HEIGHT));


        startScreen();
        initialButtons();
        startFrame.add(start);
        startFrame.setVisible(true);
    }

    // EFFECTS: Produces welcome label on start screen
    public void startScreen() {
        label.setText("Welcome to the Tournament Bracket Generator!");
        label.setHorizontalAlignment(JLabel.CENTER);
        start.setBackground(Color.gray);
        start.setLayout(new GridLayout(3, 1));
        start.add(label);

    }

    // EFFECTS: displays buttons on start screen
    public void initialButtons() {
        load = new JButton("Load");
        restart = new JButton("New");
        start.add(load);
        start.add(restart);

        loadButton();
        restartButton();
    }

    // EFFECTS: Produces action of loading saved JSON file when pressed with ActionEvent
    public void loadButton() {
        ActionListener loadListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    tournament = jsonReader.read();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(),
                            "Unable to read from file: " + JSON_STORE, JOptionPane.ERROR_MESSAGE);
                }
                JOptionPane.showMessageDialog(null,
                        "Loaded " + tournament.getName() + " from " + JSON_STORE,
                        "Loaded Tournament", JOptionPane.INFORMATION_MESSAGE);
                startFrame.setVisible(false);

                new BracketGeneratorGUI();
            }
        };


        load.addActionListener(loadListener);
    }

    // MODIFIES: JSONWriter, Tournament
    // EFFECTS: Produces new tournament when ActionEvent performed
    public void restartButton() {
        ActionListener restartListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    tournament = new Tournament("tournament");
                    jsonWriter.openTournament();
                    jsonWriter.writeTournament(tournament);
                    jsonWriter.closeTournament();
                    competitorList = new CompetitorList();
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(),
                            "Unable to write file: " + JSON_STORE, JOptionPane.ERROR_MESSAGE);
                }
                JOptionPane.showMessageDialog(null, "New Tournament Created",
                        "New Tournament Created", JOptionPane.INFORMATION_MESSAGE);
                startFrame.setVisible(false);
                new BracketGeneratorGUI();
            }
        };
        restart.addActionListener(restartListener);
    }
}
