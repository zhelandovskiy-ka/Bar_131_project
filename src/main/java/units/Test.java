package units;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        int i = 9;
        double d = (double) i / 2;
        System.out.println(d);
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
