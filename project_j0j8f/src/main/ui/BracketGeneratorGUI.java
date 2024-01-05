package ui;

import model.Competitor;
import model.CompetitorList;
import model.EventLog;
import model.Tournament;
import model.EventLog;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//Main menu of tournament bracket generator with options to add, remove and save competitors in tournament
//brackets. Allows user to also print completed brackets or quit program from here as well
public class BracketGeneratorGUI {

    private JFrame tournamentFrame;

    private JPanel title;
    private JPanel buttons;
    private JPanel left;
    private JPanel right;
    private JPanel center;

    private JButton addCompetitor;
    private JButton removeCompetitor;
    private JButton showRegistered;
    private JButton print;
    private JButton save;
    private JButton saveAll;
    private JButton quit;

    private ImageIcon dog;

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 500;
    public static final int MAX = CompetitorList.MAX;
    public static final String JSON_STORE = "./data/Tournament.json";

    private Scanner input;
    private Tournament tournament;
    private CompetitorList competitorList;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: Creates the main page of Tournament Bracket Generator
    public BracketGeneratorGUI() {

        tournamentFrame = new JFrame();
        competitorList = new CompetitorList();
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        try {
            tournament = jsonReader.read();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(),
                    "Unable to read from file: " + JSON_STORE, JOptionPane.ERROR_MESSAGE);
        }
        tournamentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tournamentFrame.setSize(WIDTH, HEIGHT);
        tournamentFrame.setLayout(new BorderLayout());
        tournamentFrame.setTitle("Bracket Generator");
        checkSavedCompetitorList();
        bracketGeneratorMainMenu();
        tournamentFrame.setVisible(true);
        EventLog.getInstance().clear();

    }

    // EFFECTS: If most recent CompetitorList size < 8 in saved tournament, CompetitorList is removed and from
    //         CompetitorLists and is modifiable in main menu. Otherwise, do nothing
    public void checkSavedCompetitorList() {
        ArrayList<CompetitorList> savedLists = tournament.getCompetitorLists();
        if (savedLists.size() == 0) {
            //Do nothing
        } else if (savedLists.get(savedLists.size() - 1).getCompetitorListSize() < 8) {
            savedLists.get(savedLists.size() - 1);
            tournament.removeCompetitorList();
        }
    }

    // EFFECTS: Loads panel and buttons of main menu
    public void bracketGeneratorMainMenu() {
        addPanels();
        addButtons();
    }

    // EFFECTS: Configures panel display of main menu
    public void addPanels() {
        title = new JPanel();
        title.setBackground(Color.gray);
        title.setPreferredSize(new Dimension(WIDTH, 50));
        tournamentFrame.add(title, BorderLayout.NORTH);

        buttons = new JPanel();
        buttons.setBackground(Color.gray);
        buttons.setPreferredSize(new Dimension(WIDTH, 100));
        tournamentFrame.add(buttons, BorderLayout.SOUTH);

        left = new JPanel();
        left.setBackground(Color.gray);
        left.setPreferredSize(new Dimension(50, HEIGHT));
        tournamentFrame.add(left, BorderLayout.WEST);

        right = new JPanel();
        right.setBackground(Color.gray);
        right.setPreferredSize(new Dimension(50, HEIGHT));
        tournamentFrame.add(right, BorderLayout.EAST);

        center = new JPanel();
        center.setBackground(Color.darkGray);
        center.setPreferredSize(new Dimension(WIDTH - 100, HEIGHT - 150));
        center.setLayout(new GridLayout(7, 1));
        tournamentFrame.add(center, BorderLayout.CENTER);
    }

    // EFFECTS: Adds buttons to center panel of main menu
    public void addButtons() {
        addCompetitorButton();
        removeCompetitorButton();
        showRegisteredButton();
        printButton();
        saveButton();
        saveAllButton();
        quitButton();
    }

    // MODIFIES: CompetitorList
    // EFFECTS: Adds competitor to competitor list when ActionEvent performed
    public void addCompetitorButton() {
        addCompetitor = new JButton("Add Competitor");
        center.add(addCompetitor);
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
                                "Competitor " + competitorList.getCompetitorListSize() + " of " + MAX + " added",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Tournament is full!",
                            "Tournament is full!", JOptionPane.WARNING_MESSAGE);
                }
            }
        };
        addCompetitor.addActionListener(addListener);
    }

    // MODIFIES: CompetitorList
    // EFFECTS: Removes competitor from CompetitorList when ActionEvent performed
    public void removeCompetitorButton() {
        removeCompetitor = new JButton("Remove Competitor");
        center.add(removeCompetitor);
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
                } else {
                    JOptionPane.showMessageDialog(null, "No competitors in bracket!",
                            "No competitors in bracket!", JOptionPane.WARNING_MESSAGE);
                }

            }
        };
        removeCompetitor.addActionListener(removeListener);
    }

    // EFFECTS: Displays all registered competitors in CompetitorList
    public void showRegisteredButton() {
        showRegistered = new JButton("Show Registered");
        center.add(showRegistered);
        ActionListener registerListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ShowRegisteredGUI(competitorList);
            }
        };
        showRegistered.addActionListener(registerListener);
    }

    // EFFECTS: Produces all completed divisions in Tournament on new JFrame
    public void printButton() {
        print = new JButton("Print");
        center.add(print);
        ActionListener printListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tournament.getCompetitorLists().size() == 0) {
                    JOptionPane.showMessageDialog(null, "Need at least 1 bracket!",
                            "No brackets present!", JOptionPane.WARNING_MESSAGE);
                } else {
                    new TournamentTreeGUI(tournament);
                }
            }
        };
        print.addActionListener(printListener);
    }

    // MODIFIES: Tournament, CompetitorList
    // EFFECTS: Completes all valid tournament brackets and adds to Tournament. If bracket is invalid, no
    //         completion will form and error message will be produced
    public void saveButton() {
        save = new JButton("Save Completed Division & Add New Division");
        center.add(save);
        ActionListener saveListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (competitorList.getCompetitorListSize() < 5) {
                    JOptionPane.showMessageDialog(null, "Not enough competitors to run bracket",
                            "No enough competitors! Minimum 5 needed!", JOptionPane.WARNING_MESSAGE);
                } else {
                    if (competitorList.getCompetitorListSize() < 8) {
                        competitorList.completeCompetitorList();
                    }
                    competitorList.matchmakeCompetitorList();
                    tournament.addCompetitorList(competitorList);
                    saveTournament();
                    JOptionPane.showMessageDialog(null, "Bracket saved! New Bracket Ready!",
                            "Bracket Saved!",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        };
        save.addActionListener(saveListener);
    }

    public void saveAllButton() {
        saveAll = new JButton("Save All Progress");
        center.add(saveAll);
        ActionListener saveAllListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tournament.addCompetitorList(competitorList);
                saveTournament();
                JOptionPane.showMessageDialog(null, "Current Progress Saved!",
                        "Current Progress Saved!",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        };
        saveAll.addActionListener(saveAllListener);
    }

    // MODIFIES: JSONWriter, CompetitorList
    // EFFECTS: Saves completed tournament brackets and produces new CompetitorList
    public void saveTournament() {
        try {
            jsonWriter.openTournament();
            jsonWriter.writeTournament(tournament);
            jsonWriter.closeTournament();
            competitorList = new CompetitorList();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Unable to write file: " + JSON_STORE,
                    "Unable to save!", JOptionPane.WARNING_MESSAGE);
        }
    }

    // EFFECTS: Quits application
    public void quitButton() {
        quit = new JButton("Quit");
        center.add(quit);
        ActionListener quitListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMyDog();
                printEventLog(EventLog.getInstance());
                JOptionPane.showMessageDialog(null, "Goodbye!",
                        "Goodbye!",
                        JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        };
        quit.addActionListener(quitListener);
    }

    // EFFECTS: Displays photo of dog when program quits
    public void showMyDog() {
        JFrame doggo = new JFrame("Clifford Says Bye!");
        doggo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        doggo.setLayout(null);
        dog = scaleClifford();
        doggo.setSize(dog.getIconWidth(), dog.getIconHeight());
        JLabel clifford = new JLabel(dog);
        clifford.setBounds(new Rectangle(dog.getIconWidth(), dog.getIconHeight()));
        doggo.setBounds(new Rectangle(dog.getIconWidth(), dog.getIconHeight()));
        clifford.setVisible(true);
        doggo.add(clifford);
        doggo.setVisible(true);
    }

    // EFFECTS: Scales image of Clifford
    public ImageIcon scaleClifford() {
        dog = new ImageIcon("./data/DSC03298.jpg");
        int height = dog.getIconHeight();
        int width = dog.getIconWidth();
        int scaleHeight = HEIGHT;
        int scaleWidth = (width * HEIGHT) / height;
        return new ImageIcon(dog.getImage().getScaledInstance(scaleWidth, scaleHeight, Image.SCALE_DEFAULT));
    }

    public void printEventLog(EventLog el) {
        for (Event e : el) {
            System.out.println(e.toString());
        }
    }

}
