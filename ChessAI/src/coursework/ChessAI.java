package coursework;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class ChessAI extends JFrame {
	
	public ChessAI() {
		setSize(1280, 720);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Chess AI");
		setVisible(true);
		add(new MainPanel());
	}

	public static void main(String[] args) {
		   SwingUtilities.invokeLater(new Runnable() {
		         @Override
		         public void run() {
		            new ChessAI();
		         }
		      });
	}

}
