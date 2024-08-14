package reminder_Manager;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import reminder_Model.Category;

public class CategoryManager {
    private List<Category> categories;
    private static final String CATEGORY_FILE_PATH = "categories.txt";
    private static final String REMINDER_FILE_PATH = "reminders.txt";

    public CategoryManager() {
        categories = new ArrayList<>();
        initializeCategories();
    }

    private void initializeCategories() {
        try {
            Files.lines(Paths.get(REMINDER_FILE_PATH))
                 .map(line -> {
                     String[] parts = line.split(",");
                     return parts.length > 4 ? parts[parts.length - 1].trim() : "";
                 })
                 .filter(cat -> !cat.isEmpty() && !isDate(cat))
                 .distinct()
                 .forEach(cat -> addCategory(new Category(cat)));
        } catch (IOException e) {
            System.err.println("Error initializing categories: " + e.getMessage());
        }
    }

    private boolean isDate(String text) {
        try {
            LocalDate.parse(text, DateTimeFormatter.ISO_LOCAL_DATE);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public void addCategory(Category category) {
        if (categories.stream().noneMatch(c -> c.getName().equals(category.getName()))) {
            categories.add(category);
            saveCategoriesToFile();
        }
    }

    public void deleteCategory(Category category) {
        categories.remove(category);
        saveCategoriesToFile();
        updateRemindersAfterCategoryDeletion(category);
    }

    private void saveCategoriesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CATEGORY_FILE_PATH))) {
            for (Category c : categories) {
                writer.write(c.getName());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing categories: " + e.getMessage());
        }
    }

    private void updateRemindersAfterCategoryDeletion(Category category) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(REMINDER_FILE_PATH));
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(REMINDER_FILE_PATH))) {
                for (String line : lines) {
                    if (!line.endsWith(category.getName())) {
                        writer.write(line);
                        writer.newLine();
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error updating reminders: " + e.getMessage());
        }
    }

    public List<Category> getAllCategories() {
        return new ArrayList<>(categories);
    }
}
