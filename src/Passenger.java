import java.awt.Graphics;
import java.util.Random;
import javax.swing.ImageIcon;

public class Passenger extends Thread {
    private Building building;
    private int x, y, queuePosition, interval;
    private ImageIcon sprite;
    private Floor currentFloor;
    private Random random;

    public Passenger(Building building, Floor initialFloor, int queuePosition) {
        this.building = building;
        this.queuePosition = queuePosition;
        random = new Random();
        currentFloor = initialFloor;
        sprite = new ImageIcon(getClass().getResource(".\\content\\passenger.png"));
        x = 40 * queuePosition + 150;
        y = initialFloor.getY() + 20;
        interval = 5;
    }

    @Override
    public void run() {
        super.run();
        travelElevator();
    }

    public void draw(Graphics g) {
        sprite.paintIcon(building, g, x, y);
    }

    private Floor findDestinationFloor() {
        Floor destinationFloor;
        do {
            destinationFloor = building.getFloors()[random.nextInt(building.getFloors().length)];
        } while (destinationFloor == currentFloor);
        return destinationFloor;
    }

    private void moveHorizontal(int destinationX) {
        while (x != destinationX) {
            if (x > destinationX) {
                x--;
            } else {
                x++;
            }
            building.paintOver();
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void moveVertical(int destinationY) {
        while (y != destinationY) {
            if (y > destinationY) {
                y--;
            } else {
                y++;
            }
            building.paintOver();
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void travelElevator() {
        if (currentFloor == building.getElevator().getFloor() && queuePosition == 0) {
            moveHorizontal(building.getElevator().getX() + 30);
            Floor destinationFloor = findDestinationFloor();
            building.getElevator().setDestination(destinationFloor);
            building.getElevator().isAvailable(false);
            moveVertical(destinationFloor.getY() + 20);
            moveHorizontal(building.getFloors()[destinationFloor.getNumber()].getWidth());
        }
    }
}
