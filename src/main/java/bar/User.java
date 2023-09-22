package bar;

import db.Base;
import units.Utilits;

import java.util.Date;

public class User {
    private String id;
    private String name;
    private double balance;
    private double totalPayed;
    private double totalDrunk;
    private double totalLost;
    private int countOrders;
    private Date lastPay;
    private Date lastLogin;
    private Date lastNotification;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(String id, String name, double balance, double totalPayed, double totalDrunk, double totalLost, int countOrders, Date lastPay, Date lastLogin, Date lastNotification) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.totalPayed = totalPayed;
        this.totalDrunk = totalDrunk;
        this.totalLost = totalLost;
        this.countOrders = countOrders;
        this.lastPay = lastPay;
        this.lastLogin = lastLogin;
        this.lastNotification = lastNotification;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                ", totalPayed=" + totalPayed +
                ", totalDrunk=" + totalDrunk +
                ", countOrders=" + countOrders +
                '}';
    }

    public String getCountOrdersMonth() {
        return Base.getInstance().getCountOrdersMonth(id);
    }
    public String getSumValueMonth() {
        return String.valueOf(Utilits.roundDouble(Base.getInstance().getSumValueMonth(id) / 1000, 2));
    }
    public String getFavoriteOrderMonth() {
        return Base.getInstance().getFavoriteOrderMonth(id);
    }
    public String getFavoriteOrder() {
        return Base.getInstance().getFavoriteOrder(id);
    }

    public String getSumMonth() {
        return Base.getInstance().getSumMonth(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getTotalPayed() {
        return totalPayed;
    }

    public void setTotalPayed(double totalPayed) {
        this.totalPayed = totalPayed;
    }

    public double getTotalDrunk() {
        return totalDrunk;
    }

    public double getTotalDrunkLiters() {
        return Utilits.roundDouble(totalDrunk/ 1000, 2);
    }

    public void setTotalDrunk(double totalDrunk) {
        this.totalDrunk = totalDrunk;
    }

    public int getCountOrders() {
        return countOrders;
    }

    public void setCountOrders(int countOrders) {
        this.countOrders = countOrders;
    }

    public double getTotalLost() {
        return totalLost;
    }

    public void setTotalLost(double totalLost) {
        this.totalLost = totalLost;
    }

    public Date getLastPay() {
        return lastPay;
    }

    public void setLastPay(Date lastPay) {
        this.lastPay = lastPay;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Date getLastNotification() {
        return lastNotification;
    }

    public void setLastNotification(Date lastNotification) {
        this.lastNotification = lastNotification;
    }
}
