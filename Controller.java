/*
 *
 * Author: Joseph Maldonado
 * ID#: 010880953
 * Description: Controller.java
 * Date: March 29, 2022
 *
 */

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class Controller implements ActionListener, MouseListener, KeyListener
{
	//Declare Variables
	public static boolean playing, placePot;
	public static int walking_image_cycle;

	View view;
	Model model;
	boolean keyLeft, keyRight, keyUp, keyDown;

	//Constructor
	Controller(Model m)
	{
		//Initialize Variables
		keyLeft = false;
		keyRight = false;
		keyUp = false;
		keyDown = false;
		playing = true;
		walking_image_cycle = 0;
		model = m;
	}

	//setView
	void setView(View v)
	{
		view = v;
	}

	//update
	void update()
	{
		//Save links previous location
		model.link.savePrevLocation();

		//If keyRight == true
		if(keyRight)
		{
			//Update link's direction to RIGHT
			model.link.direction = Direction.RIGHT;

			//Increment link's x position
			model.link.x += model.link.speed;
		}

		//If keyLeft == true
		if(keyLeft)
		{
			//Update link's direction to LEFT
			model.link.direction = Direction.LEFT;

			//Decrement link's  x position
			model.link.x -= model.link.speed;
		}

		//If keyDown == true
		if(keyDown)
		{
			//Update link's direction to DOWN
			model.link.direction = Direction.DOWN;

			//Increment link's y position
			model.link.y += model.link.speed;
		}

		//If keyUp == true
		if(keyUp)
		{
			//Update link's direction to UP
			model.link.direction = Direction.UP;

			//Decrement link's y position
			model.link.y -= model.link.speed;
		}

		//If keyDown, keyRight, keyLeft, or keyUp == true
		if(keyDown || keyRight || keyLeft || keyUp)
		{
			//Increment walking_image_cycle
			walking_image_cycle++;

			//If walking_image_cycle == walking_max_images, reset walking_image_cycle to 0
			if(walking_image_cycle == model.link.walking_max_images)
				walking_image_cycle = 0;
		}
	}

	public void actionPerformed(ActionEvent e)
	{
	}

	public void mousePressed(MouseEvent e)
	{
	}

	public void mouseReleased(MouseEvent e)
	{
	}

	public void mouseEntered(MouseEvent e)
	{
	}

	public void mouseExited(MouseEvent e)
	{
	}

	//When a mouseclick is detected
	public void mouseClicked(MouseEvent e)
	{
		//If playing == false
		if(!playing)
		{
			//Store the position where the user clicked
			model.setSpriteDestination(e.getX() + View.scrollPosX, e.getY() + View.scrollPosY);

			//Add or remove a brick or pot, if able
			model.addRemoveSprites();
		}
	}

	public void keyTyped(KeyEvent e)
	{
	}

	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			//If 'D' key is pressed: View Right
			case KeyEvent.VK_D: if(!playing) View.goRight(); break;

			//If 'A' key is pressed: View Left
			case KeyEvent.VK_A: if(!playing) View.goLeft(); break;

			//If 'W' key is pressed: View Up
			case KeyEvent.VK_W: if(!playing) View.goUp(); break;

			//If 'X' key is pressed: View Down
			case KeyEvent.VK_X: if(!playing) View.goDown(); break;

			//If 'S' key is pressed: Save
			case KeyEvent.VK_S: if(!playing) model.modelMarshal().save("assets/map.json"); break;

			//If 'L' key is pressed: Load
			case KeyEvent.VK_L: if(!playing) model.modelUnmarshal(Json.load("assets/map.json")); break;

			//If 'E' key is pressed: Toggle editing mode
			case KeyEvent.VK_E:
				if(playing)
				{
					playing = false;
					System.out.println("Editing mode is now active.");
				}
				else
				{
					playing = true;
					System.out.println("Editing mode is no longer active.");
				}
				break;

			//If 'P' key is pressed: Toggle placing a pot
			case KeyEvent.VK_P:
				if(!playing)
				{
					if(placePot)
					{
						placePot = false;
						System.out.println("You may now add and remove bricks.");
					}
					else
					{
						placePot = true;
						System.out.println("You may now add and remove pots.");
					}
				}
				break;

			//If 'Right Arrow' key is pressed: Move link right
			case KeyEvent.VK_RIGHT:
				if(playing)
				{
					keyRight = true;
					model.link.walking = true;
				}
				break;

			//If 'Left Arrow' key is pressed: Move link left
			case KeyEvent.VK_LEFT:
				if(playing)
				{
					keyLeft = true;
					model.link.walking = true;
				}
				break;

			//If 'Up Arrow' key is pressed: Move link up
			case KeyEvent.VK_UP:
				if(playing)
				{
					keyUp = true;
					model.link.walking = true;
				}
				break;

			//If 'Down Arrow' key is pressed: Move link down
			case KeyEvent.VK_DOWN:
				if(playing)
				{
					keyDown = true;
					model.link.walking = true;
				}
				break;

			//If 'ESC' or 'Q' key is pressed: Exit the game
			case KeyEvent.VK_ESCAPE:
			case KeyEvent.VK_Q: System.exit(0); break;
		}
	}

	public void keyReleased(KeyEvent e)
	{
		//Move link
		switch(e.getKeyCode())
		{
			//If 'Right Arrow' key is released: Stop moving link right
			case KeyEvent.VK_RIGHT:
				if(playing)
				{
					keyRight = false;
					model.link.walking = false;
				}
				break;

			//If 'Left Arrow' key is released: Stop moving link left
			case KeyEvent.VK_LEFT:
				if(playing)
				{
					keyLeft = false;
					model.link.walking = false;
				}
				break;

			//If 'Up Arrow' key is released: Stop moving link up
			case KeyEvent.VK_UP:
				if(playing)
				{
					keyUp = false;
					model.link.walking = false;
				}
				break;

			//If 'Down Arrow' key is released: Stop moving link down
			case KeyEvent.VK_DOWN:
				if(playing)
				{
					keyDown = false;
					model.link.walking = false;
				}
				break;

			//If 'CTRL' key is pressed: Throw a dagger
			case KeyEvent.VK_CONTROL: model.addRemoveSprites(); break;
		}
	}
}