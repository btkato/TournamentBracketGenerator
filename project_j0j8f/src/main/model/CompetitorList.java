package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// A list of all competitors registered in tournament. List is populated first registered. Can be filled with Bye
// competitors after list > 5 elements and randomized for matchmaking
public class CompetitorList implements Writable {

    private ArrayList<Competitor> competitors;
    public static final int MAX = 8;

    // Creates a list of competitors
    public CompetitorList() {
        competitors = new ArrayList<>();
    }

    // MODIFIES: this, Competitor
    // EFFECTS: Adds competitor to CompetitorList
    public void addCompetitor(Competitor c) {
        competitors.add(c);
        EventLog.getInstance().logEvent(new Event(c.getName() + " added"));
    }

    // REQUIRES: CompetitorList.size() > 0
    // MODIFIES: this
    // EFFECTS: Removes last competitor from CompetitorList
    public void removeCompetitor() {
        String c = competitors.get(getCompetitorListSize() - 1).getName();
        competitors.remove(getCompetitorListSize() - 1);
        EventLog.getInstance().logEvent(new Event(c + " removed"));
    }

    // REQUIRES: CompetitorList.size() > getTournamentSize()
    // MODIFIES: this
    // EFFECTS: Fills remaining spots in competitor list with "bye"
    public void completeCompetitorList() {
        int size = getCompetitorListSize();
        for (int i = size; i < MAX; i++) {
            competitors.add(new Competitor("Bye"));
        }
    }

    // REQUIRES: CompetitorList.size() > getTournamentSize() && CompetitorList.size() > 4
    // MODIFIES: this
    // EFFECTS: Creates a competitor list with matchmaking from nth vs (n/2 + 1) registrant
    //          until n/2 competitors reached
    public void matchmakeCompetitorList() {
        ArrayList<Competitor> matchmaker = new ArrayList<>();
        Competitor first;
        Competitor second;
        for (int i = 0; i < 4; i++) {
            first = competitors.get(i);
            second = competitors.get(i + 4);
            matchmaker.add(first);
            matchmaker.add(second);
        }
        competitors = matchmaker;
        EventLog.getInstance().logEvent(new Event("Matchmaking done"));
    }

    public ArrayList<Competitor> getCompetitorList() {
        return competitors;
    }

    public int getCompetitorListSize() {
        return competitors.size();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Competitors", competitorsToJsonArray());
        return json;
    }

    //EFFECTS: returns competitors in CompetitorList in as JSON array
    public JSONArray competitorsToJsonArray() {
        JSONArray jsonArray = new JSONArray();
        for (Competitor competitor : competitors) {
            jsonArray.put(competitor.toJson());
        }
        return jsonArray;
    }

}

