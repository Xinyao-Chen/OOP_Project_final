package reminderProject;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.awt.event.ActionEvent;

public class RescheduleDate extends JFrame {

	private JPanel contentPane;
    private JTextField datetxt;
    private DefaultListModel<String> listModel; 
    private String reminder; 
    private int index; 

	
	public RescheduleDate(DefaultListModel<String> listModel, String reminder, int index) {
		this.listModel = listModel;
        this.reminder = reminder;
        this.index = index;
	    
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JLabel datelabel = new JLabel("Date");
		
		datetxt = new JTextField();
		datetxt.setText("Enter the date you want to reschedule");
		datetxt.setColumns(10);
		
		JButton savebtn = new JButton("save");
		savebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveNewDate();
			}
		});
		
		JButton cancelbtn = new JButton("cancel");
		cancelbtn.addActionListener(new ActionListener() {
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
							.addGap(25)
							.addComponent(datelabel)
							.addGap(35)
							.addComponent(datetxt, GroupLayout.PREFERRED_SIZE, 274, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(59)
							.addComponent(savebtn, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
							.addGap(68)
							.addComponent(cancelbtn, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(77, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(65)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(datelabel)
						.addComponent(datetxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 125, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(savebtn)
						.addComponent(cancelbtn))
					.addGap(17))
		);
		contentPane.setLayout(gl_contentPane);
	}
	private void saveNewDate() {
        try {
            LocalDateTime newDate = LocalDateTime.parse(datetxt.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String updatedReminder = listModel.get(index).replaceAll("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}", newDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            listModel.set(index, updatedReminder);
            updateReminderFile();
            dispose(); 
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use yyyy-MM-dd HH:mm:ss.", "Date Format Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateReminderFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("reminders.txt"))) {
            for (int i = 0; i < listModel.size(); i++) {
                writer.write(listModel.get(i));
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error updating reminders: " + e.getMessage());
        }
    }
    void show_interactivenotificationmanager_GUI() {
    	InteractiveNotificationManagerGUI igu = new InteractiveNotificationManagerGUI();
    	igu.setVisible(true);
    }
}
