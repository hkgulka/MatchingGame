package persistence;

import org.json.JSONObject;

// An interface representing objects that can be written as JSON objects
public interface Writable {

    // Method taken from Writable interface in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: returns this as a JSON object
    public JSONObject toJson();
}
