/*
 *
 * Author: Joseph Maldonado
 * ID#: 010880953
 * Description: Model.java
 * Date: March 29, 2022
 *
 */

import java.util.ArrayList;

class Model
{
    //Initialize Constants
    final int start_x = 105, start_y = 105, brick_size = 50, pot_size = 35;

    //Declare Variables
    ArrayList<Sprite> sprites;
    Link link;
    int mouse_x, mouse_y;

    //Constructor
    Model()
    {
        //Initialize Variables
        mouse_x = 0;
        mouse_y = 0;

        //Initialize sprites to an empty ArrayList
        sprites = new ArrayList<>();

        //Specify links starting position
        link = new Link(start_x, start_y);

        //Add link to the sprites ArrayList
        sprites.add(link);
    }

    //Unmarshalling constructor
    void modelUnmarshal(Json ob)
    {
        //Initialize sprites to an empty ArrayList
        sprites = new ArrayList<>();

        //Add link to the sprites ArrayList
        sprites.add(link);

        //Store the bricks from the Json object passed in, into a list
        Json tmpListBrick = ob.get("bricks");

        //Add the bricks to the sprites ArrayList
        for(int i = 0; i < tmpListBrick.size(); i++)
            sprites.add(new Brick(tmpListBrick.get(i)));

        //Store the pots from the Json object passed in, into a list
        Json tmpListPot = ob.get("pots");

        //Add the pots to the sprites ArrayList
        for(int i = 0; i < tmpListPot.size(); i++)
            sprites.add(new Pot(tmpListPot.get(i)));
    }

    //Marshal
    Json modelMarshal()
    {
        //Initialize Json object
        Json ob = Json.newObject();

        //Create a Json list for bricks
        Json tmpListBrick = Json.newList();
        ob.add("bricks", tmpListBrick);

        //Create a Json list for link
        Json tmpListLink = Json.newList();
        ob.add("link", tmpListLink);

        //Create a Json list for pots
        Json tmpListPot = Json.newList();
        ob.add("pots", tmpListPot);

        //Add sprites into their appropriate Json lists
        for(int i = 0; i < sprites.size(); i++)
        {
            if(sprites.get(i).isBrick())
                tmpListBrick.add((sprites.get(i).marshal()));
            if(sprites.get(i).isLink())
                tmpListLink.add(sprites.get(i).marshal());
            if(sprites.get(i).isPot())
                tmpListPot.add(sprites.get(i).marshal());
        }
        return ob;
    }

