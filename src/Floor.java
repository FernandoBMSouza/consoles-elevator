import java.awt.Graphics;
import javax.swing.ImageIcon;

public class Floor {
    private Building building;
    private ImageIcon sprite;
    private int y, number;

    public Floor(Building building, int y, int number) {
        this.building = building;
        sprite = new ImageIcon(getClass().getResource(".\\content\\floor.png"));
        this.y = y * sprite.getIconHeight();
        this.number = number;
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
    // #endregion

    public void draw(Graphics g) {
        sprite.paintIcon(building, g, 0, y);
    }
}
