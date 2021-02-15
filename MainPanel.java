import javax.swing.*;
import java.awt.*;

/**
 * Created by JasonWynkoop on 4/30/17.
 */
@SuppressWarnings("DefaultFileTemplate")
class MainPanel extends JPanel {

    public MainPanel(int wb, int hb) {
        super(new BorderLayout());
        View view = new View(wb, hb);
        this.add(view, BorderLayout.CENTER);

    }

}
