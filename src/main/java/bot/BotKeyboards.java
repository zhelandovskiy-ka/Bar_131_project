package bot;

import bar.MenuPosition;
import bar.Types;
import bar.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import units.Config;
import units.Main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BotKeyboards {

    public static final String CODE_MAIN_MENU = "get_main_menu";
    public static final String CODE_ACCESS = "get_access";
    public static final String CODE_ACCESS_YES = "get_access_yes";
    public static final String CODE_ACCESS_NO = "get_access_no";
    public static final String CODE_MENU = "get_menu";
    public static final String CODE_COCKTAIL = Types.COCKTAIL;
    public static final String CODE_WHISKY = Types.WHISKY;
    public static final String CODE_WINE = Types.WINE;
    public static final String CODE_BEER = Types.BEER;
    public static final String CODE_LIQUOR = Types.LIQUOR;
    public static final String CODE_ALC_FREE = Types.FREE;
    public static final String CODE_VODKA = Types.VODKA;
    public static final String CODE_WERMUT = Types.WERMUT;
    public static final String CODE_BALM = Types.BALM;
    public static final String CODE_GIN = Types.GIN;
    public static final String CODE_RUM = Types.RUM;
    public static final String CODE_MAKE_ORDER = "make_order";
    public static final String CODE_SOMEONE_ORDER = "someone_order";
    public static final String CODE_ORDER_INFO = "order_info";
    public static final String CODE_ORDER_WH_INFO = "order_wh_info";
    public static final String CODE_ORDER_YES = "order_yes";
    public static final String CODE_ORDER_NO = "order_no";
    public static final String CODE_BALANCE = "get_balance";
    public static final String CODE_STATS = "get_stats";
    public static final String CODE_PAY = "pay";
    public static final String CODE_ADD_BALANCE = "add_balance";
    public static final String CODE_DEL_MES = "delete_message";
    public static final String CODE_ADD_FUNDS = "add_funds";
    public static final String CODE_ADD_FUNDS_USER = "add_funds_user";
    public static final String CODE_ADMIN_MENU = "admin_menu";

    public static InlineKeyboardMarkup getMainMenuKeyb() {
        String[] captions = new String[]{"Меню \uD83D\uDCDC", "Баланс \uD83D\uDCB8", "Моя статистика \uD83D\uDCC8"};
        String[] callbacks = new String[]{CODE_MENU, CODE_BALANCE, CODE_STATS};
        int[] typeButtons = new int[]{0, 0, 0};

        return generateKeyboard(captions, callbacks, typeButtons, 3, 1, null);
    }

    public static InlineKeyboardMarkup getAccessesKeyb() {
        String[] captions = new String[]{"Запросить доступ"};
        String[] callbacks = new String[]{CODE_ACCESS};
        int[] typeButtons = new int[]{0};

        return generateKeyboard(captions, callbacks, typeButtons, 1, 1, null);
    }

    public static InlineKeyboardMarkup getAccessesButKeyb(long userId, String name) {
        String[] captions = new String[]{"Да", "Нет"};
        String[] callbacks = new String[]{CODE_ACCESS_YES + "|" + userId + ">" + name, CODE_ACCESS_NO + "|" + userId + ">" + name};
        int[] typeButtons = new int[]{0, 0};

        return generateKeyboard(captions, callbacks, typeButtons, 2, 1, null);
    }

    public static InlineKeyboardMarkup getOrderKeyb(long userId, String orderName) {
        String[] captions = new String[]{"Выполнить", "Отклонить"};
        String[] callbacks = new String[]{CODE_ORDER_YES + "|" + userId + ">" + orderName, CODE_ORDER_NO + "|" + userId + ">" + orderName};
        int[] typeButtons = new int[]{0, 0};

        return generateKeyboard(captions, callbacks, typeButtons, 2, 1, null);
    }

/*    public static InlineKeyboardMarkup getCocktailMenuKeyb(String type) {
        List<MenuPosition> menuPositionList = Main.bar.getAvailableMenuPositions(type);

        int size = menuPositionList.size();

        String[] captions = new String[size];
        String[] callbacks = new String[size];
        int[] typeButtons = new int[size];

        for (int i = 0; i < size; i++) {
            MenuPosition mp = menuPositionList.get(i);
            captions[i] = mp.getLabel();
            callbacks[i] = CODE_ORDER_INFO + "|" + mp.getName();
            typeButtons[i] = 0;
        }

        return generateKeyboard(captions, callbacks, typeButtons, size, 1, CODE_MENU);
    }*/

    public static InlineKeyboardMarkup getMenuByItemTypeKeyb(String type) {
        List<MenuPosition> menuPositionList = Main.bar.getAvailableMenuPositions(type);

        int size = menuPositionList.size();

        String[] captions = new String[size];
        String[] callbacks = new String[size];
        int[] typeButtons = new int[size];

        for (int i = 0; i < size; i++) {
            MenuPosition mp = menuPositionList.get(i);
            captions[i] = mp.getLabel();
            callbacks[i] = CODE_ORDER_INFO + "|" + mp.getName();
            typeButtons[i] = 0;
        }

        int rowCount = size;
        int elementInRowCount = 1;

        if (size > 8) {
            elementInRowCount = 2;
            rowCount = rowCount / 2;
            if (rowCount * elementInRowCount < size)
                rowCount++;
        }

        return generateKeyboard(captions, callbacks, typeButtons, rowCount, elementInRowCount, CODE_MENU);
//        return generateKeyboard(captions, callbacks, typeButtons, 8, 2, CODE_MENU);
    }

    public static InlineKeyboardMarkup getMenuKeyb() {
        List<String> captionsList = new ArrayList<>();
        Set<String> callbacksList = new HashSet<>();
        List<MenuPosition> menuPositionList = Main.bar.getAvailableMenuPositions();

        for (MenuPosition menuPosition : menuPositionList) {
            callbacksList.add(menuPosition.getType());
        }
        for (String s : callbacksList) {
            captionsList.add(Types.getRuTypes(s));

        }
        String[] captions = new String[captionsList.size()];
        captionsList.toArray(captions);
        String[] callbacks = new String[callbacksList.size()];
        callbacksList.toArray(callbacks);

//        String[] callbacks = new String[]{CODE_COCKTAIL, CODE_WHISKY, CODE_WINE, CODE_BEER, CODE_LIQUOR, CODE_ALC_FREE};
        int[] typeButtons = new int[captionsList.size()];
        for (int i = 0; i < typeButtons.length; i++) {
            typeButtons[i] = 0;
        }

        double d = (double) captions.length / 2;

        int rowCount = captions.length / 2;

        if (d > rowCount)
            rowCount++;

        return generateKeyboard(captions, callbacks, typeButtons, rowCount, 2, null);
    }

    public static InlineKeyboardMarkup getOrderInfoKeyb(String name, boolean isAdmin) {
        String[] captions;
        String[] callbacks;
        int[] typeButtons;
        int rowCount;

        if (isAdmin) {
            captions = new String[]{"Заказать", "Заказать за другого"};
            callbacks = new String[]{CODE_MAKE_ORDER + "|" + name, CODE_SOMEONE_ORDER + "|" + name};
            typeButtons = new int[]{0, 0};
            rowCount = 2;
        } else {
            captions = new String[]{"Заказать"};
            callbacks = new String[]{CODE_MAKE_ORDER + "|" + name};
            typeButtons = new int[]{0};
            rowCount = 1;
        }

        return generateKeyboard(captions, callbacks, typeButtons, rowCount, 1, CODE_DEL_MES);
    }

    public static InlineKeyboardMarkup getPayKeyb() {
        String[] captions = new String[]{"Пополнить (Tinkoff)"};
        String[] callbacks = new String[]{Config.PAY_URL_2};
        int[] typeButtons = new int[]{1};

        return generateKeyboard(captions, callbacks, typeButtons, 1, 1, null);
    }

    public static InlineKeyboardMarkup getAdminKeyboard() {
        String[] captions = new String[]{"Добавить средства"};
        String[] callbacks = new String[]{CODE_ADD_FUNDS};
        int[] typeButtons = new int[]{0};

        return generateKeyboard(captions, callbacks, typeButtons, 1, 1, null);
    }


    public static InlineKeyboardMarkup getUsersListKeyb(String option1, String option2, String backBtnCallBack) {
        List<User> users = Main.base.getUsersList();

        int size = users.size();

        String[] captions = new String[size];
        String[] callbacks = new String[size];
        int[] typeButtons = new int[size];

        for (int i = 0; i < size; i++) {
            User user = users.get(i);
            captions[i] = user.getName();
            callbacks[i] = option1 + "|" + user.getId() + ">" + option2;
            typeButtons[i] = 0;
        }

        int rowCount = size;
        int elementInRowCount = 1;

        if (size > 8) {
            elementInRowCount = 2;
            rowCount = rowCount / 2;
            if (rowCount * elementInRowCount < size)
                rowCount++;
        }

        return generateKeyboard(captions, callbacks, typeButtons, rowCount, elementInRowCount, backBtnCallBack);
    }

    public static InlineKeyboardMarkup getBackButtonKeyb(String backCode) {
        String[] captions = new String[]{"Назад"};
        String[] callbacks = new String[]{backCode};
        int[] typeButtons = new int[]{0};

        return generateKeyboard(captions, callbacks, typeButtons, 1, 1, null);
    }

    public static ReplyKeyboardMarkup getKeyboardBottom(boolean isAdmin) {
        KeyboardRow kr1 = new KeyboardRow();
        kr1.add("Меню \uD83D\uDCDC");
        kr1.add("Баланс \uD83D\uDCB8");
        kr1.add("Статистика \uD83D\uDCC8");
        if (isAdmin)
            kr1.add("Админ");

        List<KeyboardRow> rowList = new ArrayList<>();
        rowList.add(kr1);

        ReplyKeyboardMarkup ikm = new ReplyKeyboardMarkup();
        ikm.setKeyboard(rowList);
        ikm.setResizeKeyboard(true);

        return ikm;
    }

    public static InlineKeyboardMarkup generateKeyboard(String[] captions, String[] callbacks, int[] typeButton, int rowCount, int elementInRowCount, String backButtonCallback) {
        if (captions.length == callbacks.length && captions.length == typeButton.length) {
            int currentRow = 0;
            int currentElement = 0;

            List<List<InlineKeyboardButton>> listRows = new ArrayList<>();
            List<InlineKeyboardButton> row = new ArrayList<>();

            for (int i = 0; i < captions.length; i++) {
                if (currentRow < rowCount) {
                    if (currentElement < elementInRowCount) {
                        InlineKeyboardButton ikb = new InlineKeyboardButton(captions[i]);

                        if (typeButton[i] == 0)
                            ikb.setCallbackData(callbacks[i]);
                        if (typeButton[i] == 1)
                            ikb.setUrl(callbacks[i]);

                        row.add(ikb);
                        currentElement++;
                    }
                    if (currentElement == elementInRowCount || i == captions.length - 1) {

                        currentElement = 0;
                        currentRow++;
                        listRows.add(row);
                        row = new ArrayList<>();
                    }
                }
            }

            if (backButtonCallback != null) {
                row.add(new InlineKeyboardButton("Назад").setCallbackData(backButtonCallback));
                listRows.add(row);
            }

            InlineKeyboardMarkup ikm = new InlineKeyboardMarkup();
            ikm.setKeyboard(listRows);

            return ikm;
        } else {
            System.err.println("Error:BotKeyboard:207 arrays not equals");
        }

        return null;
    }

/*    private static List<InlineKeyboardButton> getBackToMainMenuButton() {
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(new InlineKeyboardButton("Назад").setCallbackData(CODE_MAIN_MENU));
        return row;
    }*/
}

