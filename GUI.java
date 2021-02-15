import javax.swing.*;

/**
 * Created by JasonWynkoop on 4/30/17.
 * Final Exam Online Portion
 *
 *
 */
class GUI extends JFrame{

    private GUI(){

        setTitle("Sound Blocks : )");
        int WINDOW_HEIGHT = 700;
        int WINDOW_WIDTH = 800;
        setBounds(100, 100, WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        this.add(new MainPanel(WINDOW_WIDTH - 20, WINDOW_HEIGHT - 40));
    }

    public static void main(String[] args){

        GUI GUI = new GUI();
        GUI.setVisible(true);
    }
}
