package AutomatasCelulares;

import java.awt.event.MouseEvent;

public class MouseListener implements java.awt.event.MouseListener{

    // Declare the variables that will store the mouse position
    int x;
    int y;

    // Constructor sets the initial position of the mouse to -1 (outside the grid)
    public MouseListener() {
        x = -1;
        y = -1;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    // When the mouse is pressed, the position is stored
    @Override
    public void mousePressed(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    // When the mouse is released, the position is set to -1 (outside the grid)
    @Override
    public void mouseReleased(MouseEvent e) {
        x = -1;
        y = -1;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
