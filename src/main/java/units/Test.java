package units;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class Test {

    public static InlineKeyboardMarkup getPayKeyb() {

        String[] captions = new String[]{"Пополнить", "Вывести", "Pay", "Out", "Back", "Back"};
        String[] callbacks = new String[]{Config.PAY_URL, Config.PAY_URL, Config.PAY_URL, Config.PAY_URL, Config.PAY_URL, Config.PAY_URL};
        int[] typeButtons = new int[]{0,0,0,0,0,1};

        return generateKeyboard(captions, callbacks, 1, 6, typeButtons);

/*        InlineKeyboardButton ikb1 = new InlineKeyboardButton("Пополнить").setUrl(Config.PAY_URL);
        InlineKeyboardButton ikb2 = new InlineKeyboardButton("Вывести").setUrl(Config.PAY_URL);

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(ikb1);
        row1.add(ikb2);

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row1.add(ikb1);
        row1.add(ikb2);

        List<List<InlineKeyboardButton>> listRows = new ArrayList<>();
        listRows.add(row1);

        InlineKeyboardMarkup ikm = new InlineKeyboardMarkup();
        ikm.setKeyboard(listRows);*/
    }

    public static InlineKeyboardMarkup generateKeyboard(String[] captions, String[] callbacks, int rowCount, int elementInRowCount, int[] typeButton) {
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

        InlineKeyboardMarkup ikm = new InlineKeyboardMarkup();
        ikm.setKeyboard(listRows);

        return ikm;
    }

}
