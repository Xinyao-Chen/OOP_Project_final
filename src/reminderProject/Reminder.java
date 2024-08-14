package reminderProject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Reminder {
    private String title;
    private String description;
    private String note;
    private String due_date;
    private String category;

    
    public Reminder(String title, String description, String note, String dueDate, String category) {
        this.title = title;
        this.description = description;
        this.note = note;
        this.due_date = dueDate;
        this.category = category;
    }

    
    public Reminder(String title, String description, String note, String dueDate) {
        this.title = title;
        this.description = description;
        this.note = note;
        this.due_date = dueDate;
        this.category = "";  
    }

    // Getters
    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getNote() {
        return this.note;
    }

    public String getDueDate() {
        return this.due_date;
    }

    public String getCategory() {
        return this.category;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setDueDate(String dueDate) {
        this.due_date = dueDate;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
		return title + ", " + description + ", " + note + ", " + due_date.formatted(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + (category.isEmpty() ? "" : ", " + category);
	}
}
