package cc.labiras.web.util;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public class Props {
	private final Properties properties;
	
	public Props(Properties properties) {
		this.properties = properties;
	}
	
	public int getInt(String key) {
		return getInt(key, 0);
	}
	
	public int getInt(String key, int defaultValue) {
		return StringUtils.parseInteger(getProperty(key), defaultValue);
	}
	
	public long getLong(String key) {
		return getLong(key, 0);
	}
	
	public long getLong(String key, long defaultValue) {
		return StringUtils.parseLong(getProperty(key), defaultValue);
	}
	
	public double getDouble(String key) {
		return getDouble(key, 0);
	}
	
	public double getDouble(String key, double defaultValue) {
		if (key == null || getProperty(key) == null) {
			return defaultValue;
		}
		
		try {
			return Double.parseDouble(getProperty(key));
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}
	
	public boolean getBoolean(String key) {
		return getBoolean(key, false);
	}
	
	public boolean getBoolean(String key, boolean defaultValue) {
		return StringUtils.parseBoolean(getProperty(key), defaultValue);
	}
	
	/* ********************************************************** */
	/* ********************************************************** */
	/* ********************************************************** */
	/* ********************************************************** */
	/* ********************************************************** */

	public Object setProperty(String key, String value) {
		return properties.setProperty(key, value);
	}

	public boolean isEmpty() {
		return properties.isEmpty();
	}

	public Enumeration<Object> keys() {
		return properties.keys();
	}

	public boolean contains(Object value) {
		return properties.contains(value);
	}

	public boolean containsValue(Object value) {
		return properties.containsValue(value);
	}

	public boolean containsKey(Object key) {
		return properties.containsKey(key);
	}

	public Object get(Object key) {
		return properties.get(key);
	}

	public Object put(Object key, Object value) {
		return properties.put(key, value);
	}

	public Object remove(Object key) {
		return properties.remove(key);
	}

	public void putAll(Map<? extends Object, ? extends Object> t) {
		properties.putAll(t);
	}

	public void clear() {
		properties.clear();
	}

	public String toString() {
		return properties.toString();
	}

	public Set<Object> keySet() {
		return properties.keySet();
	}

	public Set<Entry<Object, Object>> entrySet() {
		return properties.entrySet();
	}

	public Collection<Object> values() {
		return properties.values();
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	public String getProperty(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}
}
