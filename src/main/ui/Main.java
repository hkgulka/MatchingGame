package ui;

import model.Event;
import model.EventLog;
import ui.gui.MatchingGameApp;

// Starts a new instance of a Matching Game.
public class Main {
    public static void main(String[] args) {
        new MatchingGameApp();
        EventLog el = EventLog.getInstance();
        Thread printingHook = new Thread(() -> {
            for (Event next : el) {
                System.out.println(next.toString());
            }
        });
        Runtime.getRuntime().addShutdownHook(printingHook);
    }
}
