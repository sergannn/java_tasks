package ToDoProject;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import ToDoProject.gui.MainWindow;

public class TodoApp {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		
		MainWindow window = new MainWindow();
		window.setLocationRelativeTo( null );
		window.setVisible(true);
	}


}
