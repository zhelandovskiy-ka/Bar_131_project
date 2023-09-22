package units;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Utilits {
    public static Date getDateFromString(String dateInString) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");

        try {
            Date date = formatter.parse(dateInString);
            System.out.println(date);
            return formatter.parse(dateInString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public static double roundDouble(double d, int count) {
        return Double.parseDouble(roundDoubleString(d, count));
    }

    public static String roundDoubleString(double d, int count) {
        if (d == 0)
            return "0";

        double res = 0;
        String s = "";
        while (res == 0) {
            s = String.format("%." + count + "f", d);
            if (s.contains(","))
                s = s.replace(',', '.');
            res = Double.parseDouble(s);
            count++;
        }

        return s;
    }

    public static String cutDouble(double d) {
        return String.format("%.0f", d);
    }

    public static String makeBold(String text) {
        return "*" + text + "*";
    }

    public static String makeItalic(String text) {
        return "_" + text + "_";
    }

    public static int getRandom(int max) {
        int min = 0;
        return new Random().nextInt((max - min)) + min;
    }

    public static String getTime(String format) {
        return (new SimpleDateFormat(format)).format(new Date());
    }
}
