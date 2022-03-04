package persistence;

import model.MatchingGame;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

// A writer that writes JSON representation of a matching game to a file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // Method taken from JSONWriter class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // Method taken from JSONWriter class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: opens the writer; throws FileNotFoundException if destination file
    //          cannot be opened for writing
    public void open() throws FileNotFoundException {
        this.writer = new PrintWriter(this.destination);
    }

    // Method taken from JSONWriter class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: writes a JSON representation of the matching game to file
    public void write(MatchingGame mg) {
        JSONObject json = mg.toJson();
        saveToFile(json.toString(TAB));
    }

    // Method taken from JSONWriter class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: closes the writer
    public void close() {
        this.writer.close();
    }

    // Method taken from JSONWriter class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        this.writer.print(json);
    }
}
