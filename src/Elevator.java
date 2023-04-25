import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Elevator extends Thread {
    private Building building;
    private ImageIcon sprOpen, sprClose;
    private Floor currentFloor, destinationFloor;
    private boolean open;
    private int y, x;
    private int interval;

    public Elevator(Building building) {
        this.building = building;
        sprOpen = new ImageIcon(getClass().getResource(".\\content\\elevator_open.png"));
        sprClose = new ImageIcon(getClass().getResource(".\\content\\elevator_closed.png"));
        currentFloor = building.getFloors()[0];
        y = currentFloor.getY();
        x = 30;
        open = false;
        interval = 5;
        destinationFloor = currentFloor;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Floor getCurrentFloor() {
        return currentFloor;
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

    public void openDoor() {
        open = true;
        building.paintOver();
    }

    public void closeDoor() {
        open = false;
        building.paintOver();
    }

    public void draw(Graphics g) {
        if (open) {
            sprOpen.paintIcon(building, g, x, y);
        } else {
            sprClose.paintIcon(building, g, x, y);
        }
    }
}
