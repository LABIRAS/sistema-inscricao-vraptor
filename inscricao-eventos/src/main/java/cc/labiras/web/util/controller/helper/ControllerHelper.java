package cc.labiras.web.util.controller.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Message;
import br.com.caelum.vraptor.validator.Validator;
import br.com.caelum.vraptor.view.HttpResult;
import br.com.caelum.vraptor.view.Results;
import cc.labiras.web.util.CurrentUser;
import cc.labiras.web.util.JsonUtils;
import cc.labiras.web.util.StringUtils;
import cc.labiras.web.util.vraptor.Internationalization;
import cc.labiras.web.util.vraptor.component.Config;

/**
 * Helper class with many useful methods for web application controllers.
 * 
 * @author Rafael Lins - D00162108
 * 
 */
public abstract class ControllerHelper {
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	protected final Result result;
	protected final HttpServletRequest request;
	protected final HttpServletResponse response;
	protected final HttpSession httpSession;
	protected final Session session;
	protected final Internationalization i18n;
	protected final Config config;
	
	public ControllerHelper(final Result result, final HttpServletRequest request, final HttpServletResponse response, final Session session, final Internationalization i18n, final Config config) {
		this.result = result;
		this.request = request;
		this.response = response;
		this.session = session;
		this.i18n = i18n;
		httpSession = request != null ? request.getSession() : null;
		this.config = config;
		
		if (result != null) {
			result.include("CURRENT_USER", CurrentUser.get());
			result.include("CURRENT_USER_IS_ADMIN", CurrentUser.isRole("ADMIN"));
		}
	}
	
	protected void updateClientTimeZone() {
		TimeZone tz = null;
		String clientTz = request.getParameter("tz");
		if (clientTz == null) {
			clientTz = request.getHeader("X-TimeZone");
		}
		
		if (clientTz == null) {
			tz = TimeZone.getDefault();
		}
		else {
			try {
				tz = TimeZone.getTimeZone(clientTz);
			} catch (final Exception e) {
				tz = TimeZone.getDefault();
			}
		}
		
		request.getSession().setAttribute("CLIENT_TZ", tz);
	}
	
	protected TimeZone getClientTimeZone() {
		final TimeZone tz = (TimeZone) request.getSession().getAttribute("CLIENT_TZ");
		if (tz == null) {
			updateClientTimeZone();
			return (TimeZone) request.getSession().getAttribute("CLIENT_TZ");
		}
		else {
			return tz;
		}
	}
	
	/**
	 * Creates a new {@link Query HQL Query} to be executed.
	 * 
	 * @param query The query String
	 * @return The {@link Query} object.
	 */
	protected Query query(final String query) {
		if (log.isDebugEnabled()) { log.debug("Creating Query object for HQL Query: " + query); }
		return session.createQuery(query);
	}
	
	/**
	 * Creates a new {@link SQLQuery SQL Query} to be executed.
	 * 
	 * @param query The query String
	 * @return The {@link SQLQuery} object.
	 */
	protected SQLQuery sqlQuery(final String query) {
		if (log.isDebugEnabled()) { log.debug("Creating SQLQuery object for SQL Query: " + query); }
		if (log.isWarnEnabled()) { log.warn("An SQLQuery object was requested for an SQL Query. THIS MIGHT INTRODUCE A SECURITY BREACH. BE CAREFUL: " + query); }
		return session.createSQLQuery(query);
	}
	
	/**
	 * @return {@code sqlToMap(sqlQuery(query), fieldNames)}
	 */
	protected List<Map<String, Object>> sqlToMap(final String query, final String... fieldNames) {
		return sqlToMap(sqlQuery(query), fieldNames);
	}
	
	/**
	 * Transforms an {@link SQLQuery} result in a {@link HashMap map}, each column tied to a {@code fieldNames} item. Make sure
	 * your query return at least {@code fieldNames.length} columns in the right order (the same as {@code fieldNames}).
	 * 
	 * @param query The {@link SQLQuery}
	 * @param fieldNames The names of the fields (items on the {@link HashMap map})
	 * @return The {@code List<Map<String, Object>>} of the query.
	 */
	protected List<Map<String, Object>> sqlToMap(final Query query, final String... fieldNames) {
		final List<Map<String, Object>> mapList = new LinkedList<Map<String,Object>>();
		Map<String, Object> map;
		int i;
		
		final List<List<Object>> results = query.list();
		for (final List<Object> list : results) {
			i = 0;
			map = new HashMap<String, Object>();
			
			for (final Object object : list) {
				map.put(fieldNames[i++], object);
			}
			
			mapList.add(map);
		}
		
		return mapList;
	}
	
