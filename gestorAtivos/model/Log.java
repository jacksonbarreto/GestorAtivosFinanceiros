package model;

import java.util.ArrayList;

public class Log {
    private ArrayList<Operation> operations;

    public Log(ArrayList<Operation> operations) {
        this.operations = operations;
    }
    public Log() {
        this.operations = new ArrayList<>();
    }
}
