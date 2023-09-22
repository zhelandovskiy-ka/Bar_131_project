package db;

import bar.MenuPosition;
import bar.User;
import bar.WarehousePosition;

public class SQLQueries {
    public static String checkUserReg(long id) {
            return "SELECT user_id FROM users WHERE user_id like '" + id + "';";
    }

    public static String addNewUser(String id, String userName) {
            return "INSERT INTO users VALUES(NULL, " + id + ", '" + userName + "', 0, 0, 0, 0, 0, '', CURRENT_TIMESTAMP, '')";
    }

    public static String getRecipe(String recipeName) {
            return "SELECT components FROM menu WHERE name like '" + recipeName + "';";
    }

    public static String getWarehousePositions() {
            return "SELECT * FROM warehouse;";
    }

    public static String getWarehousePositionByName(String name) {
            return "SELECT * FROM warehouse WHERE name='" + name + "';";
    }

    public static String getMenuPositions() {
            return "SELECT * FROM menu ORDER BY label;";
    }

    public static String getUsersList() {
            return "SELECT user_id, name FROM users;";
    }

    public static String updateWarehousePosition(WarehousePosition warehousePosition) {
        return String.format("UPDATE warehouse SET stock=%s WHERE name='%s';", warehousePosition.getStock(), warehousePosition.getName());
    }

    public static String updateUserData(User user) {
        return String.format("UPDATE users SET last_login=CURRENT_TIMESTAMP, balance=%s, count_orders=%s, total_drunk=%s, total_lost=%s WHERE user_id=%s;", user.getBalance(), user.getCountOrders(), user.getTotalDrunk(), user.getTotalLost(), user.getId());
    }

    public static String updateUserBalance(User user) {
        return String.format("UPDATE users SET last_pay=CURRENT_TIMESTAMP, balance=%s, total_payed=%s WHERE user_id=%s;", user.getBalance(), user.getTotalPayed(), user.getId());
    }

    public static String addToHistory(MenuPosition mp, String id) {
        return String.format("INSERT INTO history VALUES(NULL, CURRENT_TIMESTAMP, '%s', '%s', %s, %s)", mp.getName(), id, mp.getCost(), mp.getValue());
    }

    public static String getUserData(String userId) {
        return "SELECT * FROM users WHERE user_id LIKE '" + userId + "';";
    }

    public static String getLabelByName(String name) {
        return "SELECT label FROM menu WHERE name LIKE '" + name + "';";
    }

    public static String getHistoryByDate(String date, String id) {
     return "select COUNT(\"order\") from history where user='" + id + "' and date > '" + date + "-01 00:00:00' and date < '" + date + "-31 23:59:59';";
    }

    public static String getSumValue(String date, String id) {
     return "select SUM(value) from history where user='" + id + "' and date > '" + date + "-01 00:00:00' and date < '" + date + "-31 23:59:59';";
    }

    public static String getSum(String date, String id) {
     return "select SUM(cost) from history where user='" + id + "' and date > '" + date + "-01 00:00:00' and date < '" + date + "-31 23:59:59';";
    }

    public static String getFavoriteOrderMonth(String date, String id) {
        //select "order" from history where user='129474100' and date > '2023-08-01 00:00:00' and date < '2023-08-31 23:59:59' GROUP by "order" order by COUNT("order") DESC LIMIT 1;
     return "select \"order\" from history where user='" + id + "' and date > '" + date + "-01 00:00:00' and date < '" + date + "-31 23:59:59' GROUP BY \"order\" ORDER BY COUNT(\"order\") DESC LIMIT 1;";
    }

    public static String getFavoriteOrder(String id) {
        //select "order" from history where user='129474100' and date > '2023-08-01 00:00:00' and date < '2023-08-31 23:59:59' GROUP by "order" order by COUNT("order") DESC LIMIT 1;
     return "select \"order\" from history where user='" + id + "' GROUP BY \"order\" ORDER BY COUNT(\"order\") DESC LIMIT 1;";
    }
}
