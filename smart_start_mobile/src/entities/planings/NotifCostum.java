/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.planings;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Mélék-kh
 */
public class NotifCostum extends Stage  {
    

    private final NotifLocation bottomRight;

    public NotifCostum(AnchorPane ap, StageStyle style) {
        initStyle(style);

        setSize(ap.getPrefWidth(), ap.getPrefHeight());

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double x = screenBounds.getMinX() + screenBounds.getWidth() - ap.getPrefWidth() - 2;
        double y = screenBounds.getMinY() + screenBounds.getHeight() - ap.getPrefHeight() - 2;

        bottomRight = new NotifLocation(x,y);
    }

    public NotifLocation getBottomRight() {
        return bottomRight;
    }

    public void setSize(double width, double height) {
        setWidth(width);
        setHeight(height);
    }

    public NotifLocation getOffScreenBounds() {
        NotifLocation loc = getBottomRight();

        return new NotifLocation(loc.getX() + this.getWidth(), loc.getY());
    }

    public void setLocation(NotifLocation loc) {
        setX(loc.getX());
        setY(loc.getY());
    }

    private SimpleDoubleProperty xLocationProperty = new SimpleDoubleProperty() {
        @Override
        public void set(double newValue) {
            setX(newValue);
        }

        @Override
        public double get() {
            return getX();
        }
    };

    public SimpleDoubleProperty xLocationProperty() {
        return xLocationProperty;
    }

    private SimpleDoubleProperty yLocationProperty = new SimpleDoubleProperty() {
        @Override
        public void set(double newValue) {
            setY(newValue);
        }

        @Override
        public double get() {
            return getY();
        }
    };

    public SimpleDoubleProperty yLocationProperty() {
        return yLocationProperty;
    }

    
    
}
