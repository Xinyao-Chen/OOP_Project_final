package reminder_GUI;

import javax.swing.*;
import java.awt.*;

public class FilterGUI extends JFrame {
    private JTextField txtTitleFilter;
    private JTextField txtDescriptionFilter;
    private JTextField txtNoteFilter;
    private JTextField txtDueDateFilter;
    private JTextField txtCategoryFilter;
    private JButton btnApplyFilter;

    public interface FilterListener {
        void onFilterApply(String title, String description, String note, String dueDate, String category);
    }

    private FilterListener listener;

    public FilterGUI(FilterListener listener) {
        this.listener = listener;

        setTitle("Filter Reminders");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10)); 

        txtTitleFilter = new JTextField(20);
        txtDescriptionFilter = new JTextField(20);
        txtNoteFilter = new JTextField(20);
        txtDueDateFilter = new JTextField(20);
        txtCategoryFilter = new JTextField(20);
        btnApplyFilter = new JButton("Apply Filter");

        btnApplyFilter.addActionListener(e -> {
            if (listener != null) {
                listener.onFilterApply(txtTitleFilter.getText(), txtDescriptionFilter.getText(), 
                                       txtNoteFilter.getText(), txtDueDateFilter.getText(), 
                                       txtCategoryFilter.getText());
                dispose();
            }
        });

        panel.add(new JLabel("Title Filter:"));
        panel.add(txtTitleFilter);
        panel.add(new JLabel("Description Filter:"));
        panel.add(txtDescriptionFilter);
        panel.add(new JLabel("Note Filter:"));
        panel.add(txtNoteFilter);
        panel.add(new JLabel("Due Date Filter (yyyy-MM-dd):"));
        panel.add(txtDueDateFilter);
        panel.add(new JLabel("Category Filter:"));
        panel.add(txtCategoryFilter);
        panel.add(new JLabel(""));
        panel.add(btnApplyFilter);

        add(panel);
        setVisible(true);
    }
}
