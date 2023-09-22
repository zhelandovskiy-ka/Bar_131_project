package bot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import units.Config;
import units.Logs;
import units.Main;
import units.Utilits;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Bot extends TelegramLongPollingBot {
    private static Bot instance;

    public static Bot getBotInstance() {
        if (instance == null)
            instance = new Bot();

        return instance;
    }

    public static void start() {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {

            telegramBotsApi.registerBot(new Bot());
            System.out.println("bot.Bot starting...");

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return Config.BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return Config.BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        if (message != null) {

            String text = message.getText();
            long id = message.getChatId();
            Logs.writeLog(printUserMessage(message));

            boolean registrationOk = Main.base.checkUsersRegistration(message.getChat().getId());
            boolean isAdmin = id == Config.BOT_ID_MY;

            if (registrationOk) {
                if (text.equals("/start")) {
                    sendReplyKeyb(id, "Приветствую, " + message.getChat().getFirstName() + "!", BotKeyboards.getKeyboardBottom(isAdmin));
                    sendMes(id, getRandomSmile(), false);
                }

                if (text.equals("/menu") || text.equals("Меню \uD83D\uDCDC")) {
                    CallbackResponses.getMenuList(message, false);
                }

                if (text.equals("/balance") || text.equals("Баланс \uD83D\uDCB8")) {
                    CallbackResponses.getBalance(message);
                }

                if (text.equals("/stats") || text.equals("Статистика \uD83D\uDCC8")) {
                    CallbackResponses.sendStats(message.getChatId(), message.getChatId());
                }

                if (isAdmin) {
                    if (text.equals("Админ")) {
                        CallbackResponses.getAdminMenu(message, false);
                    }

                    if (text.contains("/add_user_fundS")) {
                        int pos1 = text.indexOf('S');
                        int pos2 = text.lastIndexOf('_');

                        String user_id = text.substring(pos1 + 2, pos2);
                        String sum = text.substring(pos2 + 1);

                        double newBalance = Main.base.updateUserBalance(user_id, sum);

                        sendMes(Long.parseLong(user_id), "Баланс пополнен на " + sum + "₽ \uD83D\uDCB8\n\n" +
                                Utilits.makeBold("Текущий баланс: " + newBalance + "₽"), true);
                    }
                }
            } else
                sendMesWithKeyb(id, "Приветствую, " + message.getChat().getFirstName() + "!\n\nВидимо ты здесь впервые? Чтобы запросить доступ нажми кнопку ниже ⬇️"
                        , BotKeyboards.getAccessesKeyb(), false);
        }

        if (update.hasCallbackQuery()) {
            CallbackQuery query = update.getCallbackQuery();
            String queryData = query.getData();
            long id = query.getMessage().getChatId();
            boolean isAdmin = id == Config.BOT_ID_MY;

            Logs.writeLog(printQuery(query));

            if (queryData.equals(BotKeyboards.CODE_MAIN_MENU)) {
                CallbackResponses.getMainMenu(query.getMessage());
            }

            if (queryData.equals(BotKeyboards.CODE_ACCESS)) {
                CallbackResponses.checkRegistration(query.getMessage());
            }

            if (queryData.contains(BotKeyboards.CODE_ACCESS_YES)) {
                CallbackResponses.confirmReg(queryData);
            }

            if (queryData.contains(BotKeyboards.CODE_ACCESS_NO)) {
                CallbackResponses.deniedAccess(queryData);
            }

            if (queryData.equals(BotKeyboards.CODE_MENU)) {
                CallbackResponses.getMenuList(query.getMessage(), true);
            }

            if (queryData.equals(BotKeyboards.CODE_COCKTAIL)) {
                CallbackResponses.getMenuByItemList(query, "Меню коктейлей \uD83C\uDF78");
            }

            if (queryData.equals(BotKeyboards.CODE_WINE)) {
                CallbackResponses.getMenuByItemList(query, "Винное меню \uD83C\uDF77");
            }

            if (queryData.equals(BotKeyboards.CODE_BEER)) {
                CallbackResponses.getMenuByItemList(query, "Пивное меню \uD83C\uDF7A");
            }

            if (queryData.equals(BotKeyboards.CODE_WHISKY)) {
                CallbackResponses.getMenuByItemList(query, "Виски \uD83E\uDD43");
            }

            if (queryData.equals(BotKeyboards.CODE_LIQUOR)) {
                CallbackResponses.getMenuByItemList(query, "Ликер");
            }

            if (queryData.equals(BotKeyboards.CODE_ALC_FREE)) {
                CallbackResponses.getMenuByItemList(query, "Безалкогольное \uD83E\uDDC3");
            }

            if (queryData.equals(BotKeyboards.CODE_VODKA)) {
                CallbackResponses.getMenuByItemList(query, "Водка");
            }

            if (queryData.equals(BotKeyboards.CODE_WERMUT)) {
                CallbackResponses.getMenuByItemList(query, "Вермут");
            }

            if (queryData.equals(BotKeyboards.CODE_BALM)) {
                CallbackResponses.getMenuByItemList(query, "Бальзам");
            }

            if (queryData.equals(BotKeyboards.CODE_GIN)) {
                CallbackResponses.getMenuByItemList(query, "Джин");
            }

            if (queryData.equals(BotKeyboards.CODE_RUM)) {
                CallbackResponses.getMenuByItemList(query, "Ром");
            }

            if (queryData.contains(BotKeyboards.CODE_ORDER_INFO)) {
                CallbackResponses.getMenuOrderInfo(query, isAdmin);
            }

//            if (queryData.contains(BotKeyboards.CODE_ORDER_WH_INFO)) {
//                CallbackResponses.getOrderInfo(query);
//            }

            if (queryData.equals(BotKeyboards.CODE_DEL_MES)) {
                CallbackResponses.deleteMessage(query);
            }

            if (queryData.contains(BotKeyboards.CODE_MAKE_ORDER)) {
                CallbackResponses.makeOrder(query);
            }

            if (queryData.contains(BotKeyboards.CODE_SOMEONE_ORDER)) {
                CallbackResponses.makeSomeoneOrder(query);
            }

            if (queryData.contains(BotKeyboards.CODE_ORDER_YES)) {
                CallbackResponses.acceptOrder(query);
            }

            if (queryData.contains(BotKeyboards.CODE_ORDER_NO)) {
                CallbackResponses.cancelOrder(query);
            }

            if (queryData.contains(BotKeyboards.CODE_ADMIN_MENU)) {
                CallbackResponses.getAdminMenu(query.getMessage(), true);
            }

            if (queryData.contains(BotKeyboards.CODE_ADMIN_ADD_FUNDS)) {
                CallbackResponses.addFundsToUser(query);
            }

            if (queryData.contains(BotKeyboards.CODE_ADMIN_USERS_FOR_STATS)) {
                CallbackResponses.getStatsForUser(query);
            }

            if (queryData.contains(BotKeyboards.CODE_ADMIN_ADD_FUNDS_USER)) {
                queryData = queryData.replace('|', '_');
                queryData = queryData.replace('>', '_');
                String text = "/" + queryData;
                sendMes(id, text, false);
            }

            if (queryData.contains(BotKeyboards.CODE_ADMIN_GET_STATS)) {
                int pos = queryData.indexOf('|');
                long idUser = Long.parseLong(queryData.substring(pos + 1, queryData.length() - 1));
                System.out.println(id);
                String text = "/" + queryData;
               CallbackResponses.sendStats(idUser, Config.BOT_ID_MY);

            }
        }
    }

    public void sendMesWithKeyb(long id, String text, InlineKeyboardMarkup ikm, boolean md) {
        SendMessage sm = new SendMessage()
                .setChatId(id)
                .setText(text)
                .setReplyMarkup(ikm);

        if (md)
            sm.setParseMode(ParseMode.MARKDOWN);
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        delay();
    }

    public void sendReplyKeyb(long id, String text, ReplyKeyboardMarkup ikm) {
        SendMessage sm = new SendMessage()
                .setChatId(id)
                .setText(text)
                .setReplyMarkup(ikm);
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        delay();
    }

    public void sendPhotoWithKeyb(long id, String text, String photoUrl, InlineKeyboardMarkup ikm) {
        SendPhoto photo = new SendPhoto()
                .setChatId(id)
                .setCaption(text)
                .setPhoto(new File(photoUrl))
                .setParseMode(ParseMode.MARKDOWN)
                .setReplyMarkup(ikm);

        try {
            execute(photo);

        } catch (TelegramApiException e) {
            String message = e.getMessage();
            System.out.println(message);
            e.printStackTrace();
            if (message.contains("Unable to send photo")) {
                photo.setPhoto(new File("pics\\error.png"));
                try {
                    execute(photo);
                } catch (TelegramApiException ex) {
                    throw new RuntimeException(ex);
                }
            }

        }

        delay();
    }

    public int sendMes(long id, String text, boolean md) {
//        System.out.println(text);
        Message message = new Message();

        SendMessage sm = new SendMessage()
                .setChatId(id)
                .setText(text)
                .disableWebPagePreview();
        if (md)
            sm.setParseMode(ParseMode.MARKDOWN);
        try {
            message = execute(sm);
//                System.out.println(message.getMessageId());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        delay();

        return message.getMessageId();
    }

    public void sendEditMes(long id, int message_id, String text) {

        EditMessageText messageText = new EditMessageText();
        messageText.setMessageId(message_id);
        messageText.setChatId(id);
        messageText.setText(text);

        try {
            execute(messageText);
        } catch (TelegramApiException e) {
            System.out.println(e.getMessage());
        }
    }

    public void sendEditMesWithKeyb(long id, int message_id, String text, InlineKeyboardMarkup ikm) {
        EditMessageText sm = new EditMessageText()
                .setChatId(id)
                .setText(text)
                .setMessageId(message_id)
                .setParseMode(ParseMode.MARKDOWN)
                .setReplyMarkup(ikm);

        try {
            execute(sm);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        delay();
    }

    public void deleteMessageById(long id, int messageId) {

        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(id);
        deleteMessage.setMessageId(messageId);

        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    private String printUserMessage(Message message) {
        return message.getChat().getFirstName() + " " + message.getChat().getUserName() + " (" + message.getChat().getId() + "): " + message.getText();
    }

    private String printQuery(CallbackQuery query) {
        return query.getMessage().getChat().getUserName() + " (" + query.getMessage().getChat().getId() + "): " + query.getData();
    }

    private static String getTime() {
        return new SimpleDateFormat("HH:mm").format(new Date());
    }

    public void delay() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getRandomSmile() {
        String[] smiles = new String[]{"🙋🏿‍♂️", "🙋🏿", "🙋", "🙋‍♂️", "🙋🏾‍♂️", "🙋🏼‍♂️", "🙋‍♀️", "🙋🏿‍♀️", "🙋🏾‍♀️", "🙋🏻‍♀️", "🙋🏼‍♀️", "💩", "🤡", "🙉", "🌝", "😎", "🤘", "🤘🏿", "✌️", "✌🏿", "🤙", "🤙🏿"};

        return smiles[Utilits.getRandom(smiles.length - 1)];
    }

}
