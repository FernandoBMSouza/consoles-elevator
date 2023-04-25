import java.awt.Graphics;
import java.util.concurrent.Semaphore;

import javax.swing.JPanel;

public class Building extends JPanel {
    private Floor[] floors;
    private Elevator elevator;
    private Semaphore semaphore;

    public Building(int floorsQuantity) {
        semaphore = new Semaphore(1);
        floors = new Floor[floorsQuantity];
        for (int i = 0; i < floors.length; i++) {
            floors[i] = new Floor(this, floorsQuantity - i - 1, i, semaphore);
        }
        elevator = new Elevator(this);
    }

    // #region
    public int getHeight() {
        return (floors.length + 1) * floors[0].getHeight();
    }

    public Floor[] getFloors() {
        return floors;
    }

    public Elevator getElevator() {
        return elevator;
    }
    // #endregion

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < floors.length; i++) {
            floors[i].draw(g);
        }

        elevator.draw(g);

        for (int i = 0; i < floors.length; i++) {
            for (int j = 0; j < floors[i].getPassengers().length; j++) {
                floors[i].getPassengers()[j].draw(g);
            }
        }
    }

    public void paintOver() {
        this.revalidate();
        this.repaint();
    }

    public void start() {
        for (int i = 0; i < floors.length; i++) {
            for (int j = 0; j < floors[i].getPassengers().length; j++) {
                floors[i].getPassengers()[j].start();
            }
        }
        elevator.start();
    }
}
