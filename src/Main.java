import java.awt.*;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		Gui gui = new Gui();
		gui.setPreferredSize(new Dimension(500,450));
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.pack();
		gui.setVisible(true);
		gui.setResizable(false);
	}

}
