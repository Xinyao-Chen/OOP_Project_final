package reminderProject;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.PasswordView;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class AddNewReminderGUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtEnterYourTitle;
	private JTextField duedatetxt;
	private JTextField Descriptiontxt;
	private JTextField notetxt;
	private List<Reminder> reminderList = new ArrayList();
	private DefaultListModel<String> listModel = new DefaultListModel();
	private JList<String> listView = new JList<>(listModel);
	
	
	 

	public AddNewReminderGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
	    loadRemindersFromFile("reminders.txt"); 
		
	    
		JLabel titlelabel = new JLabel("Title");
		titlelabel.setBounds(19, 17, 43, 16);
		contentPane.add(titlelabel);
		
		txtEnterYourTitle = new JTextField();
		txtEnterYourTitle.setText("");
		txtEnterYourTitle.setBounds(117, 12, 215, 26);
		contentPane.add(txtEnterYourTitle);
		txtEnterYourTitle.setColumns(10);
		
		JLabel Duedatelabel = new JLabel("Due Date");
		Duedatelabel.setBounds(19, 136, 61, 16);
		contentPane.add(Duedatelabel);
		
		duedatetxt = new JTextField();
		duedatetxt.setText("");
		duedatetxt.setBounds(117, 131, 215, 26);
		contentPane.add(duedatetxt);
		duedatetxt.setColumns(10);
		
		JLabel descriptionlabel = new JLabel("Description");
		descriptionlabel.setBounds(19, 58, 83, 16);
		contentPane.add(descriptionlabel);
		
		Descriptiontxt = new JTextField();
		Descriptiontxt.setText("");
		Descriptiontxt.setBounds(117, 53, 215, 26);
		contentPane.add(Descriptiontxt);
		Descriptiontxt.setColumns(10);
		
		JLabel noteLabel = new JLabel("note");
		noteLabel.setBounds(19, 99, 61, 16);
		contentPane.add(noteLabel);
		
		notetxt = new JTextField();
		notetxt.setText("");
		notetxt.setBounds(117, 93, 215, 26);
		contentPane.add(notetxt);
		notetxt.setColumns(10);
		
		JButton savebtn = new JButton("save");
		savebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
		            LocalDateTime dueDate = LocalDateTime.parse(duedatetxt.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		            addreminders(new Reminder(txtEnterYourTitle.getText(), Descriptiontxt.getText(), notetxt.getText(), dueDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
		        } catch (DateTimeParseException ex) {
		            JOptionPane.showMessageDialog(null, "Invalid date format. Please enter the date in yyyy-MM-dd HH:mm:ss format.", "Date Format Error", JOptionPane.ERROR_MESSAGE);
		        }
				
			}
		});
		savebtn.setBounds(301, 196, 117, 29);
		contentPane.add(savebtn);
		
		JButton cancelbtn = new JButton("cancel");
		cancelbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				show_mainGUI();
			}
		});
		cancelbtn.setBounds(301, 237, 117, 29);
		contentPane.add(cancelbtn);
		
		listView = new JList<>(listModel); 
	    listView.setBounds(6, 184, 292, 82);
	    contentPane.add(listView);
	    
	    JLabel flaggedlabel = new JLabel("To flag a reminder, note = flagged");
	    flaggedlabel.setBounds(19, 155, 292, 29);
	    contentPane.add(flaggedlabel);
	    
	}
	void show_mainGUI() {
		mainGUI mgu = new mainGUI();
		mgu.setVisible(true);
	}
	
	private void addreminders(Reminder reminder) {
		System.out.println("Title: " + reminder.getTitle()); 
	    System.out.println("Description: " + reminder.getDescription());
	    System.out.println("Note: " + reminder.getNote());
	    System.out.println("Due Date: " + reminder.getDueDate());
	    System.out.print(reminderList);
	    
	    if (!reminder.getTitle().isEmpty() ||
	        !reminder.getDescription().isEmpty() ||
	        !reminder.getNote().isEmpty() ||
	        !reminder.getDueDate().isEmpty()) {
	        reminderList.add(reminder); 
	        listModel.addElement(reminder.toString());
	        saveRemindersToFile(reminderList, "reminders.txt"); 
	        listView.validate();
	        listView.repaint();
	    } else {
	        JOptionPane.showMessageDialog(this, "No field is filled", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	public void saveRemindersToFile(List<Reminder> reminders, String filename) {
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
	        for (Reminder rm : reminders) {
	            writer.write(rm.toString());
	            writer.newLine(); 
	        }
	        writer.flush();
	    } catch (IOException e) {
	        JOptionPane.showMessageDialog(this, "Failed to save reminders: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	public void loadRemindersFromFile(String filename) {
	    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            
	            Reminder rm = parseReminder(line);
	            reminderList.add(rm);
	            listModel.addElement(rm.toString());
	        }
	    } catch (IOException e) {
	        JOptionPane.showMessageDialog(this, "Failed to load reminders: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}

	
	private Reminder parseReminder(String data) {
	    String[] parts = data.split(",\\s*");
	    if (parts.length < 4) {
	        System.out.println("Failed to parse reminder: " + Arrays.toString(parts));
	        throw new IllegalArgumentException("Incomplete reminder data: " + data);
	    }

	    
	    return new Reminder(parts[0], parts[1], parts[2], parts[3]);
	}
}