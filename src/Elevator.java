import java.awt.Graphics;
import java.util.concurrent.Semaphore;

import javax.swing.ImageIcon;

public class Elevator extends Thread {
    Building building;
    ImageIcon sprOpen, sprClose;
    Floor currentFloor, destinationFloor;
    boolean open, available;
    private int y, x;
    int interval;
    Semaphore semaphore;

    public Elevator(Building building, Semaphore semaphore) {
        this.building = building;
        this.semaphore = semaphore;
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

    public Floor getCurrentFloor() {
        return currentFloor;
    }

    public void setAvailable(boolean value) {
        available = value;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setDestination(Floor floor) {
        destinationFloor = floor;
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            visitFloor(destinationFloor);
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
        currentFloor = floor;
    }

    public void draw(Graphics g) {
        if (open) {
            sprOpen.paintIcon(building, g, x, y);
        } else {
            sprClose.paintIcon(building, g, x, y);
        }
    }
}
