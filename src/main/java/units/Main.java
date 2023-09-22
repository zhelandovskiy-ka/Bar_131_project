package units;

import bar.Bar;
import bot.Bot;
import db.Base;

public class Main {
    public static Base base = Base.getInstance();
    public static Bar bar = new Bar();

    public static void main(String[] args) {
        Bot.start();
    }
}
