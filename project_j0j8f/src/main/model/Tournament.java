package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// A tournament with a list of all competitor list divisions
public class Tournament implements Writable {

    private String name;
    private ArrayList<CompetitorList> competitorLists;

    // Creates a tournament with a name and list of CompetitorList
    public Tournament(String name) {
        this.name = name;
        this.competitorLists = new ArrayList<>();
    }

    // EFFECTS: produces the divisions of a tournament
    public ArrayList<CompetitorList> getCompetitorLists() {
        return competitorLists;
    }

    public String getName() {
        return this.name;
    }

    // REQUIRES: CompetitorList.size() == CompetitorList.MAX
    // MODIFIES: this
    // EFFECTS: adds CompetitorList to Tournament
    public void addCompetitorList(CompetitorList competitorList) {
        competitorLists.add(competitorList);
        EventLog.getInstance().logEvent(new Event("Division " + competitorLists.size() + " added"));
    }

    // REQUIRES: divisions < 0
    // MODIFIES: this
    // EFFECTS: removes most recent Competitor list from Tournament
    public void removeCompetitorList() {
        int divisionNumber = competitorLists.size();
        int mostRecent = competitorLists.size() - 1;
        competitorLists.remove(mostRecent);
        EventLog.getInstance().logEvent(new Event("Division " + divisionNumber + " removed"));
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Tournament", name);
        json.put("CompetitorLists", competitorListsToJson());
        return json;
    }

    // EFFECTS: returns CompetitorLists in this Tournament as JSON array
    public JSONArray competitorListsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (CompetitorList list : competitorLists) {
            jsonArray.put(list.toJson());
        }
        return jsonArray;
    }

}