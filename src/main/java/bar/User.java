package bar;

public class User {
    private String id;
    private String name;
    private double balance;
    private double totalPayed;
    private double totalDrunk;
    private double totalLost;
    private int countOrders;

    public User(String id, String name, double balance, double totalPayed, double totalDrunk, double totalLost, int countOrders) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.totalPayed = totalPayed;
        this.totalDrunk = totalDrunk;
        this.totalLost = totalLost;
        this.countOrders = countOrders;
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
}
