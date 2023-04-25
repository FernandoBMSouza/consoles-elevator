import java.awt.Graphics;
import java.util.Random;
import java.util.concurrent.Semaphore;

import javax.swing.ImageIcon;

public class Floor {
    private Building building;
    private ImageIcon sprite;
    private Passenger[] passengers;
    private int y, number;
    private Random random;

    public Floor(Building building, int y, int number, Semaphore semaphore) {
        this.building = building;
        sprite = new ImageIcon(getClass().getResource(".\\content\\floor.png"));
        this.y = y * sprite.getIconHeight();
        this.number = number;

        random = new Random();
        passengers = new Passenger[random.nextInt(6)];
        for (int i = 0; i < passengers.length; i++) {
            passengers[i] = new Passenger(building, this, i, semaphore);
        }
    }

    // #region Properties
    public int getHeight() {
        return sprite.getIconHeight();
    }

    public int getWidth() {
        return sprite.getIconWidth();
    }

    public int getNumber() {
        return number;
    }

    public int getY() {
        return y;
    }

    public Passenger[] getPassengers() {
        return passengers;
    }
    // #endregion

    public void draw(Graphics g) {
        sprite.paintIcon(building, g, 0, y);
    }
}
