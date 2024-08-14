package reminder_GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import reminder_Manager.CategoryManager;
import reminder_Manager.ReminderManager;
import reminder_Model.Category;

public class CategoryGUI extends JFrame {
    private JList<Category> categoryList;
    private JButton btnAddNew, btnDelete, btnBrowseReminders;
    private CategoryManager categoryManager;
    private ReminderManager reminderManager;
    private DefaultListModel<Category> listModel;

    public CategoryGUI(ReminderManager reminderManager) {
        this.reminderManager = reminderManager;
        this.categoryManager = new CategoryManager();
        setupUI();
    }

    private void setupUI() {
        setTitle("Categories");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        listModel = new DefaultListModel<>();
        categoryManager.getAllCategories().forEach(listModel::addElement);
        categoryList = new JList<>(listModel);
        categoryList.setCellRenderer(new CategoryCellRenderer());
        categoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        btnAddNew = new JButton("Add New");
        btnDelete = new JButton("Delete");
        btnBrowseReminders = new JButton("Browse Reminders");

        btnAddNew.addActionListener(this::addNewCategory);
        btnDelete.addActionListener(this::deleteCategory);
        btnBrowseReminders.addActionListener(this::browseRemindersInCategory);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnAddNew);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnBrowseReminders);

        add(buttonPanel, BorderLayout.NORTH);
        add(new JScrollPane(categoryList), BorderLayout.CENTER);
        setVisible(true);
    }

    private void addNewCategory(ActionEvent e) {
        JTextField nameField = new JTextField(10);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Enter New Category Name",
                                                   JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            if (!name.isEmpty()) {
                Category newCategory = new Category(name);
                categoryManager.addCategory(newCategory);
                listModel.addElement(newCategory);
            } else {
                JOptionPane.showMessageDialog(this, "Name cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteCategory(ActionEvent e) {
        int index = categoryList.getSelectedIndex();
        if (index != -1) {
            Category selected = listModel.getElementAt(index);
            categoryManager.deleteCategory(selected);
            listModel.removeElementAt(index);
        } else {
            JOptionPane.showMessageDialog(this, "No category selected.", "Selection Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void browseRemindersInCategory(ActionEvent e) {
        Category selectedCategory = categoryList.getSelectedValue();
        if (selectedCategory != null) {
            new CategoryReminderBrowseGUI(selectedCategory, reminderManager);
        } else {
            JOptionPane.showMessageDialog(this, "No category selected.", "Selection Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    static class CategoryCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            setText(value.toString());
            setBackground(isSelected ? Color.BLUE : Color.WHITE);
            setForeground(isSelected ? Color.WHITE : Color.BLACK);
            setOpaque(true);
            return this;
        }
    }
}
