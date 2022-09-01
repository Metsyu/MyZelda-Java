/*
 *
 * Author: Joseph Maldonado
 * ID#: 010880953
 * Description: Pot.java
 * Date: March 29, 2022
 *
 */

import java.awt.Graphics;
import java.awt.image.BufferedImage;

class Pot extends Sprite
{
    //Initialize Constants
    final int speed = 12;
    final int max_frames_broken = 4;

    //Declare Variables
    static BufferedImage image;
    static BufferedImage image_broken;
    Direction direction;
    boolean dagger_collision, link_collision, brick_collision;
    int frames_broken;

    //Constructor
    Pot(int x, int y)
    {
        //Initialize Variables
        this.x = x;
        this.y = y;
        w = 35;
        h = 35;
        dagger_collision = false;
        link_collision = false;
        brick_collision = false;
        frames_broken = 0;

        //Lazy load the images
        if(image == null)
            image = View.loadImage("assets/pot.png");
        if(image_broken == null)
            image_broken = View.loadImage("assets/pot_broken.png");
    }

    //Unmarshalling Constructor
    Pot(Json ob)
    {
        //Initialize x and y with values from the Json object that was passed in
        x = (int)ob.getLong("pot_x");
        y = (int)ob.getLong("pot_y");

        //Initialize Variables
        w = 35;
        h = 35;
        dagger_collision = false;
        link_collision = false;
        brick_collision = false;
        frames_broken = 0;

        //Lazy load the images
        if(image == null)
            image = View.loadImage("assets/pot.png");
        if(image_broken == null)
            image_broken = View.loadImage("assets/pot_broken.png");
    }

    //Marshal the pot's current x and y values into the Json object and return the Json object
    Json marshal()
    {
        Json ob = Json.newObject();
        ob.add("pot_x", x);
        ob.add("pot_y", y);
        return ob;
    }

    //update
    public boolean update()
    {
        //If there was a dagger or brick collision detected, increment frames_broken
        if(dagger_collision || brick_collision)
        {
            frames_broken++;
        }

        //Else if there was a link collision detected
        else if(link_collision)
        {
            switch(direction)
            {
                //If direction == RIGHT, increment the pot's x position
                case RIGHT: x += speed; break;

                //If direction == LEFT, decrement the pot's x position
                case LEFT: x -= speed;break;

                //If direction == DOWN, increment the pot's y position
                case DOWN: y += speed; break;

                //If direction == UP, decrement the pot's x position
                case UP: y -= speed; break;
            }
        }

        //Return true if frames_broken != 4
        return frames_broken != max_frames_broken;
    }

    //collided: Checks the type of sprite passed in and updates instance booleans and direction accordingly
    public void collided(Sprite s, Direction direction)
    {
        if(s.isDagger())
            dagger_collision = true;
        else if(s.isLink())
        {
            this.direction = direction;
            link_collision = true;
        }
        else if(s.isBrick())
        {
            brick_collision = true;
        }
    }

    //getDirection: Return direction
    public Direction getDirection()
    {
        return direction;
    }

    //drawYourself: Draw the loaded unbroken or broken pot image
    public void drawYourself(Graphics g)
    {
        if(dagger_collision || brick_collision)
            g.drawImage(image_broken, x - View.scrollPosX, y - View.scrollPosY, null);
        else
            g.drawImage(image, x - View.scrollPosX, y - View.scrollPosY, null);
    }

    //Prints out the pot's x, y, w, and h values
    @Override
    public String toString()
    {
        return "Pot (x, y) = (" + x + ", " + y + "), w = " + w + ", h = " + h;
    }

    @Override
    public boolean isPot()
    {
        return true;
    }
}
