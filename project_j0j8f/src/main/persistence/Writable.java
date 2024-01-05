package persistence;

import org.json.JSONObject;

// The following was code was modified from the Writable class in the JsonSerializationDemo project:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/Writable.java
public interface Writable {
    // EFFECTS: return this as a JSON object
    JSONObject toJson();
}
