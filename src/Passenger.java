import java.awt.Graphics;
import java.util.Random;
import javax.swing.ImageIcon;

public class Passenger extends Thread {
    Building building;
    int x, y, queuePosition;
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
}
