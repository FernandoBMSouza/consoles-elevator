import java.awt.Graphics;
import java.util.Random;
import java.util.concurrent.Semaphore;

import javax.swing.ImageIcon;

public class Passenger extends Thread {
    private Building building;
    private int x, y, queuePosition, interval, elevatorWaitTime;
    private ImageIcon sprite;
    private Floor currentFloor;
    private Random random;
    private Semaphore semaphore;

    public Passenger(Building building, Floor initialFloor, int queuePosition, Semaphore semaphore) {
        this.building = building;
        this.queuePosition = queuePosition;
        this.semaphore = semaphore;
        random = new Random();
        currentFloor = initialFloor;
        sprite = new ImageIcon(getClass().getResource(".\\content\\passenger.png"));
        x = 40 * queuePosition + 150;
        y = initialFloor.getY() + 20;
        interval = 5;
        // Encontrar um forma melhor de fazer esse tempo depois, isso pode causar bug
        elevatorWaitTime = 2500;
    }

    @Override
    public void run() {
        super.run();
        while (true) {

            try {
                semaphore.acquire();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

            // Tentar melhorar esses dois ifs depois
            if (queuePosition == 0 && building.getElevator().getCurrentFloor().getNumberOfPassengers() <= 0) {
                callElevator();
                enterElevator();
                updateCurrentFloorQueue();
                travelElevator(findDestinationFloor());
                exitElevator();
            }
            if (queuePosition == 0 && building.getElevator().getCurrentFloor() == currentFloor) {
                enterElevator();
                updateCurrentFloorQueue();
                travelElevator(findDestinationFloor());
                exitElevator();
            }
            semaphore.release();

            if (queuePosition < 0) {
                moveHorizontal(building.getFloors()[currentFloor.getNumber()].getWidth());
            }

            try {
                Thread.sleep(elevatorWaitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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

    public void travelElevator(Floor destinationFloor) {
        building.getElevator().closeDoor();
        building.getElevator().setDestination(destinationFloor);
        while (currentFloor != destinationFloor) {
            y = building.getElevator().getY() + 20;
            building.paintOver();
            if (building.getElevator().getCurrentFloor() == destinationFloor) {
                currentFloor = destinationFloor;
            }
        }
    }

    public void enterElevator() {
        building.getElevator().openDoor();
        currentFloor.decreaseNumberOfPassengers();
        moveHorizontal(building.getElevator().getX() + 60);
        queuePosition = -1;
    }

    public void exitElevator() {
        building.getElevator().openDoor();
        moveHorizontal(150);
        building.getElevator().closeDoor();
        // moveHorizontal(building.getFloors()[currentFloor.getNumber()].getWidth());
    }

    public void callElevator() {
        building.getElevator().setDestination(currentFloor);
        try {
            Thread.sleep(elevatorWaitTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void updateCurrentFloorQueue() {
        for (Passenger p : currentFloor.getPassengers()) {
            if (p.queuePosition >= 0) {
                p.queuePosition--;
                p.moveHorizontal(40 * p.queuePosition + 150);
            }
        }
    }
}