	/**
	 * @return <code>true</code> if this request was created by a XMLHttpRequest object or has an "ajax" parameter set
	 *         to <code>true</code> (in other words: "if it's AJAX or not")
	 */
	protected boolean isAjaxRequest() {
		return isAjaxRequest(request);
	}
	
	/**
	 * @return <code>true</code> if this request was created by a XMLHttpRequest object or has an "ajax" parameter set
	 *         to <code>true</code> (in other words: "if it's AJAX or not")
	 */
	public static boolean isAjaxRequest(final HttpServletRequest request) {
		final boolean ajaxParam = StringUtils.parseBoolean(request.getParameter("ajax"), false);
		final boolean xhrRequest = request.getHeader("X-Requested-With") != null ? request.getHeader("X-Requested-With").equalsIgnoreCase("XMLHttpRequest") : false;
//		if (log.isDebugEnabled()) { log.debug("Checking if this request came from a XMLHttpRequest object or had the request parameter \"ajax\" set to true: " + (ajaxParam || xhrRequest)); }
		return ajaxParam || xhrRequest;
	}
	
	/**
	 * Usado para permitir CORS. Por exemplo: {@code setAllowOrigin("*")} permite que a resposta
	 * seja enviada para qualquer pessoa na Internet inteira.
	 * 
	 * @param allowOrigin Máscara de hosts
	 */
	protected void setAllowOrigin(final String allowOrigin) {
		response.addHeader("Access-Control-Allow-Origin", allowOrigin);
	}
	
	/**
	 * Sends the specified object encoded as JSON as the response of the current request.
	 * 
	 * @param response The object to be encoded and sent
	 */
	protected void jsonResponse(final Object response) {
		this.response.setCharacterEncoding("UTF-8");
		this.response.setContentType("application/json");
		final String jsonString = JsonUtils.toJson(response);
		if (log.isDebugEnabled()) { log.debug("Sending object as UTF-8 JSON (content type \"text/json\") as response to current request: " + jsonString); }
		result.use(Results.http()).body(jsonString);
		// result.use(Results.json()).withoutRoot().from(response).serialize();
	}
	
	/**
	 * Sets up a {@link Validator} to send a JSON response if errors occurs.
	 * 
	 * @param validator The {@link Validator} object
	 * @param response The response to send
	 */
	protected void setJsonValidationResponse(final Validator validator, final Object response) {
		validator.onErrorUse(Results.http()).body(JsonUtils.toJson(response)).setStatusCode(HttpServletResponse.SC_OK);
//		validator.onErrorForwardTo(this).echo(GSON.toJson(response));
	}
	
	/**
	 * Sets up a {@link Validator} to send a JSON response if errors occurs.
	 * 
	 * @param validator The {@link Validator} object
	 * @param response The response to send
	 */
	protected HttpResult setupJsonValidationResult(final Validator validator, final Object response) {
		return validator.onErrorUse(Results.http()).body(JsonUtils.toJson(response));
//		validator.onErrorForwardTo(this).echo(GSON.toJson(response));
	}
	
	/**
	 * Send "{success: true}" as the response
	 */
	protected void jsonResponseSuccess() {
		jsonResponseSuccess(true);
	}
	
	/**
	 * Send "{success: ?}" as the response
	 * 
	 * @param success
	 */
	protected void jsonResponseSuccess(final boolean success) {
		final Map<String, Object> response = new HashMap<String, Object>(1);
		response.put("success", success);
		jsonResponse(response);
	}
	
	/**
	 * Sends the "success = true" and a text message as JSON as the response
	 * @param message Text Message
	 */
	protected void jsonResponseMessage(final String message) {
		jsonResponseMessage(true, message);
	}
	
	/**
	 * Sends the "success" field and a text message as JSON as the response
	 * 
	 * @param success The "success" value
	 * @param message The message
	 */
	protected void jsonResponseMessage(final boolean success, final String message) {
		final Map<String, Object> response = new HashMap<String, Object>(2);
		response.put("success", success);
		response.put("msg", message);
		jsonResponse(response);
	}
	
