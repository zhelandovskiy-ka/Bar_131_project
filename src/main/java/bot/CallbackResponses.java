package bot;

import bar.MenuPosition;
import bar.User;
import db.Base;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import units.Config;
import units.Main;
import units.Utilits;

public class CallbackResponses {
    //check user registration in db
    public static void checkRegistration(Message queryMessage) {
        long id = queryMessage.getChatId();
        String name = queryMessage.getChat().getFirstName();
        String userName = queryMessage.getChat().getUserName();

        if (Main.base.checkUsersRegistration(id))
            Bot.getBotInstance().sendMes(id, "У тебя уже есть доступ, не балуйся.", false);
        else
            Bot.getBotInstance().sendMesWithKeyb(Config.BOT_ID_MY, "Пользователь " + name + " @" + userName + " (" + id + ") запросил доступ"
                    , BotKeyboards.getAccessesButKeyb(id, userName), false);
    }

    //granting rights to the user
    public static void confirmReg(String queryData) {
        String[] commands = getCommandsFromQuery(queryData);
        String uId = commands[0];
        String uName = commands[1];

        Main.base.addNewUser(uId, uName);

        Bot.getBotInstance().sendMesWithKeyb(Long.parseLong(uId), "Доступ предоставлен", BotKeyboards.getMainMenuKeyb(), false);
    }

    //denying access to a user
    public static void deniedAccess(String queryData) {
        String[] commands = getCommandsFromQuery(queryData);
        String uId = commands[0];

        Bot.getBotInstance().sendMes(Long.parseLong(uId), "Доступ запрещен", false);
    }

    //get menu
    public static void getMenuList(Message queryMessage, boolean edit) {
        long id = queryMessage.getChatId();
        int messageId = queryMessage.getMessageId();

//        Bot.getBotInstance().sendEditMesWithKeyb(id, queryMessage.getMessageId(), "\uD83D\uDD25Выберите раздел\uD83D\uDD25", BotKeyboards.getMenuKeyb());
        if (edit)
            Bot.getBotInstance().sendEditMesWithKeyb(id, messageId, "Выберите раздел \uD83E\uDD14", BotKeyboards.getMenuKeyb());
        else
            Bot.getBotInstance().sendMesWithKeyb(id, "Выберите раздел \uD83E\uDD14", BotKeyboards.getMenuKeyb(), true);

    }

    //get available wines menu for user
    public static void getMenuByItemList(CallbackQuery query, String caption) {
        long id = query.getMessage().getChatId();
        int messageId = query.getMessage().getMessageId();
        String text = query.getData();

        Bot.getBotInstance().sendEditMesWithKeyb(id, messageId, caption, BotKeyboards.getMenuByItemTypeKeyb(text));
    }

    //get main menu
    public static void getMainMenu(Message queryMessage) {
        long id = queryMessage.getChatId();
        int messageId = queryMessage.getMessageId();
        String name = queryMessage.getChat().getFirstName();

        Bot.getBotInstance().sendEditMesWithKeyb(id, messageId, "Приветствую, " + name + "!", BotKeyboards.getMainMenuKeyb());
    }

    //get order info from menu
    public static void getMenuOrderInfo(CallbackQuery query, boolean isAdmin) {
        String[] commands = getCommandsFromQuery(query.getData());
        String orderName = commands[0];
        long id = query.getMessage().getChatId();

        MenuPosition menuPosition = Main.bar.getMenuPositionByName(orderName);

        Bot.getBotInstance().sendPhotoWithKeyb(id
                , Utilits.makeBold(menuPosition.getLabel())
                        + "\n\n"
                        + Utilits.makeItalic(menuPosition.getComposition())
                        + "\n\n"
                        + Utilits.makeItalic(menuPosition.getDescription())
                        + "\n\n"
                        + "Цена: " + menuPosition.getCost() + "\u20BD"
                , menuPosition.getPicSrc(), BotKeyboards.getOrderInfoKeyb(orderName, isAdmin));
    }

/*    //get order info
    public static void getOrderInfo(CallbackQuery query) {
        String[] commands = getCommandsFromQuery(query.getData());
        String orderName = commands[0];
        long id = query.getMessage().getChatId();

        WarehousePosition warehousePosition = Main.bar.getWarehousePositionByName(orderName);

        Bot.getBotInstance().sendMesWithKeyb(id
                , Utilits.makeBold(warehousePosition.getLabel())
                        + "\n\n"
                        + Utilits.makeItalic(warehousePosition.getDescription())
                        + "\n\n"
                        + "Цена: " + warehousePosition.getCost() + "\u20BD"
                , BotKeyboards.getOrderInfoKeyb(orderName), true);
    }*/

    //delete last message
    public static void deleteMessage(CallbackQuery query) {
        long id = query.getMessage().getChatId();
        int message_id = query.getMessage().getMessageId();

        Bot.getBotInstance().deleteMessageById(id, message_id);
    }

