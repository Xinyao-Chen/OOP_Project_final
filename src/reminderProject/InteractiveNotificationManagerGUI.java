package reminderProject;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.event.ActionEvent;

public class InteractiveNotificationManagerGUI extends JFrame {

	private JPanel contentPane;
	private JButton completedbtn;
	private DefaultListModel<String> listModel;
	private int selectedIndex = -1;

	
	public InteractiveNotificationManagerGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		listModel = new DefaultListModel<>();  
		loadRemindersFromFile("reminders.txt"); 
		JList<String> list = new JList<>(listModel);
		
		JButton snoozebtn = new JButton("snooze");
		snoozebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedIndex = list.getSelectedIndex();  
			    if (selectedIndex != -1) {
			        String reminder = listModel.get(selectedIndex);
			        SnoozeGUI sgu = new SnoozeGUI(listModel, selectedIndex);
			        sgu.setVisible(true);
			    } else {
			        JOptionPane.showMessageDialog(InteractiveNotificationManagerGUI.this, "No reminder selected.", "Error", JOptionPane.ERROR_MESSAGE);
			    }}
		});
		
		completedbtn = new JButton("completed");
		completedbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = list.getSelectedIndex();  
		        if (selectedIndex != -1) {
		            completeReminder(selectedIndex);
		            show_completed_GUI();  
		        } else {
		            JOptionPane.showMessageDialog(InteractiveNotificationManagerGUI.this, "Please select a reminder to complete.", "No Selection", JOptionPane.ERROR_MESSAGE);
		        }
			}
		});
		
		JButton reschedulebtn = new JButton("reschedule");
		reschedulebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = list.getSelectedIndex();
		        if (selectedIndex != -1) {
		            String reminder = listModel.get(selectedIndex);
		            RescheduleDate rgu = new RescheduleDate(listModel, reminder, selectedIndex);
		            rgu.setVisible(true);
		        } else {
		            JOptionPane.showMessageDialog(InteractiveNotificationManagerGUI.this, "Please select a reminder to reschedule.", "No Selection", JOptionPane.ERROR_MESSAGE);
		        }
			}
		});
		
		JButton cancelbtn = new JButton("cancel");
		cancelbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				show_mainGUI();
				
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(snoozebtn, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(completedbtn, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(reschedulebtn, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cancelbtn, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(21)
							.addComponent(list, GroupLayout.PREFERRED_SIZE, 395, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(list, GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(snoozebtn)
						.addComponent(completedbtn)
						.addComponent(reschedulebtn)
						.addComponent(cancelbtn))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
	private void loadRemindersFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Reminder rm = parseReminder(line); 
                listModel.addElement(rm.toString()); 
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to load reminders: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
	private Reminder parseReminder(String data) {
	    String[] parts = data.split(",\\s*");
	    if (parts.length < 4) {
	        System.out.println("Incomplete data: " + data);
	        return null; 
	    }
	    return new Reminder(parts[0], parts[1], parts[2], parts[3]); 
	}
	private void completeReminder(int selectedIndex) {
	    if (selectedIndex == -1) {
	        JOptionPane.showMessageDialog(this, "No reminder selected!", "Error", JOptionPane.ERROR_MESSAGE);
	        return;
	    }
	    
	    
	    String completedReminder = listModel.remove(selectedIndex);

	    
	    updateFileAfterCompletion();
	    
	    
	    addReminderToCompleted(completedReminder);
	}

	private void updateFileAfterCompletion() {
	    ArrayList<String> remainingReminders = Collections.list(listModel.elements());

	    try (BufferedWriter writer = new BufferedWriter(new FileWriter("reminders.txt"))) {
	        for (String reminder : remainingReminders) {
	            writer.write(reminder);
	            writer.newLine();
	        }
	    } catch (IOException e) {
	        JOptionPane.showMessageDialog(this, "Failed to update reminders: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}

	private void addReminderToCompleted(String completedReminder) {
	    
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter("completed_reminders.txt", true))) {
	        writer.write(completedReminder);
	        writer.newLine();
	    } catch (IOException e) {
	        JOptionPane.showMessageDialog(this, "Failed to save completed reminder: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}


	void show_mainGUI() {
		mainGUI mgu = new mainGUI();
		mgu.setVisible(true);
	}
	void show_snooze_GUI() {
		SnoozeGUI sgu = new SnoozeGUI(listModel, selectedIndex);
		sgu.setVisible(true);
	}
	void show_completed_GUI() {
		CompletedGUI cgu = new CompletedGUI();
		cgu.setVisible(true);
	}

}
