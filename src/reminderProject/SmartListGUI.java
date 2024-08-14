package reminderProject;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import reminder_Manager.ReminderManager;

import javax.swing.JButton;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;

public class SmartListGUI extends JFrame {

	private JPanel contentPane;
	private JButton Todaybtn;
	private JButton Scheduledbtn;
	private JButton flaggedbtn;
	private JButton backbtn;
	private List<Reminder> allReminders;
	private List<Reminder> flaggedReminders;
	
	public SmartListGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		allReminders = new ArrayList<>();
		loadRemindersFromFile("reminders.txt"); 
		
		Todaybtn = new JButton("Today");
		Todaybtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Reminder> todayReminders = loadTodaysReminders();
		        TodayGUI tgu = new TodayGUI(todayReminders);
		        tgu.setVisible(true);
			}
		});
		
		Scheduledbtn = new JButton("Scheduled");
		Scheduledbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				show_scheduled_GUI();
			}
		});
		
		flaggedbtn = new JButton("Flagged");
		flaggedbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Reminder> flaggedReminders = allReminders.stream()
			            .filter(rem -> "Flagged".equals(rem.getNote()))
			            .collect(Collectors.toList());
			        FlaggedGUI fgu = new FlaggedGUI(flaggedReminders);
			        fgu.setVisible(true);
			}
		});
		
		backbtn = new JButton("back");
		backbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				show_mainGUI();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(Todaybtn, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(Scheduledbtn, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
							.addComponent(flaggedbtn, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(backbtn)
							.addGap(178))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(flaggedbtn, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(Todaybtn, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
							.addComponent(Scheduledbtn, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED, 113, Short.MAX_VALUE)
					.addComponent(backbtn)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
	

	private void loadRemindersFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Reminder rm = parseReminder(line);
                if (rm != null) {
                    allReminders.add(rm);
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load reminders: " + e.getMessage());
        }
    }

    private Reminder parseReminder(String data) {
        String[] parts = data.split(",\\s*");
        if (parts.length == 4) {
            return new Reminder(parts[0], parts[1], parts[2], parts[3]);
        }
        return null;
    }

    private List<Reminder> loadTodaysReminders() {
    	if (allReminders == null || allReminders.isEmpty()) {
            System.out.println("No reminders loaded.");
            return Collections.emptyList();  
        }
        LocalDate today = LocalDate.now();
        return allReminders.stream()
            .filter(rem -> rem.getDueDate().startsWith(today.toString()))
            .collect(Collectors.toList());
    }


	void show_mainGUI() {
		mainGUI mgu = new mainGUI();
		mgu.setVisible(true);
	}

	void show_scheduled_GUI() {
		ScheduledGUI sgu = new ScheduledGUI();
		sgu.setVisible(true);
	}
	
	
}