    //make order from menu
    public static void makeOrder(CallbackQuery query) {
        long id = query.getMessage().getChatId();
        String name = query.getMessage().getChat().getFirstName();
        String userName = query.getMessage().getChat().getUserName();

        String[] commands = getCommandsFromQuery(query.getData());
        String orderName = commands[0];

        MenuPosition menuPosition = Main.bar.getMenuPositionByName(orderName);

        Bot.getBotInstance().sendMesWithKeyb(Config.BOT_ID_MY, "Пользователь " + name + " @" + userName + " сделал заказ"
                        + "\n\n"
                        + menuPosition.getLabel()
                        + "\n\n"
                        + menuPosition.getComposition()
                        + "\n\n"
//                        + menuPosition.getComponentListToString()
//                        + "\n\n"
                        + menuPosition.getRecipe()
                        + "\n\n"
                        + menuPosition.getCost() + "\u20BD"
                , BotKeyboards.getOrderKeyb(id, orderName), false);

        Bot.getBotInstance().sendMes(id, "Ща всё будет, ожидайте ⏳", false);
    }

    //make order for other user
    public static void makeSomeoneOrder(CallbackQuery query) {

        String[] commands = getCommandsFromQuery(query.getData());
        String orderName = commands[0];


        Bot.getBotInstance().sendMesWithKeyb(Config.BOT_ID_MY, "Выбери пользователя"
                , BotKeyboards.getUsersListKeyb(orderName), false);
    }

    //accept order
    public static void acceptOrder(CallbackQuery query) {
        String[] commands = getCommandsFromQuery(query.getData());

        String label = Base.getInstance("bar.db").getLabelByName(commands[1]);
        System.out.println("label is " + label);

        Main.base.updateUserData(commands);
        Main.base.addToHistory(commands);

        Bot.getBotInstance().sendMes(Long.parseLong(commands[0]), "Заказ \"" + label + "\" выполнен ✅", false);
        Bot.getBotInstance().sendMes(Config.BOT_ID_MY, "Заказ \"" + label + "\" выполнен для @" + Main.base.getUserNameById(commands[0]), false);

        deleteMessage(query);
    }

    //cancel order
    public static void cancelOrder(CallbackQuery query) {
        String[] commands = getCommandsFromQuery(query.getData());

        String label = Base.getInstance("bar.db").getLabelByName(commands[1]);
        System.out.println("label is " + label);

        Bot.getBotInstance().sendMes(Long.parseLong(commands[0]), "Заказ \"" + label + "\" отменен ❌", false);
    }

    //get user balance
    public static void getBalance(Message message) {
        long id = message.getChatId();
//        int messageId = message.getMessageId();
        String name = message.getChat().getFirstName();

        User user = Main.base.getUserData(String.valueOf(id));

//        Bot.getBotInstance().sendEditMesWithKeyb(id, messageId, name + ", ваш баланс составляет:\n\n" + user.getBalance() + "\u20BD", BotKeyboards.getPayKeyb());
        Bot.getBotInstance().sendMesWithKeyb(id, name + ", ваш баланс составляет:\n\n" + Utilits.makeBold(String.valueOf(user.getBalance())) + "\u20BD", BotKeyboards.getPayKeyb(), true);
    }

    //get user stats
    public static void getStats(Message message) {
        long id = message.getChatId();
//        int messageId = message.getMessageId();

        User user = Main.base.getUserData(String.valueOf(id));

        Bot.getBotInstance().sendMes(id,
                Utilits.makeBold("Статистика:\n")
                        + "\nКоличество заказов: " + Utilits.makeBold(String.valueOf(user.getCountOrders()))
                        + "\nВсего выпито (мл): " + Utilits.makeBold(String.valueOf(user.getTotalDrunk()))
                        + "\nВсего потрачено: " + Utilits.makeBold(String.valueOf(user.getTotalLost()))
                        + "\nВсего выплачено: " + Utilits.makeBold(String.valueOf(user.getTotalPayed()))
//                , BotKeyboards.getBackButtonKeyb(BotKeyboards.CODE_MAIN_MENU)
                , true);
    }

    //splitting the request into commands.
    //template: query|command_1>command_2<command_3
    public static String[] getCommandsFromQuery(String queryData) {
        String[] commands = new String[5];

        System.out.println(queryData);

        int pos1 = queryData.indexOf("|");
        int pos2 = queryData.indexOf(">");
        int pos3 = queryData.indexOf("<");

        if (pos1 != -1)
            if (pos2 == -1)
                commands[0] = queryData.substring(pos1 + 1);
            else
                commands[0] = queryData.substring(pos1 + 1, pos2);


        if (pos2 != -1)
            if (pos3 == -1)
                commands[1] = queryData.substring(pos2 + 1);
            else
                commands[1] = queryData.substring(pos2 + 1, pos3);

        if (pos3 != -1)
            commands[2] = queryData.substring(pos3 + 1);

        return commands;
    }
}
