package bar;

import units.Config;
import units.Main;
import units.Utilits;

import java.util.List;

public class MenuPosition {
    private String name;
    private String label;
    private String type;
    private String picSrc;
    private String recipe;
    private String composition;
    private String description;
    private List<Component> componentList;
    private double cost;

    public MenuPosition(String name, String label, String type, String picSrc, String recipe, String composition, List<Component> componentList, String description) {
        this.name = name;
        this.label = label;
        this.type = type;
        this.picSrc = picSrc;
        this.recipe = recipe;
        this.composition = composition;
        this.componentList = componentList;
        this.description = description;
    }

    @Override
    public String toString() {
        return "MenuPosition{" +
                "name='" + name + '\'' +
                ", label='" + label + '\'' +
                ", type='" + type + '\'' +
                ", picSrc='" + picSrc + '\'' +
                ", recipe='" + recipe + '\'' +
                ", composition='" + composition + '\'' +
                ", description='" + description + '\'' +
                ", componentList=" + componentList +
                ", cost=" + cost +
                '}';
    }

    public double getValue() {
        double value = 0;
        for (Component component : componentList) {
            value += component.getValue();
        }

        return value;
    }

    public List<Component> getComponentList() {
        return componentList;
    }

    public String getComponentListToString() {
        StringBuilder sb = new StringBuilder();

        for (Component component : componentList) {
            sb.append(component.getName());
            sb.append(": ");
            sb.append(component.getValue());
            sb.append("\n");
        }

        return sb.toString();
    }

    public void setComponentList(List<Component> componentList) {
        this.componentList = componentList;
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

    public String getPicSrc() {
        return picSrc;
    }

    public void setPicSrc(String picSrc) {
        this.picSrc = picSrc;
    }

    public String getCost() {
        double d = Main.bar.getCostByComponents(componentList);
        d = d + (d * Config.PERCENT);

        return Utilits.cutDouble(d);
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }
}
