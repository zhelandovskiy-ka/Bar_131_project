package bar;

import db.Base;
import units.Main;

import java.util.ArrayList;
import java.util.List;

public class Bar {
    private List<MenuPosition> menuPositions = Main.base.getMenuPositions();
    private List<WarehousePosition> warehousePositions = new ArrayList<>();

    public List<MenuPosition> getAvailableMenuPositions(String type) {
        refreshWarehousePosition();
        refreshMenuPositions();

        List<MenuPosition> menuPositions = new ArrayList<>();

        for (MenuPosition menuPosition : this.menuPositions) {
            if (menuPosition.getType().equals(type)) {
                int countInStock = 0;

                for (Component component : menuPosition.getComponentList()) {
                    for (WarehousePosition warehousePosition : warehousePositions) {
                        if (warehousePosition.getName().contains(component.getName())
                                && (warehousePosition.getStock() * warehousePosition.getCount()) >= component.getValue()) {
                            countInStock++;
                            break;
                        }
                    }
                }

                if (countInStock == menuPosition.getComponentList().size())
                    menuPositions.add(menuPosition);
            }
        }

        return menuPositions;
    }

    public List<MenuPosition> getAvailableMenuPositions() {
        refreshWarehousePosition();
        refreshMenuPositions();

        List<MenuPosition> menuPositions = new ArrayList<>();

        for (MenuPosition menuPosition : this.menuPositions) {
            int countInStock = 0;

            for (Component component : menuPosition.getComponentList()) {
                for (WarehousePosition warehousePosition : warehousePositions) {
                    if (warehousePosition.getName().contains(component.getName())
                            && (warehousePosition.getStock() * warehousePosition.getCount()) >= component.getValue()) {
                        countInStock++;
                        break;
                    }
                }
            }

            if (countInStock == menuPosition.getComponentList().size()) {
                menuPositions.add(menuPosition);
            }
        }

        return menuPositions;
    }

    public List<MenuPosition> getMenuPositions() {
        refreshMenuPositions();

        return this.menuPositions;
    }

    public List<WarehousePosition> getAvailableWarehousePositions(String name) {
        refreshWarehousePosition();

        List<WarehousePosition> warehousePositions = new ArrayList<>();

        for (WarehousePosition position : this.warehousePositions) {
            System.out.println(position.getName() + " = " + name + " " + position.getValue());
            if (position.getName().contains(name) && position.getStock() > 0)
                warehousePositions.add(position);
        }

        return warehousePositions;
    }

    public void refreshMenuPositions() {
        menuPositions = Base.getInstance().getMenuPositions();
    }

    public void refreshWarehousePosition() {
        warehousePositions = Base.getInstance().getWarehousePositions();
    }

    public double getCostByComponents(List<Component> components) {
        refreshWarehousePosition();

        double costPosition = 0;

        for (Component component : components) {
            double volume = 0;
            double cost = 0;

            WarehousePosition lowCostPosition = new WarehousePosition(999999);

            for (WarehousePosition position : warehousePositions) {
                if (position.getName().contains(component.getName()) && position.getStock() >= component.getValue()) {
                    if (position.getCost() < lowCostPosition.getCost())
                        lowCostPosition = position;
                }
            }

            volume += lowCostPosition.getValue();
            cost += lowCostPosition.getCost();

            double costByUnit = cost / volume;

            costPosition += costByUnit * component.getValue();
        }

        return costPosition;
    }

    public List<Component> getRecipe(String menuName) {
        for (MenuPosition position : menuPositions) {
            if (position.getName().equals(menuName)) {
                return position.getComponentList();
            }
        }

        return null;
    }

    public MenuPosition getMenuPositionByName(String name) {
        for (MenuPosition menuPosition : menuPositions) {
            if (menuPosition.getName().equals(name))
                return menuPosition;
        }

        return null;
    }

    public WarehousePosition getWarehousePositionLowByName(Component component) {
        refreshWarehousePosition();

        WarehousePosition lowCostPosition = new WarehousePosition(999999);

        for (WarehousePosition warehousePosition : warehousePositions) {
            if (warehousePosition.getName().contains(component.getName()) && (warehousePosition.getStock() * warehousePosition.getCount()) >= component.getValue())
                if (warehousePosition.getCost() < lowCostPosition.getCost())
                    lowCostPosition = warehousePosition;
        }

        return lowCostPosition;
    }

    public boolean inStock(String name) {
        MenuPosition mp = getMenuPositionByName(name);

        for (Component component : mp.getComponentList()) {
            WarehousePosition wp = getWarehousePositionLowByName(component);
            if (wp.getStock() < component.getValue()) {
                return false;
            }
        }
        return true;
    }
}
