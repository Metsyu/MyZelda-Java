/*
 *
 * Author: Joseph Maldonado
 * ID#: 010880953
 * Description: Sprite.java
 * Date: March 29, 2022
 *
 */

import java.awt.Graphics;
import java.util.ArrayList;

abstract class Sprite
{
    //Declare abstract methods for classes that extend Sprite
    abstract public void drawYourself(Graphics g);
    abstract public boolean update();
    abstract Json marshal();
    abstract public void collided(Sprite s, Direction direction);
    abstract public Direction getDirection();

    //Declare Variables
    int x, y, w, h;

    //Constructor
    public Sprite()
    {
        //Initialize Variables
        x = 0;
        y = 0;
        w = 0;
        h = 0;
    }

    //detectSprites: Returns the index of a sprite if one is located at the passed in x and y coordinates
    public int detectSprites(int x, int y, ArrayList<Sprite> sprites)
    {
        //Iterate through the ArrayList of sprites that is passed in
        for(int i = 0; i < sprites.size(); i++)
        {
            //If the current sprite's x and y coordinates match the x and y coordinates that were passed in,
            //return the array index of that sprite.
            if(sprites.get(i).x == x && sprites.get(i).y == y)
                return i;
        }

        //Return -1 if no sprite is found that matches
        return -1;
    }

    //Returns true if the sprite that is calling it is Link
    public boolean isLink()
    {
        return false;
    }

    //Returns true if the sprite that is calling it is a Brick
    public boolean isBrick()
    {
        return false;
    }

    //Returns true if the sprite that is calling it is a Pot
    public boolean isPot()
    {
        return false;
    }

    //Returns true if the sprite that is calling it is a Dagger
    public boolean isDagger()
    {
        return false;
    }

    //Prints out the sprite's x, y, w, and h values
    @Override
    public String toString()
    {
        return "Sprite (x, y) = (" + x + ", " + y + "), w = " + w + ", h = " + h;
    }
}