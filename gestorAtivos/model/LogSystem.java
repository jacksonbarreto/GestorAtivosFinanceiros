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

    public void registerOcurrence(SystemOperation systemOperation) {
        if (systemOperation == null)
            throw new IllegalArgumentException();
        getInstance().systemOperations.add(systemOperation);
    }

    public List<SystemOperation> getSystemOperations() {
        return systemOperations;
    }
}
