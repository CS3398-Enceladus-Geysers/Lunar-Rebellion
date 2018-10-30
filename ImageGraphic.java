package game;

import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.HashMap;

import javax.imageio.ImageIO;

/**
 * A graphic which is represented by a still image, as opposed to an animation.
 */
public class ImageGraphic extends Graphic {
	private static final HashMap<String, Image> RESOURCES = new HashMap<String, Image>();
	private static final long serialVersionUID = 7604033494188278910L;
	private final Image facingRight;
//	private final Image facingLeft;

	/**
	 * This constructor loads and scales an image, and also sets its size.
	 * 
	 * @param fileName File name for image to be loaded.
	 * @param width    Width of this graphic, before scaling by
	 *                 {@link Main#SIZE_FACTOR}
	 * @param height   Height of this graphic, before scaling by
	 *                 {@link Main#SIZE_FACTOR}
	 * @throws Exception In case a file is missing.
	 */
	public ImageGraphic(String fileName, double xoffset, double yoffset, double width, double height) throws Exception {
		super(xoffset, yoffset, width, height);
		if (RESOURCES.containsKey(fileName)) {
			facingRight = RESOURCES.get(fileName);
		} else {
			facingRight = ImageIO.read(new BufferedInputStream(new FileInputStream(fileName)))
					.getScaledInstance(getWidth(), getHeight(), Image.SCALE_AREA_AVERAGING);
			RESOURCES.put(fileName, facingRight);
		}
		// TODO Uncomment facingLeft and implement an Image Flip.
		// TODO Also differentiate different facing images in RESOURCES HashMap.
	}

	@Override
	public void act() {
	}

	/**
	 * Overrides paintComponent to paint the still image.
	 * 
	 * @see game.Graphic#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(facingRight, 0, 0, null);
	}
}