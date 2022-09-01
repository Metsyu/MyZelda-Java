/*
 *
 * Author: Joseph Maldonado
 * ID#: 010880953
 * Description: View.java
 * Date: March 29, 2022
 *
 */

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

class View extends JPanel
{
	//Initialize Constants
	public static final int screen_width = 700, screen_height = 500;

	//Declare Variables
	public static int scrollPosX, scrollPosY;
	Model model;
	Controller controller;

	//Constructor
	View(Controller c, Model m)
	{
		//setView for c
		c.setView(this);

		//Initialize Variables
		controller = c;
		model = m;
		scrollPosX = 0;
		scrollPosY = 0;
	}

	//paintComponent
	public void paintComponent(Graphics g)
	{
		//Set background color and fill the background
		g.setColor(new Color(40, 40, 40, 255));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		//Iterate through the ArrayList of sprites in model
		for(int i = 0; i < model.sprites.size(); i++)
		{
			//If the current sprite is link and playing == true, draw itself
			if(model.sprites.get(i).isLink() && Controller.playing)
				model.sprites.get(i).drawYourself(g);

			//Else if the current sprite isn't link, draw itself
			else if(!model.sprites.get(i).isLink())
				model.sprites.get(i).drawYourself(g);
		}
	}

	//loadImage: load an image with the passed in filename
	static BufferedImage loadImage(String filename)
	{
		BufferedImage image = null;
		try
		{
			image = ImageIO.read(new File(filename));
		}
		catch (Exception e)
		{
			e.printStackTrace(System.err);
			System.exit(1);
		}
		return image;
	}

	//goRight: Move the view right
	public static void goRight()
	{
		if(scrollPosX < screen_width)
			scrollPosX += screen_width;
	}

	//goLeft: Move the view left
	public static void goLeft()
	{
		if(scrollPosX > 0)
			scrollPosX -= screen_width;
	}

	//goUp: Move the view up
	public static void goUp()
	{
		if(scrollPosY > 0)
			scrollPosY -= screen_height;
	}

	//goDown: Move the view down
	public static void goDown()
	{
		if(scrollPosY < screen_height)
			scrollPosY += screen_height;
	}
}