package units;

import bar.WarehousePosition;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Contstructor {
    private static List<WarehousePosition> warehousePositions = Main.base.getWarehousePositions();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Constructor");
        JComboBox<String> listNames = new JComboBox<>();
        JComboBox<String> listTypes = new JComboBox<>();
        JTextField textFieldName = new JTextField();
        JTextField textFieldLabel = new JTextField();
        JTextField textFieldVolume = new JTextField();
        JButton buttonCopy = new JButton(">");
        JButton buttonAdd = new JButton("Add");
        JButton buttonOk = new JButton("Ok");
        JTextArea textAreaComponents = new JTextArea();
        textAreaComponents.setFont(new Font("Arial", 0, 16));
        textAreaComponents.setLineWrap(true);
        JTextArea textAreaComposition = new JTextArea();
        textAreaComposition.setLineWrap(true);
        textAreaComposition.setFont(new Font("Arial", 0, 16));

        frame.setLayout(new BorderLayout());

        List<String> listNamesNotSort = new ArrayList<>();
        for (WarehousePosition position : warehousePositions) {
            listNamesNotSort.add(position.getName());
        }

        Collections.sort(listNamesNotSort);

        for (String s : listNamesNotSort) {
            listNames.addItem(s);
        }

        listTypes.addItem("beer");
        listTypes.addItem("cocktail");
        listTypes.addItem("free");
        listTypes.addItem("liquor");
        listTypes.addItem("vodka");
        listTypes.addItem("whisky");
        listTypes.addItem("wine");
        listTypes.addItem("wermut");

        buttonAdd.addActionListener(e -> {
            String text = textAreaComponents.getText();
            int size = text.length();
            int pos = textAreaComponents.getText().indexOf("[");
            if (pos != -1)
                text = text.substring(1, text.length() - 1);

            String name = "{\"name\":\"" + listNames.getSelectedItem() + "\",";
            String volume = "\"value\":" + textFieldVolume.getText() + "}";

            if (size > 1)
                text = text + "," + name + volume;
            else
                text = text + name + volume;

            textAreaComponents.setText("");

            textAreaComponents.append("[" + text + "]");
        });

        buttonCopy.addActionListener(e -> {
            textFieldName.setText((String) listNames.getSelectedItem());
        });

        buttonOk.addActionListener(e -> {
            String name = textFieldName.getText();
            String label = textFieldLabel.getText();
            String type = (String) listTypes.getSelectedItem();
            String pic = "pics/" + name + ".png";
            String components = textAreaComponents.getText();

            String text = String.format("INSERT INTO menu VALUES ('%s', '%s', '', '%s', '%s', '%s', '', '', '');", name, type, label, pic, components);
            textAreaComposition.setText(text);
            Main.base.addMenuPosition(text);
            textAreaComponents.setText("");
        });

        listNames.addActionListener(e -> {
            textFieldName.setText((String) listNames.getSelectedItem());
        });

        JPanel panelTop = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.20;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);
        panelTop.add(listTypes, c);
        c.gridx = 1;
        panelTop.add(listNames, c);
        c.gridx = 2;
        c.weightx = 0;
        panelTop.add(buttonCopy, c);
        c.gridx = 3;
        c.weightx = 0.2;
        panelTop.add(textFieldName, c);
        c.gridx = 4;
        panelTop.add(textFieldLabel, c);
        c.gridx = 5;
        panelTop.add(textFieldVolume, c);
        c.gridx = 6;
        c.weightx = 0;
        panelTop.add(buttonAdd, c);
        c.gridx = 7;
        panelTop.add(buttonOk, c);

        JPanel panelCenter = new JPanel(new GridBagLayout());
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 0.5;
        c.weightx = 1;
        c.fill = GridBagConstraints.BOTH;
        panelCenter.add(textAreaComponents, c);
        c.gridy = 1;
        panelCenter.add(textAreaComposition, c);

        frame.add(panelTop, BorderLayout.NORTH);
        frame.add(panelCenter, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setVisible(true);

    }
}
