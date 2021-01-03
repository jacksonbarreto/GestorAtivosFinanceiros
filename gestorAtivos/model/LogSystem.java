package model;

import java.util.ArrayList;

public class LogSystem {
    private static LogSystem INSTANCE;
    ArrayList<Operation> operations;

    private LogSystem() {
    }

    public static LogSystem getInstance() {
        if (INSTANCE == null) {
            synchronized (LogSystem.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LogSystem();
                }
            }
        }
        return INSTANCE;
    }

    public void addEvent(Operation event) {
        this.operations.add(event);
    }

}
