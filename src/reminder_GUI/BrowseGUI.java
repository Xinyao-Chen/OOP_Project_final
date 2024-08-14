package reminder_GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import reminder_Manager.ReminderManager;
import reminderProject.Reminder;

public class BrowseGUI extends JFrame {
    private JPanel reminderListPanel;
    private JButton btnFilter;
    private JButton btnDelete;
    private JButton btnEdit;
    private JButton btnCancel;
    private ReminderManager reminderManager;
    private List<Reminder> filteredReminders;
    private Reminder selectedReminder;

    public BrowseGUI() {
        initializeComponents();
        setupEventHandlers();
    }

    private void initializeComponents() {
        setTitle("Browse Reminders");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        reminderManager = ReminderManager.getInstance();
        filteredReminders = reminderManager.getAllReminders();

        reminderListPanel = new JPanel();
        reminderListPanel.setLayout(new BoxLayout(reminderListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(reminderListPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        btnFilter = new JButton("Filter");
        btnDelete = new JButton("Delete");
        btnEdit = new JButton("Edit");
        btnCancel = new JButton("Cancel");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnFilter);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnCancel);

        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        displayReminders();
    }

    private void setupEventHandlers() {
    	btnFilter.addActionListener(e -> showFilterGUI());
        btnDelete.addActionListener(e -> deleteReminder());
        btnEdit.addActionListener(e -> editReminder());
        btnCancel.addActionListener(e -> this.dispose());
    }
    
    private void showFilterGUI() {
        FilterGUI filterGUI = new FilterGUI(new FilterGUI.FilterListener() {
            @Override
            public void onFilterApply(String title, String description, String note, String dueDate, String category) {
                applyFilters(title, description, note, dueDate, category);
            }
        });
        filterGUI.setVisible(true);
    }
    
    private void applyFilters(String title, String description, String note, String dueDate, String category) {
        filteredReminders = reminderManager.filterReminders(title, description, note, dueDate, category);
        displayReminders();
    }

    private void displayReminders() {
        reminderListPanel.removeAll();  

        
        for (Reminder reminder : filteredReminders) {
            JPanel reminderPanel = new JPanel();
            reminderPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            reminderPanel.add(new JLabel(reminder.toString()));
            reminderPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (selectedReminder != null && selectedReminder == reminder) {
                        selectedReminder = null;
                        reminderPanel.setBackground(reminderListPanel.getBackground());
                    } else {
                        selectedReminder = reminder;
                        for (Component comp : reminderListPanel.getComponents()) {
                            comp.setBackground(reminderListPanel.getBackground());
                        }
                        reminderPanel.setBackground(Color.LIGHT_GRAY);
                    }
                }
            });
            reminderListPanel.add(reminderPanel);
        }
        reminderListPanel.revalidate();
        reminderListPanel.repaint();
    }


    private void editReminder() {
        if (selectedReminder != null) {
            // Create the panel to hold input fields
            JPanel panel = new JPanel(new GridLayout(0, 1));

            // Create and populate the fields
            JTextField titleField = new JTextField(selectedReminder.getTitle());
            JTextField descriptionField = new JTextField(selectedReminder.getDescription());
            JTextField noteField = new JTextField(selectedReminder.getNote());
            JTextField dueDateField = new JTextField(selectedReminder.getDueDate());
            JTextField categoryField = new JTextField(selectedReminder.getCategory());

            panel.add(new JLabel("Title:"));
            panel.add(titleField);
            panel.add(new JLabel("Description:"));
            panel.add(descriptionField);
            panel.add(new JLabel("Note:"));
            panel.add(noteField);
            panel.add(new JLabel("Due Date (yyyy-MM-dd HH:mm:ss):"));
            panel.add(dueDateField);
            panel.add(new JLabel("Category:"));
            panel.add(categoryField);

            // Show the dialog
            int result = JOptionPane.showConfirmDialog(this, panel, "Edit Reminder", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    // Validate and update the reminder
                    LocalDateTime.parse(dueDateField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); // Validate date
                    
                    // Update reminder with new values
                    selectedReminder.setTitle(titleField.getText());
                    selectedReminder.setDescription(descriptionField.getText());
                    selectedReminder.setNote(noteField.getText());
                    selectedReminder.setDueDate(dueDateField.getText());
                    selectedReminder.setCategory(categoryField.getText());

                    // Save the updated reminders to file
                    reminderManager.updateReminder(selectedReminder);
                    displayReminders();
                } catch (DateTimeParseException e) {
                    JOptionPane.showMessageDialog(this, "Invalid date format. Please use yyyy-MM-dd HH:mm:ss", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "No reminder selected.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void deleteReminder() {
        if (selectedReminder != null) {
            reminderManager.deleteReminder(selectedReminder);
            filteredReminders.remove(selectedReminder);
            displayReminders();
        } else {
            JOptionPane.showMessageDialog(this, "No reminder selected.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
