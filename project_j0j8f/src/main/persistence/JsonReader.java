package persistence;

import model.Competitor;
import model.CompetitorList;
import model.Tournament;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// a JSON reader that reads workroom in this program from JSON data stored
// The following was code was modified from the JsonReader class in the JsonSerializationDemo project:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/JsonReader.java
public class JsonReader {

    private String source;

    // EFFECTS: creates a reader to read stored files
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads tournament from file and returns it.
    //          If and error occurs from reading data from file, IOException is thrown
    public Tournament read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTournament(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    public String readFile(String source) throws  IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // MODIFIES: Tournament
    // EFFECTS: parses tournament from JSON object and adds them to tournament
    public Tournament parseTournament(JSONObject jsonObject) {
        String name = jsonObject.getString("Tournament");
        Tournament tournament = new Tournament(name);
        addCompetitorLists(tournament, jsonObject);
        return tournament;
    }

    // MODIFIES: Tournament
    // EFFECTS: parses CompetitorLists from JSON object and adds it to tournament
    public void addCompetitorLists(Tournament tournament, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("CompetitorLists");
        for (Object json : jsonArray) {
            JSONObject nextCompetitorList = (JSONObject) json;
            tournament.addCompetitorList(addCompetitorList(tournament, nextCompetitorList));
        }
    }

    // MODIFIES: CompetitorList
    // EFFECTS: parses CompetitorList from JSON object and adds it to tournament
    public CompetitorList addCompetitorList(Tournament tournament, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Competitors");
        CompetitorList list = new CompetitorList();
        for (Object json : jsonArray) {
            JSONObject nextCompetitor = (JSONObject) json;
            list.addCompetitor(addCompetitor(tournament, nextCompetitor));
        }
        return list;
    }

    // MODIFIES: Competitor
    // EFFECTS: parses Competitor from JSON object and adds it to tournament
    public Competitor addCompetitor(Tournament tournament, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Competitor competitor = new Competitor(name);
        return competitor;
    }
}
