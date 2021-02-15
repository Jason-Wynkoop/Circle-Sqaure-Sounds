import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by JasonWynkoop on 4/30/17.
 * All the logic and design of the program. Makes rectangles that
 * have a specified or random sound assigned to them.  Sound plays when the
 * circle enters the bounds of the rectangles.  The circle cannot leave the bounds
 * of the frame.
 */
class View extends JPanel {

    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu fileMenu = new JMenu("File");
    private final JMenu soundsMenu = new JMenu("Sounds");
    private final JMenuItem exitButton = new JMenuItem("Exit");
    private final JRadioButtonMenuItem properSoundButton = new JRadioButtonMenuItem("Use Proper Sound");
    private final JRadioButtonMenuItem randomSoundButton = new JRadioButtonMenuItem("Randomize The Used Sound");
    private final int C_WIDTH = 75;
    private final int C_HEIGHT = 75;
    private final int rightBound;
    private final int bottomBound;
    private final ArrayList<String> keysPressed;
    @SuppressWarnings("unchecked")
    private final ArrayList<File> soundsList = new ArrayList();
    private int x_point;
    private int y_point;
    private Clip clip;
    private boolean isRunning = false;

    public View(int rb, int bb) {
        super(new BorderLayout());
        keysPressed = new ArrayList<>();
        rightBound = rb;
        bottomBound = bb;
        x_point = 350;
        y_point = 300;
        File fighterSound = new File("SoundFiles/Fighter.wav");
        soundsList.add(fighterSound);
        File interceptionSound = new File("SoundFiles/Interception.wav");
        soundsList.add(interceptionSound);
        File rageSound = new File("SoundFiles/Rage.wav");
        soundsList.add(rageSound);
        File tingleSound = new File("SoundFiles/Tingle.wav");
        soundsList.add(tingleSound);
        this.setBackground(Color.DARK_GRAY);
        this.setFocusable(true);
        this.requestFocus();
        this.add(genMenuBar(), BorderLayout.NORTH);
    }

    /**
     * Creates a menu bar and adds the required buttons
     * as well as hotkeys
     *
     * @return - JMenuBar
     */
    private JMenuBar genMenuBar() {
        fileMenu.add(exitButton);
        exitButton.addActionListener(new MenuListener());
        properSoundButton.addActionListener(new MenuListener());
        randomSoundButton.addActionListener(new MenuListener());
        exitButton.setMnemonic(KeyEvent.VK_E);
        properSoundButton.setMnemonic(KeyEvent.VK_P);
        randomSoundButton.setMnemonic(KeyEvent.VK_R);
        soundsMenu.add(properSoundButton);
        soundsMenu.add(randomSoundButton);
        menuBar.add(fileMenu);
        menuBar.add(soundsMenu);
        addKeyListener(new CircleDriver());
        return menuBar;
    }

    /**
     * Paints the 4 rectangles as well as the oval
     *
     * @param g
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        g2D.setStroke(new BasicStroke(5));

        g2D.setColor(Color.BLACK);
        g2D.fillRect(100, 100, 100, 100);
        g2D.setColor(Color.GREEN);
        g2D.drawRect(100, 100, 100, 100);

        g2D.setColor(Color.BLUE);
        g2D.fillRect(100, 500, 100, 100);
        g2D.setColor(Color.YELLOW);
        g2D.drawRect(100, 500, 100, 100);

        g2D.setColor(Color.ORANGE);
        g2D.fillRect(600, 100, 100, 100);
        g2D.setColor(Color.BLACK);
        g2D.drawRect(600, 100, 100, 100);

        g2D.setColor(Color.cyan);
        g2D.fillRect(600, 500, 100, 100);
        g2D.setColor(Color.WHITE);
        g2D.drawRect(600, 500, 100, 100);

        g2D.setColor(Color.LIGHT_GRAY);
        g2D.fillOval(x_point, y_point, C_WIDTH, C_HEIGHT);
    }


    /**
     * Called when an arrow key is hit.  As long as the circle
     * is within the bounds of the frame, it will move in the location
     * according to the elements stored in the array
     */
    private void movePoint() {
        int SPEED = 35;
        if (keysPressed.contains("Up")) {
            if (!(y_point <= 0)) {
                y_point -= SPEED;
            }
        }
        if (keysPressed.contains("Down")) {
            if (!(y_point + C_HEIGHT >= bottomBound)) {
                y_point += SPEED;
            }
        }
        if (keysPressed.contains("Left")) {
            if (!(x_point <= 0)) {
                x_point -= SPEED;
            }
        }
        if (keysPressed.contains("Right")) {
            if (!(x_point + C_WIDTH >= rightBound)) {
                x_point += SPEED;
            }
        }
    }

