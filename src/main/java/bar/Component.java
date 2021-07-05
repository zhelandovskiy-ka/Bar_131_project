package bar;

public class Component {
    private String name;
    private double value;

    public Component(String name, double value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Component{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
