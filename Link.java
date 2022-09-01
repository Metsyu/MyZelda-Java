/*
 *
 * Author: Joseph Maldonado
 * ID#: 010880953
 * Description: Link.java
 * Date: March 29, 2022
 *
 */

import java.awt.image.BufferedImage;
import java.awt.Graphics;

enum Direction {LEFT, UP, RIGHT, DOWN}

class Link extends Sprite
{
    //Initialize Constants
    final int speed = 6;
    final int walking_max_images = 10;

    //Declare Variables
    Direction direction;
    BufferedImage standing_backward;
    BufferedImage standing_forward;
    BufferedImage standing_left;
    BufferedImage standing_right;
    BufferedImage[] walking_backward = new BufferedImage[walking_max_images];
    BufferedImage[] walking_forward = new BufferedImage[walking_max_images];
    BufferedImage[] walking_left = new BufferedImage[walking_max_images];
    BufferedImage[] walking_right = new BufferedImage[walking_max_images];
    int prev_x, prev_y;
    boolean walking;

    Link(int x, int y)
    {
        //Initialize Variables
        this.x = x;
        this.y = y;
        prev_x = this.x;
        prev_y = this.y;
        w = 45;
        h = 58;
        walking = false;
        direction = Direction.DOWN;

        //Lazy load link_standing_backward images
        if(standing_backward == null)
            standing_backward = View.loadImage("assets/link_images/link_standing/link_standing_backward.png");

        //Lazy load link_standing forwards images
        if(standing_forward == null)
            standing_forward = View.loadImage("assets/link_images/link_standing/link_standing_forward.png");

        //Lazy load link_standing_left images
        if(standing_left == null)
            standing_left = View.loadImage("assets/link_images/link_standing/link_standing_left.png");

        //Lazy load link_standing_right images
        if(standing_right == null)
            standing_right = View.loadImage("assets/link_images/link_standing/link_standing_right.png");

        //Lazy load link_walking_backward images
        for(int i = 0; i < walking_max_images; i++)
        {
            if(walking_backward[i] == null)
            {
                walking_backward[i] = View.loadImage("assets/link_images/link_walking/link_walking_backward/link_walking_backward" + i + ".png");
            }
        }

        //Lazy load link_walking_forward images
        for(int i = 0; i < walking_max_images; i++)
        {
            if(walking_forward[i] == null)
            {
                walking_forward[i] = View.loadImage("assets/link_images/link_walking/link_walking_forward/link_walking_forward" + i + ".png");
            }
        }

        //Lazy load link_walking_left images
        for(int i = 0; i < walking_max_images; i++)
        {
            if(walking_left[i] == null)
            {
                walking_left[i] = View.loadImage("assets/link_images/link_walking/link_walking_left/link_walking_left" + i + ".png");
            }
        }

        //Lazy load link_walking_right images
        for(int i = 0; i < walking_max_images; i++)
        {
            if (walking_right[i] == null)
                walking_right[i] = View.loadImage("assets/link_images/link_walking/link_walking_right/link_walking_right" + i + ".png");
        }
    }

    //marshal
    Json marshal()
    {
        return null;
    }

    //update
    public boolean update()
    {
        return true;
    }

    //collided
    public void collided(Sprite s, Direction direction)
    {
    }

    //getOutOfSprite: Depending on the side of the sprite that link collided with, update x or y with prev_x or prev_y
    public void getOutOfSprite(Sprite s)
    {
        //Walking right
        if(x + w >= s.x && prev_x + w <= s.x)
            x = prev_x;
        //Walking left
        else if(x <= s.x + s.w && prev_x >= s.x + s.w)
            x = prev_x;
        //Walking down
        else if(y + h >= s.y && prev_y + h <= s.y)
            y = prev_y;
        //Walking up
        else if(y <= s.y + s.h && prev_y >= s.y + s.h)
            y = prev_y;
    }

    //savePrevLocation: Set prev_x and prev_y equal to x and y respectively
    public void savePrevLocation()
    {
        prev_x = x;
        prev_y = y;
    }

    //getDirection: Return direction
    public Direction getDirection()
    {
        return direction;
    }

    //drawYourself: Draw the loaded appropriate image depending on if link is walking/not walking and the direction
    //link is facing
    public void drawYourself(Graphics g)
    {
        if(walking)
        {
            switch(direction)
            {
                //Walking Right
                case RIGHT:
                    g.drawImage(walking_right[Controller.walking_image_cycle], x - View.scrollPosX, y - View.scrollPosY, null);
                    break;

                //Walking Left
                case LEFT:
                    g.drawImage(walking_left[Controller.walking_image_cycle], x - View.scrollPosX, y - View.scrollPosY, null);
                    break;

                //Walking Forward
                case DOWN:
                    g.drawImage(walking_forward[Controller.walking_image_cycle], x - View.scrollPosX, y - View.scrollPosY, null);
                    break;

                //Walking Backward
                case UP:
                    g.drawImage(walking_backward[Controller.walking_image_cycle], x - View.scrollPosX, y - View.scrollPosY, null);
                    break;
            }
        }
        else
        {
            switch(direction)
            {
                //Standing Right
                case RIGHT:
                    g.drawImage(standing_right, x - View.scrollPosX, y - View.scrollPosY, null);
                    break;

                //Standing Left
                case LEFT:
                    g.drawImage(standing_left, x - View.scrollPosX, y - View.scrollPosY, null);
                    break;

                //Standing Forward
                case DOWN:
                    g.drawImage(standing_forward, x - View.scrollPosX, y - View.scrollPosY, null);
                    break;

                //Standing Backward
                case UP:
                    g.drawImage(standing_backward, x - View.scrollPosX, y - View.scrollPosY, null);
                    break;
            }
        }
    }

    //Prints out the link's x, y, w, and h values
    @Override
    public String toString()
    {
        return "Link (x, y) = (" + x + ", " + y + "), w = " + w + ", h = " + h;
    }

    @Override
    public boolean isLink()
    {
        return true;
    }
}
