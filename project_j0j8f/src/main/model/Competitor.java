package model;

import org.json.JSONObject;
import persistence.Writable;

// Singular competitor with a name signed up to compete in tournament bracket and position on the tournament
// bracket tree page
public class Competitor implements Writable {

    private final String name;

    // Creates a competitor with a name
    public Competitor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        return json;
    }
}

