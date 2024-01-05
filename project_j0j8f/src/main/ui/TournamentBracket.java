package ui;

import model.CompetitorList;
import model.Competitor;
import model.EventLog;
import model.Tournament;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Tournament Bracket Generator
// The following was code was modified from the TellerApp class in the TellerApp project:
// https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/main/src/main/ca/ubc/cpsc210/bank/ui/TellerApp.java
public class TournamentBracket {
    public static final int MAX = CompetitorList.MAX;
    public static final String JSON_STORE = "./data/Tournament.json";
    private Scanner input;
    private Tournament tournament;
    private CompetitorList competitorList;
    private boolean keepGoing = true;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs tournament bracket generator
    public TournamentBracket() {
        EventLog.getInstance().clear();
        runBracketGenerator();
    }

    // MODIFIES: this
    // EFFECTS: processes user input to create tournament bracket
    private void runBracketGenerator() {
        String command;
        init();
        loadTournament();

        while (keepGoing) {
            System.out.println("Welcome to the Tournament Bracket Generator!");
            mainMenu();
            command = input.next();
            command = command.toLowerCase();
            processMenu(command);
        }
    }

    // MODIFIES: this, Tournament, JsonReader, JsonWriter
    // EFFECTS: initializes user
    private void init() {
        input = new Scanner(System.in);
        competitorList = new CompetitorList();
        tournament = new Tournament("Tournament");
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
    }

    // EFFECTS: displays options for tournament bracket generator
    private void mainMenu() {
        System.out.println("Choose from the following options:");
        System.out.println("\tadd = Add competitor");
        System.out.println("\tremove = Remove competitor");
        System.out.println("\tprint = Print completed tournament brackets");
        System.out.println("\tsave = Save completed bracket");
        System.out.println("\tload = Load tournament bracket");
        System.out.println("\tquit = Quit");
    }

    // EFFECTS: processes type input from user
    private void processMenu(String command) {
        if (command.equals("add")) {
            addCompetitor();
        } else if (command.equals("remove")) {
            removeCompetitor();
        } else if (command.equals("print")) {
            checkPrint();
        } else if (command.equals("save")) {
            saveMenu();
            chooseSaveMenu();
        } else if (command.equals("load")) {
            loadTournament();
        } else if (command.equals("quit")) {
            checkQuit();
        } else {
            System.out.println("Invalid command!");
        }
    }

