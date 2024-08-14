package reminderProject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import reminder_GUI.BrowseGUI;
import reminder_GUI.CategoryGUI;
import reminder_Manager.ReminderManager;
import reminderProject.Reminder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainGUI extends JFrame {

    private JPanel contentPane;
    private JButton addCategoryBtn;
    private JButton addBtn;
    private JButton browseBtn;
    private JButton smartListBtn;
    private JButton notificationBtn;
    private JButton recurringBtn;

    private ReminderManager reminderManager = ReminderManager.getInstance();

    public mainGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        setupButtons();
        
    }

    private void setupButtons() {
        addBtn = new JButton("Add New Reminder");
        addBtn.addActionListener(e -> show_AddNewReminder_GUI());
        addBtn.setBounds(130, 6, 169, 29);
        contentPane.add(addBtn);

        addCategoryBtn = new JButton("Add Category");
        addCategoryBtn.addActionListener(e -> show_category_GUI());
        addCategoryBtn.setBounds(156, 47, 117, 29);
        contentPane.add(addCategoryBtn);

        browseBtn = new JButton("Browse");
        browseBtn.addActionListener(e -> show_browse_GUI());
        browseBtn.setBounds(156, 89, 117, 29);
        contentPane.add(browseBtn);

        smartListBtn = new JButton("Smart List");
        smartListBtn.addActionListener(e -> show_smartlist_GUI());
        smartListBtn.setBounds(156, 130, 117, 29);
        contentPane.add(smartListBtn);

        notificationBtn = new JButton("Interactive notification manager");
        notificationBtn.addActionListener(e -> show_interactivenotificationmanager_GUI());
        notificationBtn.setBounds(87, 171, 282, 29);
        contentPane.add(notificationBtn);

        recurringBtn = new JButton("Recurring Reminder Manager");
        recurringBtn.addActionListener(e -> show_recurringremindermanager_GUI());
        recurringBtn.setBounds(87, 214, 282, 29);
        contentPane.add(recurringBtn);
    }


    private void show_AddNewReminder_GUI() {
    	AddNewReminderGUI agu = new AddNewReminderGUI();
        agu.setVisible(true);
    }

    private void show_category_GUI() {
    	CategoryGUI cgu = new CategoryGUI(reminderManager);
        cgu.setVisible(true);
    }

    private void show_browse_GUI() {
        BrowseGUI bgu = new BrowseGUI();
        bgu.setVisible(true);
    }

    private void show_smartlist_GUI() {
    	SmartListGUI sgu = new SmartListGUI();
        sgu.setVisible(true);
    }

    private void show_interactivenotificationmanager_GUI() {
    	InteractiveNotificationManagerGUI igu = new InteractiveNotificationManagerGUI();
        igu.setVisible(true);
    }

    private void show_recurringremindermanager_GUI() {
    	RecurringReminderManager rgu = new RecurringReminderManager();
        rgu.setVisible(true);
    }

}