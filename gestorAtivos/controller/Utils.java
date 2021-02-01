package controller;

import javafx.scene.Parent;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Utils {
    public static final DecimalFormatSymbols dfs = new DecimalFormatSymbols(new Locale("pt", "Portugal"));
    public static final DecimalFormat euroCurrency = new DecimalFormat("€ ###,##0.00", dfs);
    public static final DecimalFormat percentage = new DecimalFormat("##0.00 %", dfs);
    public static final DateTimeFormatter EuropeanDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    static {
        dfs.setDecimalSeparator(',');
    }


    public static void applyStyle(Parent object, String style) {
        object.getStyleClass().add(style);
    }




}
