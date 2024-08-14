package reminderProject;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class RecurringReminderManager extends JFrame {

	private JPanel contentPane;
	private DefaultListModel<String> listModel;
	private int selectedIndex = -1;
	

	
	public RecurringReminderManager() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		listModel = new DefaultListModel<>();  
		loadRemindersFromFile("reminders.txt"); 
		JList<String> list = new JList<>(listModel);
		
		JButton setbtn = new JButton("set recurring");
		setbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedIndex = list.getSelectedIndex();  
			    if (selectedIndex != -1) {
			        String reminder = listModel.get(selectedIndex);
			        show_recurringdate();
			    } else {
			        JOptionPane.showMessageDialog(RecurringReminderManager.this, "No reminder selected.", "Error", JOptionPane.ERROR_MESSAGE);
			    }}
			}
		);
		
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
					.addGap(36)
					.addComponent(setbtn)
					.addPreferredGap(ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
					.addComponent(cancelbtn, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
					.addGap(74))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(list, GroupLayout.PREFERRED_SIZE, 422, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(12, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(list, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(setbtn)
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

	void show_mainGUI() {
		mainGUI mgu = new mainGUI();
		mgu.setVisible(true);
	}
	void show_recurringdate() {
		RecurringDate rgu = new RecurringDate(listModel, selectedIndex);
		rgu.setVisible(true);
	}

}