    // MODIFIES: this
    // EFFECTS: terminates app and checks if you have saved completed brackets before termination
    private void checkQuit() {
        System.out.println("All unsaved brackets will be lost!");
        System.out.println("Are you sure you want to quit?");
        String command = input.next();
        command = command.toLowerCase();
        if (command.equals("yes")) {
            System.out.println("Goodbye!");
            this.keepGoing = false;
        } else if (command.equals("no")) {
            System.out.println("Going back to main menu!");
        } else {
            System.out.println("Invalid prompt!");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds competitor to competitor list
    private void addCompetitor() {
        String command;
        int tournamentSize = competitorList.getCompetitorListSize();
        if (tournamentSize == MAX) {
            System.out.println("Tournament is full!");
        } else if (tournamentSize == MAX - 1) {
            System.out.println("Enter competitor name:");
            command = input.next();
            enterName(command);
            competitorList.matchmakeCompetitorList();
        } else {
            System.out.println("Enter competitor name:");
            command = input.next();
            enterName(command);
        }
    }

    // MODIFIES: this, CompetitorList, Competitor
    // EFFECTS: creates new competitor
    private void enterName(String command) {
        competitorList.addCompetitor(new Competitor(command));
        System.out.println(command + " entered!");
    }

    // MODIFIES: this, CompetitorList
    // EFFECTS: removes competitor from competitor list
    private void removeCompetitor() {
        int tournamentSize = competitorList.getCompetitorListSize();
        if (tournamentSize == MAX) {
            System.out.println("You have nobody registered!");
        } else {
            competitorList.removeCompetitor();
            System.out.println("Competitor removed!");
        }
    }

    // EFFECTS: checks if user can print bracket
    private void checkPrint() {
        int divisionsPresent = tournament.getCompetitorLists().size();
        if (divisionsPresent > 0) {
            printBracket();
        } else {
            System.out.println("No saved divisions!");
        }
    }

    // EFFECTS: prompts user with save menu
    private void saveMenu() {
        System.out.println("Choose from the following options: ");
        System.out.println("\t save = Save completed bracket");
        System.out.println("\t load = Load previous tournament");
        System.out.println("\t discard = Delete current bracket");
        System.out.println("\t delete = Delete most recently saved bracket");
        System.out.println("\t reset = Reset tournament");

    }

    // MODIFIES: this
    // EFFECTS: chooses options from save menu
    private void chooseSaveMenu() {
        String command = input.next();
        command = command.toLowerCase();
        if (command.equals("save")) {
            checkSave();
        } else if (command.equals("load")) {
            loadTournament();
        } else if (command.equals("discard")) {
            discardBracket();
        } else if (command.equals("delete")) {
            deleteSavedBracket();
        } else if (command.equals("reset")) {
            checkReset();
        } else {
            System.out.println("Invalid Command!");
        }
    }

    // MODIFIES: this, Tournament
    // EFFECTS: checks if you would like to reset Tournament
    private void checkReset() {
        System.out.println("Are you sure you would like to reset?");
        System.out.println("All previously saved data will be lost! yes/no?");
        String command = input.next();
        command = command.toLowerCase();
        if (command.equals("yes")) {
            System.out.println("Tournament reset!");
            tournament = new Tournament("Tournament");
            saveTournament();
        } else if (command.equals("no")) {
            System.out.println("Reset cancelled");
        }
    }

    // EFFECTS: checks if tournament bracket can be saved
    private void checkSave() {
        int tournamentSize = competitorList.getCompetitorListSize();
        if (tournamentSize < 5) {
            System.out.println("Needs at least " + (5 - tournamentSize) + " more competitors");
        } else {
            promptSave();
        }
    }

    // MODIFIES: this, Tournament
    // EFFECTS: deletes most recently saved tournament bracket
    private void deleteSavedBracket() {
        tournament.removeCompetitorList();
        saveTournament();
    }

    // MODIFIES: this, CompetitorList
    // EFFECTS: discards most recent tournament bracket
    private void discardBracket() {
        this.competitorList = new CompetitorList();
        System.out.println("New bracket created!");
    }

    // MODIFIES: this, Tournament, CompetitorList
    // EFFECTS: prompt user to save Tournament
    private void promptSave() {
        System.out.println("Would you like to save bracket?");
        System.out.println("Once saved, bracket cannot be modified!");
        System.out.println("yes/no");
        String response = input.next();
        if (response.equals("yes")) {
            int size = competitorList.getCompetitorListSize();
            if (size < 8) {
                competitorList.completeCompetitorList();
            }
            competitorList.matchmakeCompetitorList();
            tournament.addCompetitorList(competitorList);
            saveTournament();
        } else if (response.equals("no")) {
            mainMenu();
        } else {
            System.out.println("Invalid response!");
        }
    }

    // MODIFIES: this, JsonWriter
    // EFFECTS: saves tournament to file
    private void saveTournament() {
        try {
            jsonWriter.openTournament();
            jsonWriter.writeTournament(tournament);
            jsonWriter.closeTournament();
            System.out.println("Saved " + tournament.getName() + " to " + JSON_STORE);
            System.out.println("New competitor list generated!");
            System.out.println("Saved competitor list(s) can be printed!");
            competitorList = new CompetitorList();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write file: " + JSON_STORE);
        }
    }

    // MODIFIES: this, Tournament
    // EFFECTS: loads Tournament from file
    private void loadTournament() {
        try {
            tournament = jsonReader.read();
            System.out.println("Loaded " + tournament.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: prints out tournament bracket if MAX participants reached
    private void printBracket() {
        int tournamentSize = tournament.getCompetitorLists().size();
        if (tournamentSize > 0) {
            printTrees();
        } else {
            System.out.println("You have no saved brackets!");
        }
    }

    // MODIFIES: this
    // EFFECTS: takes CompetitorLists and produces a tree from all elements in array
    private void printTrees() {
        for (int i = 0; i < tournament.getCompetitorLists().size(); i++) {
            System.out.println("Division " + (i + 1));
            competitorList = tournament.getCompetitorLists().get(i);
            printTree();
        }
    }

    //EFFECTS: prints out tournament bracket tree
    private void printTree() {
        System.out.println(" ");
        System.out.println(competitorList.getCompetitorList().get(0).getName());
        System.out.println("----------");
        System.out.println(competitorList.getCompetitorList().get(1).getName());
        makeFirstPool();
        System.out.println(competitorList.getCompetitorList().get(2).getName());
        System.out.println("----------\t\t\t  |");
        System.out.println(competitorList.getCompetitorList().get(3).getName());
        System.out.println("\t\t\t\t\t  |----------");
        System.out.println(competitorList.getCompetitorList().get(4).getName());
        System.out.println("----------\t\t\t  |");
        System.out.println(competitorList.getCompetitorList().get(5).getName());
        makeSecondPool();
        System.out.println(competitorList.getCompetitorList().get(6).getName());
        System.out.println("----------");
        System.out.println(competitorList.getCompetitorList().get(7).getName());
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
    }

    // EFFECTS: creates branches for first pool of the tree
    private void makeFirstPool() {
        System.out.println("\t\t   |");
        System.out.println("\t\t   |----------");
        System.out.println("\t\t   |\t\t  |");
    }

    // EFFECTS: creates branches for second pool of the tree
    private void makeSecondPool() {
        System.out.println("\t\t   |\t\t  |");
        System.out.println("\t\t   |----------");
        System.out.println("\t\t   |");
    }


}
