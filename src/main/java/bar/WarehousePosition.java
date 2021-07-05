package bar;

public class WarehousePosition {
    private String name;
    private String label;
    private String description;
    private int count;
    private int value;
    private double cost;

    public WarehousePosition(double cost) {
        this.cost = cost;
    }

    public WarehousePosition(String name, String label, String description, int count, int value, double cost) {
        this.name = name;
        this.label = label;
        this.description = description;
        this.count = count;
        this.value = value;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "WarehousePosition{" +
                "name='" + name + '\'' +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", count=" + count +
                ", volume=" + value +
                ", cost=" + cost +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

//    public String getType() {
//        return type;
//    }

//    public void setType(String type) {
//        this.type = type;
//    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