	/**
	 * Sends the "success" field and an echo (a way to make repeated requests don't trigger repeated actions)
	 * @param success The "success" value
	 * @param echo The echo value
	 */
	protected void jsonResponseEcho(final boolean success, final String echo) {
		final Map<String, Object> response = new HashMap<String, Object>(2);
		response.put("success", success);
		response.put("echo", echo);
		jsonResponse(response);
	}
	
	/**
	 * Sets up a {@link Validator} to send a JSON response if errors occurs.
	 * 
	 * @param validator The {@link Validator} object
	 */
	protected void setupJsonValidationResponse(final Validator validator) {
		Map<String, Object> jsonError;
		final Map<String, Object> response = new HashMap<String, Object>(3);
		final List<Map<String, Object>> errors = new ArrayList<Map<String,Object>>(validator.getErrors().size());
		
		for (final Message error : validator.getErrors()) {
			jsonError = new HashMap<String, Object>(3);
			jsonError.put("category", error.getCategory());
			jsonError.put("message", error.getMessage());
			jsonError.put("severity", error.getSeverity().name());
			errors.add(jsonError);
		}
		
		response.put("success", false);
		response.put("validationErrors", true);
		response.put("errors", errors);
		
		// A ordem dos tratores altera o viaduto?
		setJsonValidationResponse(validator, response);
	}
	
	/**
	 * Sends a cache expires time header with the specified time (in days)
	 * 
	 * @param days How many days should this request be kept in the user's browser cache?
	 */
	protected void setResponseExpiryDate(final int days) {
		if (log.isDebugEnabled()) { log.debug("Calculating and setting response expiration date to " + days + " days..."); }
		setResponseExpiryDateMillis(System.currentTimeMillis() + (days * 86400000L));
	}
	
	/**
	 * Sends a cache expires time header with the specified time (in milliseconds)
	 * 
	 * @param milliseconds "Expires" header value
	 */
	protected void setResponseExpiryDateMillis(final long milliseconds) {
		if (log.isDebugEnabled()) { log.debug("Setting \"Expires\" header to " + milliseconds + " milliseconds"); }
		response.addDateHeader("Expires", milliseconds);
	}
	
	/**
	 * Prevents this request from being cached.
	 */
	protected void disableCache() {
		if (log.isDebugEnabled()) { log.debug("Setting \"Cache-Control\" header to \"no-store\" (disabling cache)"); }
		response.setHeader("Cache-Control", "no-store");
	}
	
	/**
	 * Search and returns a {@link Cookie} by its name (case-INsensitive).
	 * 
	 * @param name Cookie name
	 * @return A {@link Cookie} object or <code>null</code>
	 */
	protected Cookie getCookie(final String name) {
		if (log.isDebugEnabled()) { log.debug("Trying to find cookie \"" + name + "\"."); }
		
		try {
			final Cookie[] cookies = request.getCookies();
			
			if (cookies != null && cookies.length > 0) {
				for (final Cookie cookie : cookies) {
					if (cookie.getName().equalsIgnoreCase(name)) {
						if (log.isDebugEnabled()) { log.debug("Cookie \"" + name + "\": " + cookie); }
						return cookie;
					}
				}
			}
		} catch (final Exception e) {
			// Unable to read the cookies.
			if (log.isDebugEnabled()) { log.debug("Error while reading cookies.", e); }
		}
		
		if (log.isDebugEnabled()) { log.debug("Cookie \"" + name + "\" not found."); }
		
		return null;
	}
	
	/**
	 * Returns the content of a {@link Cookie}.
	 * 
	 * @param name Cookie name
	 * @return Cookie's content or <code>null</code>
	 * @see #getCookie(String)
	 */
	protected String getCookieValue(final String name) {
		if (log.isDebugEnabled()) { log.debug("Getting cookie \"" + name + "\" value..."); }
		final Cookie cookie = getCookie(name);
		if (log.isDebugEnabled()) {
			if (cookie == null) {
				log.debug("Cookie \"" + name + "\" not found. Returning null.");
			}
			else {
				log.debug("Cookie \"" + name + "\" value: " + cookie.getValue());
			}
		}
		return cookie != null ? cookie.getValue() : null;
	}
	
