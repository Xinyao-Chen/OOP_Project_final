package reminderProject;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class ScheduledGUI extends JFrame {

	private JPanel contentPane;
	private DefaultListModel<String> listModel;
	

	
	public ScheduledGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		listModel = new DefaultListModel<>();  
		loadRemindersFromFile("reminders.txt"); 
		setContentPane(contentPane);
		
		JButton backbtn = new JButton("back");
		backbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				show_smartlist_GUI();
			}
		});
		
		JList<String> list = new JList<>(listModel);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(157)
							.addComponent(backbtn, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(list, GroupLayout.PREFERRED_SIZE, 428, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(list, GroupLayout.PREFERRED_SIZE, 231, GroupLayout.PREFERRED_SIZE)
					.addGap(1)
					.addComponent(backbtn))
		);
		contentPane.setLayout(gl_contentPane);
	}
	private void loadRemindersFromFile(String filename) {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    LocalDateTime now = LocalDateTime.now();
	    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            Reminder rm = parseReminder(line, formatter);
	            if (rm != null && !LocalDateTime.parse(rm.getDueDate(), formatter).isBefore(now)) {
	                listModel.addElement(rm.toString());
	            }
	        }
	    } catch (IOException e) {
	        JOptionPane.showMessageDialog(this, "Failed to load reminders: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	private Reminder parseReminder(String data, DateTimeFormatter formatter) {
	    String[] parts = data.split(",\\s*");
	    if (parts.length == 4) {
	        
	        try {
	            LocalDateTime.parse(parts[3], formatter); 
	            return new Reminder(parts[0], parts[1], parts[2], parts[3]);
	        } catch (DateTimeParseException e) {
	            System.err.println("Invalid date format in reminder: " + parts[3]);
	            return null;
	        }
	    }
	    System.err.println("Incomplete data: " + data);
	    return null;
	}

	void show_smartlist_GUI() {
		SmartListGUI sgu = new SmartListGUI();
		sgu.setVisible(true);
	}
}
