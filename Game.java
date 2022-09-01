/*
 *
 * Author: Joseph Maldonado
 * ID#: 010880953
 * Description: Game.java
 * Date: March 29, 2022
 *
 */

import javax.swing.JFrame;
import java.awt.Toolkit;

public class Game extends JFrame
{
	Model model;
	Controller controller;
	View view;

	public Game()
	{
		model = new Model();
		controller = new Controller(model);
		view = new View(controller, model);
		this.setTitle("MyZelda");
		this.setSize(716, 539);
		this.setFocusable(true);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		view.addMouseListener(controller);
		this.addKeyListener(controller);
		model.modelUnmarshal(Json.load("assets/map.json"));
	}

	public static void main(String[] args)
	{
		Game g = new Game();
		g.run();
	}

	public void run()
	{
		while(true)
		{
			controller.update();
			model.update();
			view.repaint();
			Toolkit.getDefaultToolkit().sync();

			//Sleep for 40
			try
			{
				Thread.sleep(40);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
}
