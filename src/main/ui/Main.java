package ui;

import exceptions.CrashException;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            new BudgetApp();
        } catch (CrashException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
