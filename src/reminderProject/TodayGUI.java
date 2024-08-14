package reminderProject;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JCheckBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.event.ActionEvent;

public class TodayGUI extends JFrame {

	private JPanel contentPane;
    private JList<String> morningList;
    private JList<String> afternoonList;
    private JList<String> nightList;
    private DefaultListModel<String> morningModel = new DefaultListModel<>();
    private DefaultListModel<String> afternoonModel = new DefaultListModel<>();
    private DefaultListModel<String> nightModel = new DefaultListModel<>();
	
	public TodayGUI(List<Reminder> reminders) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		
		
		JLabel MorningLabel = new JLabel("Morning");
		
		JLabel Afternoonlabel = new JLabel("Afternoon");
		
		JLabel tonightbtn = new JLabel("Tonight");
		
		JButton backbtn = new JButton("back");
		backbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				show_smartlist_GUI();
			}
		});
		
		filterReminders(reminders);

        morningList = new JList<>(morningModel);
        afternoonList = new JList<>(afternoonModel);
        nightList = new JList<>(nightModel);

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(MorningLabel)
								.addComponent(Afternoonlabel)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(morningList, GroupLayout.PREFERRED_SIZE, 422, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(tonightbtn))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(afternoonList, GroupLayout.PREFERRED_SIZE, 422, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(nightList, GroupLayout.PREFERRED_SIZE, 417, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(171)
							.addComponent(backbtn)))
					.addContainerGap(12, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(MorningLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(morningList, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
					.addGap(3)
					.addComponent(Afternoonlabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(afternoonList, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tonightbtn)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(nightList, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(backbtn)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
	private void filterReminders(List<Reminder> reminders) {
	    LocalDate today = LocalDate.now();
	    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	    for (Reminder rem : reminders) {
	        try {
	            LocalDateTime dateTime = LocalDateTime.parse(rem.getDueDate(), dateTimeFormatter);
	            if (dateTime.toLocalDate().equals(today)) {
	                int hour = dateTime.getHour();
	                if (hour >= 6 && hour < 12) {
	                    morningModel.addElement(rem.toString());
	                } else if (hour >= 12 && hour < 18) {
	                    afternoonModel.addElement(rem.toString());
	                } else{
	                    nightModel.addElement(rem.toString());
	                }
	            }
	        } catch (DateTimeParseException e) {
	            try {
	                LocalDate date = LocalDate.parse(rem.getDueDate(), dateFormatter);
	                if (date.equals(today)) {
	                    morningModel.addElement(rem.toString()); 
	                }
	            } catch (DateTimeParseException ex) {
	                
	                System.err.println("Failed to parse date: " + rem.getDueDate());
	            }
	        }
	    }
	}

	void show_smartlist_GUI() {
		SmartListGUI sgu = new SmartListGUI();
		sgu.setVisible(true);
	}
}
