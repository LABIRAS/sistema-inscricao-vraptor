package cc.labiras.web.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {
	/**
	 * Validates an URL. A valid URL should be well-formed URL, use the HTTP or HTTP protocol on ports 80, 8080 or 443.
	 * 
	 * @param value The URL to be validated.
	 * @param resultIfBlank The result should the string be {@link StringUtils#isBlank(String) blank} ({@code false} means it cannot be {@code null})
	 * @return {@code true} if the URL is valid.
	 * @see URL
	 * @see URL#getProtocol()
	 * @see URL#getPort()
	 */
	public static boolean url(final String value, final boolean resultIfBlank) {
		if (!StringUtils.isBlank(value)) {
			try {
				// Checks if the URL is well-formed
				final URL url = new URL(value);
				
				// Checks if the protocol is HTTP or HTTPS and the port is right.
				return (url.getProtocol().toUpperCase().equals("HTTP") || url.getProtocol().toUpperCase().equals("HTTPS")) && (url.getPort() < 0 || url.getPort() == 80 || url.getPort() == 8080 || url.getPort() == 443);
			} catch (final MalformedURLException e) {
				return false;
			}
		}
		
		return resultIfBlank;
	}
	
	private static final Pattern PATTERN_HASHCOLOR = Pattern.compile("(?i)#?([a-f0-9]{3})|#?([a-f0-9]{6})");
	private static final Pattern PATTERN_RGBA = Pattern.compile("(?i)(rgba?\\()?\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3})\\s*(?:,\\s*(\\d*.\\d+))?\\s*\\)?");
	
	/**
	 * Validates a CSS Color. A valid CSS Color is on the #RGB or #RRGGBB format or rgb[a](r, g, b[, a]).
	 * 
	 * @param value The Color to be validated.
	 * @param resultIfBlank The result should the string be {@link StringUtils#isBlank(String) blank} ({@code false} means it cannot be {@code null})
	 * @return {@code true} if the Color is valid.
	 */
	public static boolean cssColor(final String value, final boolean resultIfBlank) {
		if (!StringUtils.isBlank(value)) {
			final Matcher hashMatcher = PATTERN_HASHCOLOR.matcher(value);
			
			if (hashMatcher.matches()) {
				return true;
			}
			else {
				final Matcher rgbaMatcher = PATTERN_RGBA.matcher(value);
				if (rgbaMatcher.find()) {
					final int red = Integer.parseInt(rgbaMatcher.group(1), 10);
					final int green = Integer.parseInt(rgbaMatcher.group(2), 10);
					final int blue = Integer.parseInt(rgbaMatcher.group(3), 10);
					final double alpha = rgbaMatcher.group(4) == null ? 0.0 : Double.parseDouble("0" + rgbaMatcher.group(4).trim());
					
					return red >= 0 && red <= 255 && green >= 0 && green <= 255 && blue >= 0 && blue <= 255 && alpha >= 0.0 && alpha <= 1.0;
				}
				else {
					return false;
				}
			}
		}
		
		return resultIfBlank;
	}
	
//	public static void main(String[] args) {
//		String hash3 = "#abc";
//		String hash6 = "#aabbcc";
//		String rgb = "rgb(123, 1, 11)";
//		String rgba = "rgba(123, 1, 11)";
//		String rgbaWithAlpha = "rgba(1, 22, 222, 0)";
//
//		System.out.println("[validate]          hash 3 -> " + hash3 + " -> " + cssColor(hash3, false));
//		System.out.println("[validate]          hash 6 -> " + hash6 + " -> " + cssColor(hash6, false));
//		System.out.println("[validate]             RGB -> " + rgb + " -> " + cssColor(rgb, false));
//		System.out.println("[validate]            RGBA -> " + rgba + " -> " + cssColor(rgba, false));
//		System.out.println("[validate] RGBA with Alpha -> " + rgbaWithAlpha + " -> " + cssColor(rgbaWithAlpha, false));
//	}
}
