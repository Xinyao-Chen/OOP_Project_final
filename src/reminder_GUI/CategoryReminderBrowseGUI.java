package reminder_GUI;

import javax.swing.*;

import reminderProject.Reminder;

import java.awt.*;
import java.awt.event.ActionEvent;
import reminder_Manager.ReminderManager;
import reminder_Model.Category;


public class CategoryReminderBrowseGUI extends JFrame {
    private JTextArea reminderListDisplay;
    private JButton btnAddNewReminder;
    private Category category;
    private ReminderManager reminderManager;

    public CategoryReminderBrowseGUI(Category category, ReminderManager reminderManager) {
        this.category = category;
        this.reminderManager = reminderManager;
        setupUI();
    }

    private void setupUI() {
        setTitle("Reminders in Category: " + category.getName());
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        reminderListDisplay = new JTextArea();
        reminderListDisplay.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(reminderListDisplay);

        btnAddNewReminder = new JButton("Add New Reminder");
        btnAddNewReminder.addActionListener(this::addNewReminder);

        add(scrollPane, BorderLayout.CENTER);
        add(btnAddNewReminder, BorderLayout.SOUTH);

        displayReminders();
        setVisible(true);
    }

    private void displayReminders() {
        java.util.List<Reminder> reminders = reminderManager.getAllReminders();
        reminderListDisplay.setText("");
        boolean found = false;
        for (Reminder r : reminders) {
            if (r.getCategory() != null && r.getCategory().equalsIgnoreCase(category.getName())) {
                reminderListDisplay.append(r.toString() + "\n");
                found = true;
            }
        }
        if (!found) {
            reminderListDisplay.setText("No reminders found in this category.");
        }
    }


    private void addNewReminder(ActionEvent e) {
        
        DefaultListModel<Reminder> model = new DefaultListModel<>();
        reminderManager.getAllReminders().stream()
            .filter(reminder -> !reminder.getCategory().equalsIgnoreCase(category.getName()))
            .forEach(model::addElement);

        JList<Reminder> reminderJList = new JList<>(model);
        reminderJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScroller = new JScrollPane(reminderJList);
        listScroller.setPreferredSize(new Dimension(250, 80));

        
        int result = JOptionPane.showConfirmDialog(null, listScroller, "Select Reminder to Add to Category",
                                                   JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            Reminder selectedReminder = reminderJList.getSelectedValue();
            if (selectedReminder != null) {
                selectedReminder.setCategory(category.getName());
                reminderManager.updateReminder(selectedReminder);
                displayReminders();
            }
        }
    }

}
