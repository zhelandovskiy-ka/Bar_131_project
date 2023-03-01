package bar;

public class Types {
    public static final String BEER = "beer";
    public static final String COCKTAIL = "cocktail";
    public static final String FREE = "free";
    public static final String LIQUOR = "liquor";
    public static final String VODKA = "vodka";
    public static final String WHISKY = "whisky";
    public static final String WINE = "wine";
    public static final String WERMUT = "wermut";
    public static final String AD = "ad";
    public static final String BALM = "balm";
    public static final String GIN = "gin";
    public static final String RUM = "rum";
    public static String getRuTypes(String type) {
        switch (type) {
            case BEER: {
                return "Пиво";
            }
            case COCKTAIL: {
                return "Коктейли";
            }
            case FREE: {
                return "б/а напитки";
            }
            case LIQUOR: {
                return "Ликер";
            }
            case VODKA: {
                return "Водка";
            }
            case WHISKY: {
                return "Виски";
            }
            case WINE: {
                return "Вино";
            }
            case WERMUT: {
                return "Вермут";
            }
            case AD: {
                return "Допы";
            }
            case BALM: {
                return "Бальзам";
            }
            case GIN: {
                return "Джин";
            }
            case RUM: {
                return "Ром";
            }
        }

        return null;
    }
}
