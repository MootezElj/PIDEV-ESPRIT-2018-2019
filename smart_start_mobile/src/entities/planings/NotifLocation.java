/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.planings;

/**
 *
 * @author Mélék-kh
 */
public class NotifLocation {
    private double x, y;

    public NotifLocation (double xLoc, double yLoc) {
        this.x = xLoc;
        this.y = yLoc;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    
}
