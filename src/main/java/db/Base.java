package db;

import bar.Types;
import bar.*;
import units.Json;
import units.Main;
import units.Utilits;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class Base {

    private static Base instance;
    private static final String URL = "jdbc:sqlite:";
    private Connection connection;

    public Base(String base_name) {
        try {
            DriverManager.registerDriver(new org.sqlite.JDBC());
            this.connection = DriverManager.getConnection(URL + base_name);
            System.out.println("connection " + base_name + " ok");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Base getInstance() {
        if (instance == null) {
            instance = new Base("bar.db");
        }
        return instance;
    }
    public boolean checkUsersRegistration(long id) {
        return runSQL(SQLQueries.checkUserReg(id)).size() != 0;
    }

    public void addNewUser(String id, String userName) {
        updateSQL(SQLQueries.addNewUser(id, userName));
    }

    public User getUserData(String userId) {
        ResultSet rs = getResultSet(SQLQueries.getUserData(userId));
        try {
            while (rs.next()) {
                String name = rs.getString("name");
                int countOrders = rs.getInt("count_orders");
                double balance = rs.getDouble("balance");
                double totalPayed = rs.getDouble("total_payed");
                double totalDrunk = rs.getDouble("total_drunk");
                double totalLost = rs.getDouble("total_lost");
                Date lastPay = Utilits.getDateFromString(rs.getString("last_pay"));
                Date lastLogin = Utilits.getDateFromString(rs.getString("last_login"));
                Date lastNotification = Utilits.getDateFromString(rs.getString("last_notification"));

                return new User(userId, name, balance, totalPayed, totalDrunk, totalLost, countOrders, lastPay, lastLogin, lastNotification);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    public void updateUserData(String[] commands) {
        User user = getUserData(commands[0]);
        MenuPosition mp = Main.bar.getMenuPositionByName(commands[1]);

        user.setBalance(user.getBalance() - Double.parseDouble(mp.getCost()));
        user.setCountOrders(user.getCountOrders() + 1);
        user.setTotalDrunk(user.getTotalDrunk() + mp.getValue());
        user.setTotalLost(user.getTotalLost() + Double.parseDouble(mp.getCost()));

        updateSQL(SQLQueries.updateUserData(user));
    }

    public double updateUserBalance(String id, String sum) {
        User user = getUserData(id);

        user.setBalance(user.getBalance() + Double.parseDouble(sum));
        user.setTotalPayed(user.getTotalPayed() + Double.parseDouble(sum));

        updateSQL(SQLQueries.updateUserBalance(user));

        return user.getBalance();
    }

    public String getCountOrdersMonth(String userId) {
        String date = Utilits.getTime("YYYY-MM");

        List<String> list = new ArrayList<>(runSQL(SQLQueries.getHistoryByDate(date, userId)));

        return list.get(0);
    }

    public double getSumValueMonth(String userId) {
        String date = Utilits.getTime("YYYY-MM");

        List<String> list = new ArrayList<>(runSQL(SQLQueries.getSumValue(date, userId)));
        System.out.println(list.get(0));

        try {
            return Double.parseDouble(list.get(0));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public String getSumMonth(String userId) {
        String date = Utilits.getTime("YYYY-MM");

        List<String> list = new ArrayList<>(runSQL(SQLQueries.getSum(date, userId)));

        if (list.get(0).equals("null"))
            return "0";
        else
            return list.get(0);
    }

    public String getFavoriteOrderMonth(String userId) {
        String date = Utilits.getTime("YYYY-MM");

        List<String> list = new ArrayList<>(runSQL(SQLQueries.getFavoriteOrderMonth(date, userId)));

        try {
            return getLabelByName(list.get(0));
        } catch (IndexOutOfBoundsException e) {
            return "-";
        }
    }

    public String getFavoriteOrder(String userId) {
        List<String> list = new ArrayList<>(runSQL(SQLQueries.getFavoriteOrder(userId)));

        try {
            return getLabelByName(list.get(0));
        } catch (IndexOutOfBoundsException e) {
            return "-";
        }
    }

    public void updateWarehouseData(String[] commands) {
        MenuPosition mp = Main.bar.getMenuPositionByName(commands[1]);
        List<Component> componentList = mp.getComponentList();

        for (Component component : componentList) {
            WarehousePosition wp = Main.bar.getWarehousePositionLowByName(component);
            System.out.println(wp.getStock() + " - " + component.getValue());
            wp.setStock(wp.getStock() - component.getValue());
            updateSQL(SQLQueries.updateWarehousePosition(wp));
        }

        Main.bar.refreshWarehousePosition();
    }

    public void addToHistory(String[] commands) {
        MenuPosition mp = Main.bar.getMenuPositionByName(commands[1]);

        updateSQL(SQLQueries.addToHistory(mp, commands[0]));
    }

    public void addMenuPosition(String sql) {
        updateSQL(sql);
    }

    public List<MenuPosition> getMenuPositions() {
        ResultSet rs = getResultSet(SQLQueries.getMenuPositions());
        List<MenuPosition> positions = new ArrayList<>();

        try {
            while (rs.next()) {
                String name = rs.getString("name");
                String label = rs.getString("label");
                String type = rs.getString("type");
                String subType = rs.getString("sub_type");
                String picSrc = rs.getString("pic_src");
                String recipe = rs.getString("recipe");
                String description = rs.getString("description");
                String composition = rs.getString("composition");
                List<Component> components = Json.getComponentsFromJson(rs.getString("components"));
//                double cost = rs.getDouble("cost");

                positions.add(new MenuPosition(name, label, type, subType, Types.getRuTypes(type), picSrc, recipe, composition, components, description));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return positions;
    }

    public List<WarehousePosition> getWarehousePositions() {
        ResultSet rs = getResultSet(SQLQueries.getWarehousePositions());
        List<WarehousePosition> positions = new ArrayList<>();

        try {
            while (rs.next()) {
                String name = rs.getString("name");
                String label = rs.getString("label");
                String description = rs.getString("description");
                int count = rs.getInt("count");
                int volume = rs.getInt("volume");
                double cost = rs.getDouble("cost");
                int stock = rs.getInt("stock");

                positions.add(new WarehousePosition(name, label, description, count, volume, cost, stock));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return positions;
    }

    public String getLabelByName(String name) {
        ResultSet rs = getResultSet(SQLQueries.getLabelByName(name));
        String label = "null";
        try {
            while (rs.next()) {
                label = rs.getString("label");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return label;
    }

    public List<User> getUsersList() {
        ResultSet rs = getResultSet(SQLQueries.getUsersList());
        List<User> users = new ArrayList<>();

        try {
            while (rs.next()) {
                String user_id = rs.getString("user_id");
                String user_name = rs.getString("name");

                users.add(new User(user_id, user_name));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return users;
    }

    public String getUserNameById(String id) {
        for (User user : getUsersList()) {
            if (user.getId().equals(id))
                return user.getName();
        }
        return "%NONAME%";
    }

    private Collection<String> runSQL(String sql) {
        Collection<String> res = new ArrayList<>();
        StringBuilder builder = new StringBuilder();

        System.out.println(sql);

        try (Statement statement = this.connection.createStatement()) {
//            System.out.println(sql);
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                String[] columns = getColumns(resultSet.getMetaData());
                while (resultSet.next()) {
                    builder.setLength(0);
                    for (String column : columns) {
//                        builder.append(String.format("%s:%s | ", column, resultSet.getObject(column)));
                        builder.append(resultSet.getObject(column));
                    }
                    res.add(builder.toString());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    private ResultSet getResultSet(String sql) {
        try {
            Statement statement = this.connection.createStatement();

            return statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void updateSQL(String sql) {
        System.out.println(sql);
        try (Statement statement = this.connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e1) {
            System.err.println(e1.getMessage());
        }
    }

    public String[] getColumns(ResultSetMetaData metadata) throws SQLException {

        String[] columns = new String[metadata.getColumnCount()];
        for (int i = 0; i < columns.length; i++) {
            columns[i] = metadata.getColumnName(i + 1);
        }

        return columns;

    }

}
