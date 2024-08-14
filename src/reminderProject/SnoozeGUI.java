package reminderProject;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionEvent;

public class SnoozeGUI extends JFrame {

	private JPanel contentPane;
	private DefaultListModel<String> listModel;
    private int reminderIndex;


	public SnoozeGUI(DefaultListModel<String> listModel, int reminderIndex) {
		this.listModel = listModel;
        this.reminderIndex = reminderIndex;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JButton ahourbtn = new JButton("1 hour later");
		ahourbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				snoozeReminder(1);
			}
		});
		
		JButton sixbtn = new JButton("6 hours later");
		sixbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				snoozeReminder(6);
			}
		});
		
		JButton onebtn = new JButton("1 day later");
		onebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				snoozeReminder(24);
			}
		});
		
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
					.addGap(179)
					.addComponent(backbtn)
					.addContainerGap(186, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap(157, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(ahourbtn, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(sixbtn, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(onebtn, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(159))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(ahourbtn)
					.addGap(39)
					.addComponent(sixbtn)
					.addGap(37)
					.addComponent(onebtn)
					.addPreferredGap(ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
					.addComponent(backbtn)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
	private void snoozeReminder(int hours) {
	    if (reminderIndex != -1) {
	        try {
	            String currentReminder = listModel.get(reminderIndex);
	            String[] parts = currentReminder.split(",\\s*");
	            if (parts.length >= 4) {  
	                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	                Date reminderDate = sdf.parse(parts[3]);  
	                Calendar cal = Calendar.getInstance();
	                cal.setTime(reminderDate);
	                cal.add(Calendar.HOUR_OF_DAY, hours);  
	                parts[3] = sdf.format(cal.getTime());
	                String updatedReminder = String.join(", ", parts);
	                listModel.set(reminderIndex, updatedReminder);
	                updateReminderFile();
	                dispose();
	            }
	        } catch (ParseException e) {
	            JOptionPane.showMessageDialog(this, "Error parsing reminder date: " + e.getMessage(), "Date Format Error", JOptionPane.ERROR_MESSAGE);
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    } else {
	        JOptionPane.showMessageDialog(this, "No reminder selected.", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}


    private void updateReminderFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("reminders.txt"))) {
            for (int i = 0; i < listModel.size(); i++) {
                writer.write(listModel.get(i));
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error updating reminders: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        	}
    	}
	
	void show_interactivenotificationmanager_GUI() {
		InteractiveNotificationManagerGUI igu = new InteractiveNotificationManagerGUI();
		igu.setVisible(true);
	}
}
