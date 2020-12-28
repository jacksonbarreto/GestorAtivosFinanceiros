package model;

import java.util.ArrayList;

public class Bank {
    private final String name;
    private ArrayList<TermDeposit> termDeposits;

    public Bank(String name, ArrayList<TermDeposit> termDeposits) {
        this.name = name;
        this.termDeposits = termDeposits;
    }

    public Bank(String name) {
        this.name = name;
        termDeposits = new ArrayList<>();
    }
}
