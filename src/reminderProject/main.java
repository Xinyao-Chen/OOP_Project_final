package reminderProject;

import java.awt.EventQueue;

public class main {
	public static void main(String[] args) {
	    EventQueue.invokeLater(() -> {
	        try {
	            mainGUI frame = new mainGUI();
	            frame.setVisible(true);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    });
	}

}
