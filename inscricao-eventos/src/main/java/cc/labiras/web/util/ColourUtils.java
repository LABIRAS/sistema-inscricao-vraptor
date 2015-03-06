package cc.labiras.web.util;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Some utility methods for generating colours
 * @author g0dkar
 *
 */
public class ColourUtils {
	public static List<Color> generatePallete(final int colours) {
		final List<Color> pallete = new ArrayList<Color>(colours);
		for (int i = 0; i < colours; i++) {
			pallete.add(generateColour());
		}
		return pallete;
	}
	
	/**
	 * Generates a completely random, pastel colour.
	 * @return
	 */
	public static Color generateColour() {
	    final Random random = new Random();
	    int red = random.nextInt(256);
	    int green = random.nextInt(256);
	    int blue = random.nextInt(256);

	    // mix the color
        red = (red + 255) / 2;
        green = (green + 255) / 2;
        blue = (blue + 255) / 2;

	    final Color color = new Color(red, green, blue);
	    return color;
	}
	
	/**
	 * Calculate how similar two colours are. This can be used to test if two colours look alike to a human. More info: http://www.compuphase.com/cmetric.htm
	 * @param c1 First colour
	 * @param c2 Second colour
	 * @return Their distance within the RGB Cube
	 */
	public double colourDistance(final Color c1, final Color c2) {
		final double rmean = (c1.getRed() + c2.getRed()) / 2;
		final int r = c1.getRed() - c2.getRed();
		final int g = c1.getGreen() - c2.getGreen();
		final int b = c1.getBlue() - c2.getBlue();
		final double weightR = 2 + rmean / 256;
		final double weightG = 4.0;
		final double weightB = 2 + (255 - rmean) / 256;
		return Math.sqrt(weightR * r * r + weightG * g * g + weightB * b * b);
	}
}
