import java.awt.Graphics;
import java.util.Random;
import javax.swing.ImageIcon;

public class Passenger extends Thread {
    Building building;
    int x, y, queuePosition, interval;
    ImageIcon sprite;
    Floor currentFloor, destinationFloor;
    Random random;

    public Passenger(Building building, Floor initialFloor, int queuePosition) {
        this.building = building;
        this.queuePosition = queuePosition;
        currentFloor = initialFloor;
        sprite = new ImageIcon(getClass().getResource(".\\content\\passenger.png"));
        x = 40 * queuePosition + 150;
        y = initialFloor.getY() + 20;
        destinationFloor = findDestinationFloor();
        interval = 5;
    }

    @Override
    public void run() {
        super.run();
        enterElevator();
    }

    public void draw(Graphics g) {
        sprite.paintIcon(building, g, x, y);
    }

    private Floor findDestinationFloor() {
        Floor floor = building.getFloors()[currentFloor.getNumber()];
        while (floor == currentFloor) {
            floor = building.getFloors()[random.nextInt(building.getFloors().length)];
        }
        return floor;
    }

    private void move(int destinationX) {
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

    public void enterElevator() {
        if (currentFloor == building.getElevator().getFloor() && queuePosition == 0) {
            move(building.getElevator().getX() + 30);
        }
    }
}
