package bar;

public class SubTypes {
    private final static String SHOT = "shot";
    private final static String SHORT = "short";
    private final static String LONG = "long";
    private final static String MARTINI = "martini";

    public static String getBySubtype(String subType) {
        switch (subType) {
            case SHOT: {
                return getShot();
            }
            case SHORT: {
                return getShort();
            }
            case LONG: {
                return getLong();
            }
            case MARTINI: {
                return getMartini();
            }
        }

        return "";
    }
    private static String getLong() {
        return "\uD83C\uDF79";
    }
    private static String getShot() {
        return "\uD83D\uDCA5";
    }
    private static String getShort() {
        return "\uD83E\uDD43";
    }
    private static String getMartini() {
        return "\uD83C\uDF78";
    }

}
