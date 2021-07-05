package db;

import bar.MenuPosition;
import bar.User;

public class SQLQueries {
    public static String checkUserReg(long id) {
            return "SELECT user_id FROM users WHERE user_id like '" + id + "';";
    }

    public static String addNewUser(String id, String userName) {
            return "INSERT INTO users VALUES(NULL, " + id + ", '" + userName + "', 0, 0, 0, 0, 0)";
    }

    public static String getRecipe(String recipeName) {
            return "SELECT components FROM menu WHERE name like '" + recipeName + "';";
    }

    public static String getWarehousePositions() {
            return "SELECT * FROM warehouse;";
    }

    public static String getMenuPositions() {
            return "SELECT * FROM menu;";
    }

    public static String getUsersList() {
            return "SELECT user_id, name FROM users;";
    }

    public static String updateUserData(User user) {
        return String.format("UPDATE users SET balance=%s, count_orders=%s, total_drunk=%s, total_lost=%s WHERE user_id=%s;", user.getBalance(), user.getCountOrders(), user.getTotalDrunk(), user.getTotalLost(), user.getId());
    }

    public static String addToHistory(MenuPosition mp, String id) {
        return String.format("INSERT INTO history VALUES(NULL, CURRENT_TIMESTAMP, '%s', '%s', %s, %s)", mp.getName(), id, mp.getCost(), mp.getValue());
    }

    public static String getUserData(String userId) {
        return "SELECT * FROM users WHERE user_id LIKE '" + userId + "';";
    }
}
