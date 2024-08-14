package reminder_GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import reminder_Manager.CategoryManager;
import reminder_Model.Category;

public class AddNewCategoryGUI extends JFrame {
    private JTextField nameField;
    private JTextField typeField;
    private JButton btnSave;
    private JButton btnCancel;
    private CategoryManager categoryManager;

    public AddNewCategoryGUI(CategoryManager categoryManager) {
        this.categoryManager = categoryManager;
        setupUI();
    }

    private void setupUI() {
        setTitle("Add New Category");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(0, 2));

        nameField = new JTextField();
        typeField = new JTextField();
        btnSave = new JButton("Save");
        btnCancel = new JButton("Cancel");

        add(new JLabel("Name:"));
        add(nameField);
        add(new JLabel("Type:"));
        add(typeField);
        add(btnSave);
        add(btnCancel);

        btnSave.addActionListener(this::saveCategory);
        btnCancel.addActionListener(e -> this.dispose());

        pack();
        setVisible(true);
    }

    private void saveCategory(ActionEvent e) {
        String name = nameField.getText().trim();
        String type = typeField.getText().trim();
        if (!name.isEmpty() && !type.isEmpty()) {
            Category newCategory = new Category(name);
            categoryManager.addCategory(newCategory);
            JOptionPane.showMessageDialog(this, "Category added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Both name and type must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
