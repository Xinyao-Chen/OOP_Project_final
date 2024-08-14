package reminderProject;

import java.awt.EventQueue;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import java.awt.event.ActionEvent;

public class RecurringDate extends JFrame {

	private JPanel contentPane;
	private DefaultListModel<String> listModel;
    private int reminderIndex;

	
	public RecurringDate(DefaultListModel<String> listModel, int reminderIndex) {
		this.listModel = listModel;
        this.reminderIndex = reminderIndex;
        
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JButton tenbtn = new JButton("10 days later");
		tenbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setRecurring(10);
			}
		});
		
		JButton fivebtn = new JButton("5 days later");
		fivebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setRecurring(5);
			}
		});
		
		JButton fifthteenbtn = new JButton("15 days later");
		fifthteenbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setRecurring(15);
			}
		});
		
		JButton cancelbtn = new JButton("cancel ");
		cancelbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				show_recurringremindermanager_GUI();
			}
		});
		
		JButton custombtn = new JButton("custom date");
		custombtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				customSnooze();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(155)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(custombtn)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
							.addComponent(cancelbtn, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(fifthteenbtn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(tenbtn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(fivebtn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addContainerGap(160, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(24)
					.addComponent(fivebtn)
					.addGap(29)
					.addComponent(tenbtn)
					.addGap(26)
					.addComponent(fifthteenbtn)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(custombtn)
					.addPreferredGap(ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
					.addComponent(cancelbtn)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
	private void setRecurring(int days) {
        if (reminderIndex != -1) {
            try {
                String currentReminder = listModel.get(reminderIndex);
                String[] parts = currentReminder.split(",\\s*");
                LocalDateTime dueDate = LocalDateTime.parse(parts[3], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                dueDate = dueDate.plusDays(days);
                parts[3] = dueDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                String updatedReminder = String.join(", ", parts);
                listModel.set(reminderIndex, updatedReminder);
                updateReminderFile();
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error updating the reminder: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No reminder selected.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void customSnooze() {
        String daysString = JOptionPane.showInputDialog(this, "Enter number of days to snooze:", "Custom Snooze", JOptionPane.QUESTION_MESSAGE);
        try {
            int days = Integer.parseInt(daysString);
            setRecurring(days);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number of days.", "Input Error", JOptionPane.ERROR_MESSAGE);
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

	void show_recurringremindermanager_GUI() {
		RecurringReminderManager rgu = new RecurringReminderManager();
		rgu.setVisible(true);
	}
}
