package ui;

import model.Competitor;
import model.CompetitorList;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//Converts completed tournament bracket into a tree image
public class GeneratedTreeGUI extends JPanel {

    private CompetitorList competitorList;

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 600;

    // EFFECTS: Creates a tournament tree image
    public GeneratedTreeGUI(CompetitorList competitorList) {
        this.competitorList = competitorList;
        this.setLayout(null);
        this.setSize(WIDTH, HEIGHT);
        this.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        printBracket(competitorList, g2D);
    }

    // EFFECTS: Produces list of Competitor from CompetitorList
    public void printBracket(CompetitorList competitorList, Graphics2D g2D) {
        ArrayList<Competitor> list = competitorList.getCompetitorList();
        printTree(g2D);
        printCompetitors(list, g2D);
    }

    // EFFECTS: Combines tree image of all three columns
    public void printTree(Graphics2D g2D) {
        drawFirstRound(g2D);
        drawSecondRound(g2D);
        drawThirdRound(g2D);
    }

    // EFFECTS: Produces first column of tournament tree
    public void drawFirstRound(Graphics2D g2D) {
        g2D.drawLine(100, 50, 400, 50);
        g2D.drawLine(400, 50, 400, 200);
        g2D.drawLine(400, 200, 100, 200);

        g2D.drawLine(100, 350, 400, 350);
        g2D.drawLine(400, 350, 400, 500);
        g2D.drawLine(400, 500, 100, 500);
    }

    // EFFECTS: Produces second column of tournament tree
    public void drawSecondRound(Graphics2D g2D) {
        g2D.drawLine(400, 125, 700, 125);
        g2D.drawLine(700, 125, 700, 425);
        g2D.drawLine(700, 425, 400, 425);
    }

    // EFFECTS: Produces final column of tournament bracket
    public void drawThirdRound(Graphics2D g2D) {
        g2D.drawLine(700, 250, 1000, 250);
    }

    // EFFECTS: Prints all competitor names from list of Competitor onto JPanel
    public void printCompetitors(ArrayList<Competitor> competitorList, Graphics2D g2D) {
        Competitor one = competitorList.get(0);
        g2D.drawString(one.getName(), 100, 40);
        Competitor two = competitorList.get(1);
        g2D.drawString(two.getName(), 100, 70);
        Competitor three = competitorList.get(2);
        g2D.drawString(three.getName(), 100, 190);
        Competitor four = competitorList.get(3);
        g2D.drawString(four.getName(), 100, 220);
        Competitor five = competitorList.get(4);
        g2D.drawString(five.getName(), 100, 340);
        Competitor six = competitorList.get(5);
        g2D.drawString(six.getName(), 100, 370);
        Competitor seven = competitorList.get(6);
        g2D.drawString(seven.getName(), 100, 490);
        Competitor eight = competitorList.get(7);
        g2D.drawString(eight.getName(), 100, 520);

    }
}
