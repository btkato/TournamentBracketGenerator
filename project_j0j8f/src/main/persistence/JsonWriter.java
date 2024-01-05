package persistence;

import model.Tournament;
import org.json.JSONObject;
import java.io.*;

// A writer that writes JSON representations of workroom to file
// The following was code was modified from the JsonWriter class in the JsonSerializationDemo project:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/JsonWriter.java
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens Tournament writer, if destination not found throws FileNotFoundException if destination
    //          file opened for writing
    public void openTournament() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of workroom to file
    public void writeTournament(Tournament tournament) {
        JSONObject json = tournament.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void closeTournament() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    public void saveToFile(String json) {
        writer.print(json);
    }
}
