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
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class CompletedGUI extends JFrame {

	private JPanel contentPane;
	private DefaultListModel<String> listModel;
	
	public CompletedGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		listModel = new DefaultListModel<>();  
		loadRemindersFromFile("reminders.txt"); 
		JList<String> list = new JList<>(listModel);
		
		JButton backbtn = new JButton("back");
		backbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				show_interactivenotificationmanager_GUI();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(20)
							.addComponent(list, GroupLayout.PREFERRED_SIZE, 395, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(164)
							.addComponent(backbtn, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(25, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(15)
					.addComponent(list, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
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
	void show_interactivenotificationmanager_GUI() {
		InteractiveNotificationManagerGUI igu = new InteractiveNotificationManagerGUI();
		igu.setVisible(true);
	}

}
