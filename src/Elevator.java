import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Elevator extends Thread {
    Building building;
    ImageIcon sprOpen, sprClose;
    Floor currentFloor;
    boolean open;
    int y, x;
    long interval;

    public Elevator(Building building) {
        this.building = building;
        sprOpen = new ImageIcon(getClass().getResource(".\\content\\elevator_open.png"));
        sprClose = new ImageIcon(getClass().getResource(".\\content\\elevator_closed.png"));
        currentFloor = building.getFloors()[0];
        y = currentFloor.getY();
        x = 30;
        open = false;
        interval = 5;
    }

    @Override
    public void run() {
        super.run();
        // visitFloor(building.getFloors()[5]);
    }

    public void visitFloor(Floor destinationFloor) {
        while (y != destinationFloor.getY()) {
            if (destinationFloor.getY() > y) {
                y++;
            } else {
                y--;
            }

            building.paintOver();

            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        currentFloor = destinationFloor;
    }

    public void draw(Graphics g) {
        if (open) {
            sprOpen.paintIcon(building, g, x, y);
        } else {
            sprClose.paintIcon(building, g, x, y);
        }
    }
}
