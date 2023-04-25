import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Elevator extends Thread {
    Building building;
    ImageIcon sprOpen, sprClose;
    Floor currentFloor, destinationFloor;
    boolean open, available;
    private int y, x;
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
        available = true;
    }

    public int getX() {
        return x;
    }

    public Floor getFloor() {
        return currentFloor;
    }

    public void isAvailable(boolean value) {
        available = value;
    }

    public void setDestination(Floor floor) {
        destinationFloor = floor;
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            // depois adiciona !available || elevador foi chamado
            if (!available) {
                visitFloor(destinationFloor);
            }

            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void visitFloor(Floor floor) {
        while (y != floor.getY()) {
            if (floor.getY() > y) {
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
