package ui;

import model.CompetitorList;
import model.Tournament;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//Converts all completed tournament CompetitorList items into tournament tree images
public class TournamentTreeGUI extends JPanel {

    private JTabbedPane tournamentTrees;
    private Tournament tournament;
    private JFrame tournamentTreesFrame;

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;

    // EFFECTS: Creates a tabbed pane with all generated tournament trees
    public TournamentTreeGUI(Tournament tournament) {
        this.tournament = tournament;
        tournamentTrees = new JTabbedPane();
        tournamentTrees.setSize(WIDTH - 100, HEIGHT - 100);
        tournamentTreesFrame = new JFrame("Brackets");
        tournamentTreesFrame.setLayout(null);
        tournamentTreesFrame.setSize(WIDTH, HEIGHT);
        tournamentTreesFrame.pack();
        tournamentTreesFrame.setBounds(new Rectangle(WIDTH, HEIGHT));
        tournamentTreesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tournamentTrees.setVisible(true);
        tournamentTreesFrame.setVisible(true);
        printTournament(tournament);
    }

    // EFFECTS: Produces tournament tree images from every CompetitorList in Tournament
    public void printTournament(Tournament tournament) {
        ArrayList<CompetitorList> list = tournament.getCompetitorLists();
        int i = 1;
        for (CompetitorList bracket : list) {
            GeneratedTreeGUI tree = new GeneratedTreeGUI(bracket);
            tournamentTrees.add("Division: " + i, tree);
            i++;
        }
        tournamentTreesFrame.add(tournamentTrees);
    }
}