    /**
     * selects a random file for sound playback
     *
     * @return
     */
    private File getRandomSoundFile() {
        Random rand = new Random();
        int i = rand.nextInt(4);
        return soundsList.get(i);
    }

    /**
     * Opens the specified sound file and tries to play it
     *
     * @param f
     */
    private void triggerSound(File f) {

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(f);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e1) {
            System.out.println("Something went wrong when trying to play the sound...");
            e1.printStackTrace();
        }
    }

    /**
     * Checks the bounds of the circle
     * plays and stops playing accordingly
     */
    private void getSoundIndex() {
        if (isRunning && (x_point < 100 || x_point > 200 || y_point < 100 || y_point > 200)) {
            isRunning = false;
            clip.stop();
        } else if (isRunning && (x_point < 100 || x_point > 200 || y_point < 500 || y_point > 600)) {
            isRunning = false;
            clip.stop();
        } else if (isRunning && (x_point < 600 || x_point > 700 || y_point < 100 || y_point > 200)) {
            isRunning = false;
            clip.stop();
        } else if (isRunning && (x_point < 600 || x_point > 700 || y_point < 500 || y_point <= 600)) {
            isRunning = false;
            clip.stop();
        }

        if (properSoundButton.isSelected() && !isRunning) {
            if (x_point >= 100 && x_point <= 200 && y_point >= 100 && y_point <= 200) {
                triggerSound(soundsList.get(0));
                isRunning = true;
            } else if (x_point >= 100 && x_point <= 200 && y_point >= 500 && y_point <= 600) {
                triggerSound(soundsList.get(1));
                isRunning = true;
            } else if (x_point >= 600 && x_point <= 700 && y_point >= 100 && y_point <= 200) {
                triggerSound(soundsList.get(2));
                isRunning = true;
            } else if (x_point >= 600 && x_point <= 700 && y_point >= 500 && y_point <= 600) {
                triggerSound(soundsList.get(3));
                isRunning = true;
            }

        } else if (randomSoundButton.isSelected() && !isRunning) {
            if (x_point >= 100 && x_point <= 200 && y_point >= 100 && y_point <= 200) {
                triggerSound(getRandomSoundFile());
                isRunning = true;
            } else if (x_point >= 100 && x_point <= 200 && y_point >= 500 && y_point <= 600) {
                triggerSound(getRandomSoundFile());
                isRunning = true;
            } else if (x_point >= 600 && x_point <= 700 && y_point >= 100 && y_point <= 200) {
                triggerSound(getRandomSoundFile());
                isRunning = true;
            } else if (x_point >= 600 && x_point <= 700 && y_point >= 500 && y_point <= 600) {
                triggerSound(getRandomSoundFile());
                isRunning = true;
            }
        }
    }

    /**
     * Listener for menu clicks
     */
    public class MenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String cmd = e.getActionCommand();
            if ("Exit".equals(cmd)) {
                System.exit(0);
            } else if ("Use Proper Sound".equals(cmd)) {
                randomSoundButton.setSelected(false);
            } else if ("Randomize The Used Sound".equals(cmd)) {
                properSoundButton.setSelected(false);
            }
        }
    }

    /**
     * Adds key strokes to an array list to be read by the
     * movePoint() method.  Only stores the required keys
     */
    private class CircleDriver extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    if (!keysPressed.contains("Up")) {
                        keysPressed.add("Up");
                    }
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    if (!keysPressed.contains("Down")) {
                        keysPressed.add("Down");
                    }
                    break;
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    if (!keysPressed.contains("Left")) {
                        keysPressed.add("Left");
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    if (!keysPressed.contains("Right")) {
                        keysPressed.add("Right");
                    }
                    break;
            }
            getSoundIndex();
            movePoint();
            repaint();

        }

        /**
         * Removes the stores strings from the array once the
         * keys are released
         *
         * @param e
         */
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    keysPressed.remove("Up");
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    keysPressed.remove("Down");
                    break;
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    keysPressed.remove("Left");
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    keysPressed.remove("Right");
                    break;
            }
        }
    }
}
