import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame implements ActionListener {
    private Building building;
    private JButton btnStart;
    private JPanel pnlUI;

    public Window(Building building) {
        this.building = building;
        windowConfig();
    }

    private JPanel uiConfig() {
        pnlUI = new JPanel();
        pnlUI.setLayout(new BorderLayout());

        btnStart = new JButton("Start");
        btnStart.addActionListener(this);
        pnlUI.add(btnStart);
        return pnlUI;
    }

    private void windowConfig() {
        setLayout(new BorderLayout());
        setTitle("Elevator Game");
        setSize(new Dimension(building.getFloors()[0].getWidth(), building.getHeight()));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        //setResizable(false);
        add(building, BorderLayout.CENTER);
        add(uiConfig(), BorderLayout.SOUTH);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnStart) {
            building.start();
            pnlUI.remove(btnStart);
        }
    }
}
