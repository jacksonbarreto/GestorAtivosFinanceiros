package model;

import java.util.ArrayList;
import java.util.List;

public class LogSystem {
    private static LogSystem INSTANCE;
    private final List<SystemOperation> systemOperations;

    private LogSystem(){
        this.systemOperations = new ArrayList<>();
    }

    private static LogSystem getInstance() {
        if (INSTANCE == null) {
            synchronized (LogSystem.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LogSystem();
                }
            }
        }
        return INSTANCE;
    }

    public void registerOccurrence(String message) {
            getInstance().systemOperations.add(new SystemOperation(message));
    }

    public List<SystemOperation> getSystemOperations() {
        return systemOperations;
    }
}
