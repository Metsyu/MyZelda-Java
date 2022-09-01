/*
 *
 * Author: Joseph Maldonado
 * ID#: 010880953
 * Description: Dagger.java
 * Date: March 29, 2022
 *
 */

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.lang.Math;
import java.awt.image.AffineTransformOp;

class Dagger extends Sprite
{
    //Initialize Constants
    final int speed = 12;

    //Declare Variables
    static BufferedImage image;
    Direction direction;
    boolean isActive;

    Dagger(int x, int y, Direction direction)
    {
        //Initialize Variables
        this.x = x;
        this.y = y;
        this.direction = direction;
        w = 27;
        h = 27;
        isActive = true;

        //Lazy load the image
        if(image == null)
            image = View.loadImage("assets/dagger.png");
    }

    //marshal
    Json marshal()
    {
        return null;
    }

    //update
    public boolean update()
    {
        switch(direction)
        {
            //If direction == RIGHT, increment the dagger's x position
            case RIGHT: x += speed; break;

            //If direction == LEFT, decrement the dagger's x position
            case LEFT: x -= speed;break;

            //If direction == DOWN, increment the dagger's y position
            case DOWN: y += speed; break;

            //If direction == UP, decrement the dagger's x position
            case UP: y -= speed; break;
        }

        //Return isActive
        return isActive;
    }

    //collided: Set isActive to false
    public void collided(Sprite s, Direction direction)
    {
        isActive = false;
    }

    //getDirection: Return direction
    public Direction getDirection()
    {
        return direction;
    }

    //drawYourself: Draw the loaded image and rotate it, if needed
    public void drawYourself(Graphics g)
    {
        //Declare Variables
        double rotation;
        double transform_x;
        double transform_y;
        AffineTransform t;
        AffineTransformOp op;

        switch(direction)
        {
            //If direction == RIGHT, rotate and transform the image 90 degrees then draw the image
            case RIGHT:
                rotation = Math.toRadians(90);
                transform_x = (double)w/2.0;
                transform_y = (double)h/2.0;

                t = AffineTransform.getRotateInstance(rotation, transform_x, transform_y);
                op = new AffineTransformOp(t, AffineTransformOp.TYPE_BILINEAR);

                g.drawImage(op.filter(image, null), x - View.scrollPosX, y - View.scrollPosY, null);
                break;

            //If direction == LEFT, rotate and transform the image 270 degrees then draw the image
            case LEFT:
                rotation = Math.toRadians(270);
                transform_x = (double)w/2.0;
                transform_y = (double)h/2.0;

                t = AffineTransform.getRotateInstance(rotation, transform_x, transform_y);
                op = new AffineTransformOp(t, AffineTransformOp.TYPE_BILINEAR);

                g.drawImage(op.filter(image, null), x - View.scrollPosX, y - View.scrollPosY, null);
                break;

            //If direction == DOWN, rotate and transform the image 180 degrees then draw the image
            case DOWN:
                rotation = Math.toRadians(180);
                transform_x = (double)w/2.0;
                transform_y = (double)h/2.0;

                t = AffineTransform.getRotateInstance(rotation, transform_x, transform_y);
                op = new AffineTransformOp(t, AffineTransformOp.TYPE_BILINEAR);

                g.drawImage(op.filter(image, null), x - View.scrollPosX, y - View.scrollPosY, null);
                break;

            //If direction == UP, draw the image as is
            case UP: g.drawImage(image, x - View.scrollPosX, y - View.scrollPosY, null); break;
        }
    }

    //Prints out the dagger's x, y, w, and h values
    @Override
    public String toString()
    {
        return "Dagger (x, y) = (" + x + ", " + y + "), w = " + w + ", h = " + h;
    }

    @Override
    public boolean isDagger()
{
    return true;
}
}
