package game;

import java.awt.Graphics;

public class Animation extends Graphic {
	private static final long serialVersionUID = 3016242110618863938L;
	// TODO Allocate Variables for assets.

	public Animation(double width, double height) {
		super(width, height);
		// TODO Load and transform assets.
	}

	public Animation(double xoffset, double yoffset, double width, double height) {
		super(xoffset, yoffset, width, height);
		// TODO Load and transform assets.
	}

	@Override
	public void act() {
		// TODO Next frame;
	}

	@Override
	public void paintComponent(Graphics g) {
		// TODO Paint current frame;
	}
}