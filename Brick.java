/*
 *
 * Author: Joseph Maldonado
 * ID#: 010880953
 * Description: Brick.java
 * Date: March 29, 2022
 *
 */

import java.awt.Graphics;
import java.awt.image.BufferedImage;

class Brick extends Sprite
{
    //Declare Variables
    static BufferedImage image;

    //Constructor
    Brick(int x, int y)
    {
        //Initialize Variables
        this.x = x;
        this.y = y;
        w = 50;
        h = 50;

        //Lazy load the image
        if(image == null)
           image = View.loadImage("assets/brick.jpg");
    }

    //Unmarshalling Constructor
    Brick(Json ob)
    {
        //Initialize x and y with values from the Json object that was passed in
        x = (int)ob.getLong("brick_x");
        y = (int)ob.getLong("brick_y");

        //Initialize w and h
        w = 50;
        h = 50;

        //Lazy load the image
        if(image == null)
            image = View.loadImage("assets/brick.jpg");
    }

    //Marshal the brick's current x and y values into the Json object and return the Json object
    Json marshal()
    {
        Json ob = Json.newObject();
        ob.add("brick_x", x);
        ob.add("brick_y", y);
        return ob;
    }

    //update
    public boolean update()
    {
        return true;
    }

    //Collided
    public void collided(Sprite s, Direction direction)
    {
    }

    //getDirection
    public Direction getDirection()
    {
        return null;
    }

    //drawYourself: Draw the loaded image
    public void drawYourself(Graphics g)
    {
        g.drawImage(image, x - View.scrollPosX, y - View.scrollPosY, null);
    }

    //Prints out the brick's x, y, w, and h values
    @Override
    public String toString()
    {
        return "Brick (x, y) = (" + x + ", " + y + "), w = " + w + ", h = " + h;
    }

    @Override
    public boolean isBrick()
    {
        return true;
    }
}
