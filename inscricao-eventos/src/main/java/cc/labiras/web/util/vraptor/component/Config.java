package cc.labiras.web.util.vraptor.component;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cc.labiras.web.model.Configuration;
import cc.labiras.web.model.User;
import cc.labiras.web.util.CurrentUser;
import cc.labiras.web.util.StringUtils;

/**
 * VRaptor Utility class for easy access to {@link Configuration system and user configurations}.
 * @author g0dkar
 *
 */
@RequestScoped
public class Config {
	private static final Logger LOG = LoggerFactory.getLogger(Config.class);
	
	/** Needed by CDI @deprecated */
	@Deprecated Config() { this(null, null); }
	
	private final Session session;
	private final HttpServletRequest request;
	private final HttpSession httpSession;
	private boolean forCurrentUser;
	
	@Inject
	public Config(final Session session, final HttpServletRequest request) {
		this.session = session;
		this.request = request;
		httpSession = request != null ? request.getSession(false) : null;
	}
	
	public Config forCurrentUser() {
		forCurrentUser = true;
		return this;
	}
	
	public Config forSystem() {
		forCurrentUser = false;
		return this;
	}
	
	private Query query(final String query) {
		return session.createQuery(query);
	}
	
	/**
	 * Builds a unique key for the specified user
	 * @param key The Key base name
	 * @param user The {@link User}
	 * @return {@code key.uID} where {@code key} is the specified key and {@code ID} is the {@link User#getId() user ID} - or {@code key} if {@code user} is {@code null}.
	 */
	private String userKey(final String key, final User user) {
		return user != null ? key + ".u" + user.getId() : key;
	}
	
	/**
	 * Finds a {@link Configuration} based on its {@link Configuration#getKey() key}. This method tries to {@link Query#setCacheable(boolean) cache} the results.
	 * @param key The Configuration's unique key
	 * @return The Configuration or {@code null} if it doesn't exist
	 */
	public Configuration getConf(final String key) {
		return (Configuration) query("FROM Configuration WHERE key = :key").setString("key", key).setCacheable(true).uniqueResult();
	}
	
	/**
	 * Returns the {@link Configuration} value based on it's key, or {@code null} if it doesn't exist
	 * @param key The Configuration's unique key
	 * @return
	 */
	public String getValue(final String key) {
		final String result = (String) query("SELECT value FROM Configuration WHERE key = :key").setString("key", key).setCacheable(true).uniqueResult();
		
		if (result == null && LOG.isWarnEnabled()) {
			LOG.warn("Configuration key not found: {}", key);
		}
		
		if (LOG.isDebugEnabled()) {
			LOG.debug("Loaded Configuration: {} = {}", key, result);
		}
		
		return result;
	}
	
	/**
	 * Returns the {@link Configuration} value based on it's key and an {@link User}, or the general Configuration if that doesn't exist. For example:
	 * the {@code example.value} configuration for the user ID 16. First the {@code example.value.u16} key will be queried. If it doesn't have a value
	 * the {@code example.value} one will be queried instead. If this method returns {@code null} both keys doesn't exist.
	 * 
	 * @param key The key
	 * @param user The User
	 * @return Values as described earlier.
	 */
	public String getValue(final String key, final User user) {
		final String value = getValue(userKey(key, user));
		return value == null ? getValue(key) : value;
	}
	
	public String get(final String key) {
		if (forCurrentUser && httpSession != null) {
			final User user = CurrentUser.get();
			return getValue(key, user);
		}
		else {
			return getValue(key);
		}
	}
	
	public String get(final String key, final User user) {
		return getValue(key, user);
	}
	
	public String get(final String key, final String defaultValue) {
		final String value = getValue(key);
		return value == null ? defaultValue : value;
	}
	
	public String get(final String key, final User user, final String defaultValue) {
		final String value = getValue(key, user);
		return value == null ? defaultValue : value;
	}
	
	/* ********************************* */
	
	public int getInt(final String key) {
		return StringUtils.parseInteger(get(key), 0);
	}
	
	public int getInt(final String key, final User user) {
		return StringUtils.parseInteger(get(key, user), 0);
	}
	
	public int getInt(final String key, final int defaultValue) {
		return StringUtils.parseInteger(get(key), defaultValue);
	}
	
	public int getInt(final String key, final User user, final int defaultValue) {
		return StringUtils.parseInteger(get(key, user), defaultValue);
	}
	
	/* ********************************* */
	
	public long getLong(final String key) {
		return StringUtils.parseLong(get(key), 0);
	}
	
	public long getLong(final String key, final User user) {
		return StringUtils.parseLong(get(key, user), 0);
	}
	
	public long getLong(final String key, final long defaultValue) {
		return StringUtils.parseLong(get(key), defaultValue);
	}
	
	public long getLong(final String key, final User user, final long defaultValue) {
		return StringUtils.parseLong(get(key, user), defaultValue);
	}
	
	/* ********************************* */
	
	public double getDouble(final String key) {
		return StringUtils.parseDouble(get(key), 0.0);
	}
	
	public double getDouble(final String key, final User user) {
		return StringUtils.parseDouble(get(key, user), 0.0);
	}
	
	public double getDouble(final String key, final double defaultValue) {
		return StringUtils.parseDouble(get(key), defaultValue);
	}
	
	public double getDouble(final String key, final User user, final double defaultValue) {
		return StringUtils.parseDouble(get(key, user), defaultValue);
	}
	
	/* ********************************* */
	
	public boolean getBoolean(final String key) {
		return StringUtils.parseBoolean(get(key), false);
	}
	
	public boolean getBoolean(final String key, final User user) {
		return StringUtils.parseBoolean(get(key, user), false);
	}
	
	public boolean getBoolean(final String key, final boolean defaultValue) {
		return StringUtils.parseBoolean(get(key), defaultValue);
	}
	
	public boolean getBoolean(final String key, final User user, final boolean defaultValue) {
		return StringUtils.parseBoolean(get(key, user), defaultValue);
	}
}