    //update
    public void update()
    {
        //Iterate through the ArrayList of sprites
        for(int i = 0; i < sprites.size(); i++)
        {
            //If the current sprite isn't link or a dagger
            if(!sprites.get(i).isLink() && !sprites.get(i).isDagger())
            {
                //Check if there is a collision between link and the current sprite
                if(isThereACollision(link, sprites.get(i)))
                {
                    //If the current sprite is a brick
                    if(sprites.get(i).isBrick())
                    {
                        //Signal link to get out of the current sprite
                        link.getOutOfSprite(sprites.get(i));
                    }
                    //If the current sprite is a pot
                    else if(sprites.get(i).isPot())
                    {
                        //Signal the pot that it has collided with link
                        sprites.get(i).collided(link, link.direction);
                    }
                }
                //Check if the current sprite is a pot
                if(sprites.get(i).isPot())
                {
                    //Iterate through the ArrayList of sprites
                    for(int j = 0; j < sprites.size(); j++)
                    {
                        //Check if there is a collision between the pot and another sprite
                        if(isThereACollision(sprites.get(i), sprites.get(j)))
                        {
                            //If the sprite that collided with the pot is a brick
                            if(sprites.get(j).isBrick())
                            {
                                //Signal the pot that it has collided with another sprite
                                sprites.get(i).collided(sprites.get(j), sprites.get(i).getDirection());
                            }
                        }
                    }
                }
            }
            //If the current sprite is a dagger
            if(sprites.get(i).isDagger())
            {
                //Iterate through the ArrayList of sprites
                for(int j = 0; j < sprites.size(); j++)
                {
                    //Check if there is a collision between a dagger and any other sprites, excluding daggers and link
                    if(isThereACollision(sprites.get(i), sprites.get(j)) && !sprites.get(j).isDagger() && !sprites.get(j).isLink())
                    {
                        //Signal the dagger that it has collided
                        sprites.get(i).collided(sprites.get(j), sprites.get(j).getDirection());

                        //If the current sprite is a pot
                        if(sprites.get(j).isPot())
                        {
                            //Signal the pot that it has collided with another sprite
                            sprites.get(j).collided(sprites.get(i), sprites.get(i).getDirection());
                        }
                    }
                }
            }

            //Call all sprites update functions, if update returns false, remove it from the sprites ArrayList
            if(!sprites.get(i).update())
                sprites.remove(i);
        }

        //If playing == true
        if(Controller.playing)
        {
            //If link's x position is >= screen_width, call goRight()
            if (link.x >= View.screen_width)
            {
                View.goRight();
            }

            //If link's y position is >= screen_height, call goDown()
            if (link.y >= View.screen_height)
            {
                View.goDown();
            }

            //If link's x position is <= screen_width, call goLeft()
            if (link.x <= View.screen_width)
            {
                View.goLeft();
            }

            //If link's y position is <= screen_width, call goUp()
            if (link.y <= View.screen_height)
            {
                View.goUp();
            }
        }
    }

    //isThereACollision: Checks if 2 sprites are colliding with each other
    boolean isThereACollision(Sprite s, Sprite s1)
    {
        if(s.y + s.h < s1.y)
            return false;
        if(s.y > s1.y + s1.h)
            return false;
        if(s.x + s.w < s1.x)
            return false;
        if(s.x > s1.x + s1.w)
            return false;
        else
            return true;
    }

    //addRemoveSprites: Generates new sprites
    public void addRemoveSprites()
    {
        //If playing == true
        if(Controller.playing)
        {
            //Initialize a new Dagger object at link's current position
            Dagger d = new Dagger(link.x + link.w/4, link.y + link.h/4, link.direction);

            //Add the dagger to the sprites ArrayList
            sprites.add(d);
        }
        else
        {
            //If placePot == true
            if(Controller.placePot)
            {
                //Generate a new Pot object
                Pot p = new Pot(mouse_x, mouse_y);

                //Call detect sprites, storing the returned value in array_index
                int array_index = p.detectSprites(mouse_x, mouse_y, sprites);

                //If array_index == -1, if a sprite was not detected, add the pot to the sprites ArrayList
                if (array_index == -1)
                    sprites.add(p);

                //Else if remove the pot, that was detected, from the ArrayList
                else if(sprites.get(array_index).isPot())
                    sprites.remove(array_index);
            }
            //Else placePot == false
            else
            {
                //Generate a new Brick object
                Brick b = new Brick(mouse_x, mouse_y);

                //Call detect sprites, storing the returned value in array_index
                int array_index = b.detectSprites(mouse_x, mouse_y, sprites);

                //If array_index == -1, if a sprite was not detected, add the brick to the sprites ArrayList
                if (array_index == -1)
                    sprites.add(b);

                //Else if remove the brick, that was detected, from the ArrayList
                else if(sprites.get(array_index).isBrick())
                    sprites.remove(array_index);
            }
        }
    }

    //setSpriteDestination: Sets the destination of pots or bricks, snapping them to a grid
    public void setSpriteDestination(int mouse_x, int mouse_y)
    {
        if(Controller.placePot)
        {
            this.mouse_x = (mouse_x - mouse_x % pot_size);
            this.mouse_y = (mouse_y - mouse_y % pot_size);
        }
        else
        {
            this.mouse_x = (mouse_x - mouse_x % brick_size);
            this.mouse_y = (mouse_y - mouse_y % brick_size);
        }
    }
}