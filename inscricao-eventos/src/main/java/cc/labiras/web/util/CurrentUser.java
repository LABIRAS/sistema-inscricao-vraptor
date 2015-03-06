package cc.labiras.web.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cc.labiras.web.model.User;

/**
 * Holds the current user of the request in a way accessible everywhere.
 * 
 * @author Rafael g0dkar
 * 
 * @see ThreadLocal
 * @see ThreadLocal#set(Object)
 * @see ThreadLocal#get()
 */
public class CurrentUser {
	private static final Logger LOG = LoggerFactory.getLogger(CurrentUser.class);
	private static ThreadLocal<User> CURRENT_USER = new ThreadLocal<User>();
	
	/**
	 * Sets the Current User.
	 * @param user The Current User
	 * @return The current user
	 */
	public static User set(final User user) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("CurrentUser set as #{} ({})", user == null ? 0 : user.getId(), user);
		}
		LOG.info("CurrentUser set as #{} ({})", user == null ? 0 : user.getId(), user);
		
		CURRENT_USER.set(user);
		return get();
	}
	
	/**
	 * @return The current user
	 */
	public static User get() {
		return CURRENT_USER.get();
	}
	
	/**
	 * @return {@code true} if there is a Current User set, {@code false} otherwise.
	 */
	public static boolean hasCurrentUser() {
		return CURRENT_USER.get() != null;
	}
	
	/**
	 * Checks if the {@link CurrentUser} is the same as {@code user} (same object or same {@link User#getId() id})
	 * @param user The other user
	 * @return {@code true} if they're the same object or have the same {@link User#getId() id}.
	 */
	public static boolean equals(final User user) {
		return get() == user || (hasCurrentUser() && user != null && getId() == user.getId());
	}
	
	/** Same as {@code get().isRole(role)} */
	public static boolean isRole(final String role) {
		return get() != null ? get().getRole().equals(role) : false;
	}
	
	/** Same as {@code get().isRole("ADMIN")} */
	public static boolean isAdmin() {
		return isRole("ADMIN");
	}

	/** Same as {@code get().getId()} */
	public static Long getId() {
		return get().getId();
	}

	/** Same as {@code get().getUsername()} */
	public static String getUsername() {
		return get().getUsername();
	}

	/** Same as {@code get().getEmail()} */
	public static String getEmail() {
		return get().getEmail();
	}
}