	/**
	 * Returns the content of a {@link Cookie}.
	 * 
	 * @param name Cookie name
	 * @return Cookie's content or <code>null</code>
	 * @see #getCookie(String)
	 */
	protected boolean isCookiePresent(final String name) {
		if (log.isDebugEnabled()) { log.debug("Checking if cookie \"" + name + "\" exists..."); }
		
		try {
			final Cookie[] cookies = request.getCookies();
			
			if (cookies != null && cookies.length > 0) {
				for (final Cookie cookie : cookies) {
					if (cookie.getName().equalsIgnoreCase(name)) {
						if (log.isDebugEnabled()) { log.debug("Cookie \"" + name + "\" found. Is its maxAge = 0 and it has a value? " + (cookie.getMaxAge() != 0 && !StringUtils.isEmpty(cookie.getValue()))); }
						return cookie.getMaxAge() != 0 && !StringUtils.isEmpty(cookie.getValue());
					}
				}
			}
		} catch (final Exception e) {
			// Unable to read the cookies.
			if (log.isDebugEnabled()) { log.debug("Error while reading cookies.", e); }
		}
		
		return false;
	}
	
	/**
	 * This effectively removes a cookie from the user's browser.
	 * 
	 * @param name {@link Cookie} name
	 * @return The {@link Cookie} removed
	 */
	protected Cookie removeCookie(final String name) {
		return removeCookie(getCookie(name));
	}
	
	/**
	 * This effectively removes a cookie from the user's browser.
	 * 
	 * @param cookie The {@link Cookie}
	 * @return The {@link Cookie} removed
	 */
	protected Cookie removeCookie(final Cookie cookie) {
		if (log.isDebugEnabled()) { log.debug("Removing cookie: " + cookie); }
		
		if (cookie != null) {
			cookie.setValue("");
			cookie.setPath("/");
			cookie.setMaxAge(0);
			
			response.addCookie(cookie);
			
			if (log.isInfoEnabled()) { log.info("Cookie \"removed\" (value = \"\", path = \"/\", maxAge = 0): " + cookie); }
		}
		else if (log.isDebugEnabled()) { log.debug("Cookie was null. Nothing was done."); }
		
		return cookie;
	}
	
	/**
	 * Simple detection of a Mobile User-Agent. Not 100%.
	 * @return <code>true</code> if the current User-Agent is from a Mobile Phone.
	 */
	protected boolean isMobileUserAgent() {
		final String ua = request.getHeader("User-Agent").toLowerCase();
		final boolean result = StringUtils.parseBoolean(request.getParameter("mobi"), false) || (!StringUtils.isEmpty(ua) && ua.matches("(?i).*((android|bb\\d+|meego).+mobile|avantgo|bada\\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\\.(browser|link)|vodafone|wap|windows (ce|phone)|xda|xiino).*") || ua.substring(0, 4).matches("(?i)1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\\-(n|u)|c55\\/|capi|ccwa|cdm\\-|cell|chtm|cldc|cmd\\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\\-s|devi|dica|dmob|do(c|p)o|ds(12|\\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\\-|_)|g1 u|g560|gene|gf\\-5|g\\-mo|go(\\.w|od)|gr(ad|un)|haie|hcit|hd\\-(m|p|t)|hei\\-|hi(pt|ta)|hp( i|ip)|hs\\-c|ht(c(\\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\\-(20|go|ma)|i230|iac( |\\-|\\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\\/)|klon|kpt |kwc\\-|kyo(c|k)|le(no|xi)|lg( g|\\/(k|l|u)|50|54|\\-[a-w])|libw|lynx|m1\\-w|m3ga|m50\\/|ma(te|ui|xo)|mc(01|21|ca)|m\\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\\-2|po(ck|rt|se)|prox|psio|pt\\-g|qa\\-a|qc(07|12|21|32|60|\\-[2-7]|i\\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\\-|oo|p\\-)|sdk\\/|se(c(\\-|0|1)|47|mc|nd|ri)|sgh\\-|shar|sie(\\-|m)|sk\\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\\-|v\\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\\-|tdg\\-|tel(i|m)|tim\\-|t\\-mo|to(pl|sh)|ts(70|m\\-|m3|m5)|tx\\-9|up(\\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\\-|your|zeto|zte\\-"));
		
		if (log.isDebugEnabled()) { log.debug("Checking User-Agent header if it's a mobile browser: " + ua + " --- check result: " + result); }
		if (log.isInfoEnabled() && result) { log.info("Mobile User-Agent detected!"); }
		
		return result;
//		return true;
	}
	
	protected void sendErrorJsonResponse(final String error) {
		final List<String> errorMessagesStrings = new ArrayList<>(1);
		final Map<String, Object> errors = new HashMap<>(1);
		errorMessagesStrings.add(i18n.get(error));
		errors.put("errors", errorMessagesStrings);
		jsonResponse(errors);
	}
}
