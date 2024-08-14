package reminder_Manager;

import reminderProject.Reminder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReminderManager {
    private static ReminderManager instance;
    private List<Reminder> reminders;
    private static final String REMINDERS_FILE_PATH = "reminders.txt";

    private ReminderManager() {
        reminders = new ArrayList<>();
        loadRemindersFromFile();
    }

    public static ReminderManager getInstance() {
        if (instance == null) {
            instance = new ReminderManager();
        }
        return instance;
    }

    private void loadRemindersFromFile() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(REMINDERS_FILE_PATH));
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {  
                    String title = parts[0].trim();
                    String description = parts.length > 1 ? parts[1].trim() : "";
                    String note = parts.length > 2 ? parts[2].trim() : "";
                    String dueDate = parts.length > 3 ? parts[3].trim() : "";
                    String category = parts.length > 4 ? parts[4].trim() : "";
                    
                    Reminder reminder = new Reminder(title, description, note, dueDate, category);
                    reminders.add(reminder);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading reminders from file: " + e.getMessage());
        }
    }


    public void saveRemindersToFile() {
        try (FileWriter writer = new FileWriter(REMINDERS_FILE_PATH, false)) {
            for (Reminder reminder : reminders) {
                writer.write(reminder.getTitle() + ", " + reminder.getDescription() + ", " + reminder.getNote()  + ", " + reminder.getDueDate() + "," + reminder.getCategory() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error saving reminders to file: " + e.getMessage());
        }
    }

    public void addReminder(Reminder reminder) {
        reminders.add(reminder);
        saveRemindersToFile();
    }

    public void deleteReminder(Reminder reminder) {
        reminders.remove(reminder);
        saveRemindersToFile();
    }

    public void updateReminder(Reminder updatedReminder) {
        int index = reminders.indexOf(updatedReminder);
        if (index != -1) {
            reminders.set(index, updatedReminder);
        }
        saveRemindersToFile();
    }

    public List<Reminder> getAllReminders() {
        return new ArrayList<>(reminders);
    }
    
    public List<Reminder> filterReminders(String title, String description, String note, String dueDate, String category) {
        return reminders.stream()
                .filter(r -> (title == null || title.isEmpty() || r.getTitle().contains(title))
                          && (description == null || description.isEmpty() || r.getDescription().contains(description))
                          && (note == null || note.isEmpty() || r.getNote().contains(note))
                          && (dueDate == null || dueDate.isEmpty() || r.getDueDate().contains(dueDate))
                          && (category == null || category.isEmpty() || r.getCategory().equalsIgnoreCase(category)))
                .collect(Collectors.toList());
    }
}
