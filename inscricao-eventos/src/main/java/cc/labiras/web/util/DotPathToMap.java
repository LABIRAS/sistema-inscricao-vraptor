package cc.labiras.web.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Converts a {@code path.to.something.separated.by.dots} to a sequence of {@link Map Maps}. Useful for JSON + i18n stuff.
 * 
 * @author Rafael g0dkar
 *
 */
public class DotPathToMap {
	private static final Logger log = LoggerFactory.getLogger(DotPathToMap.class);
	
	/**
	 * Takes a {@code path.to.something.separated.by.dots} and turns it into a {@link HashMap}.
	 * 
	 * @param path The path
	 * @param value The value
	 * @return The {@link HashMap}
	 */
	public static Map<String, Object> pathToMap(final String path, final Object value) {
		return pathToMap(new HashMap<String, Object>(), path, value);
	}
	
	/**
	 * Takes a {@code path.to.something.separated.by.dots} and set it into a {@link HashMap}. <strong>IT OVERWRITES WHATEVER VALUE IS ALREADY ON THE MAP</strong>.
	 * 
	 * @param path The path
	 * @param value The value
	 * @return The {@link HashMap}
	 */
	public static Map<String, Object> pathToMap(Object currentMap, final String path, final Object value) {
		if (currentMap == null || !(currentMap instanceof Map)) {
			currentMap = new HashMap<String, Object>();
		}
		
		final Map<String, Object> map = (Map<String, Object>) currentMap;
		final int indexOfDot = path.indexOf(".");
		final String currentPart = indexOfDot >= 0 ? path.substring(0, indexOfDot) : path;
		
		if (log.isDebugEnabled()) { log.debug("currentMap = {}, path = {}, indexOfDot = {}, currentPart = {}", map, path, indexOfDot, currentPart); }
		
		if (path.indexOf(".", indexOfDot) >= 0) {
			if (log.isDebugEnabled()) { log.debug("We still have levels to go. Invoking pathToMap(\"{}\", {})...", path.substring(currentPart.length() + 1), value); }
			map.put(currentPart, pathToMap(map.get(currentPart), path.substring(currentPart.length() + 1), value));
		}
		else {
			if (log.isDebugEnabled()) { log.debug("Reached the path's end. Setting {} = {}", currentPart, value); }
			map.put(currentPart, value);
		}
		
		return map;
	}
}
