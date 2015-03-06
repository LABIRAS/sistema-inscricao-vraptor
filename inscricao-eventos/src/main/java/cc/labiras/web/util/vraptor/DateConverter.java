package cc.labiras.web.util.vraptor;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.enterprise.inject.Specializes;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.converter.Converter;

/**
 * VRaptor {@link Converter} for common date formats from the web (including an UTC time)
 * 
 * @author Rafael Lins - D00162108
 *
 */
@Specializes
public class DateConverter extends br.com.caelum.vraptor.converter.DateConverter {
	private static final Logger log = LoggerFactory.getLogger(DateConverter.class);
	
	/**
	 * Regex {@link Pattern} to extract the date/time information of dates/times on the
	 * <code>date/month/year hours:minutes:seconds</code> format.
	 */
	private static final Pattern PATTERN_DATA = Pattern.compile("(?:(?<date>\\d{1,2})/(?<month>\\d{1,2})/(?<year>\\d{4})\\s*)?(?:\\s*(?<hours>\\d{2}):(?<minutes>\\d{2}):(?<seconds>\\d{2}))?");
	
	public static final String SESSION_TZ = "CLIENT_TZ";
	
	@Inject private HttpServletRequest request;
	
	/**
	 * Executes the conversion.
	 * 
	 * @param value The string value from the request
	 * @param type The {@link Class Java Type} of the target java attribute
	 */
	public Date convert(String value, final Class<? extends Date> type) {
		if (value != null) {
			value = value.trim();
			if (log.isDebugEnabled()) { log.debug("Trying to convert \"" + value + "\" to Date."); }
			
			// UTC time (amount of milliseconds since CTIME -> System.currentTimeMillis())
			if (value.matches("\\d+")) {
				if (log.isDebugEnabled()) { log.debug("Value is a UTC time (a single number). Returning new Date(value as long)."); }
				return new Date(Long.parseLong(value));
			}
			else if (value.length() > 0) {
				final Matcher matcherData = PATTERN_DATA.matcher(value);
				
				if (matcherData.matches()) {
					TimeZone tz = (TimeZone) request.getSession().getAttribute(SESSION_TZ);
					
					if (tz == null) {
						String timezone = request.getParameter("timezone");
						
						if (timezone == null) {
							timezone = request.getHeader("X-TimeZone");
						}
						
						if (timezone == null) {
							tz = TimeZone.getDefault();
						}
						else {
							try {
								tz = TimeZone.getTimeZone(timezone.toUpperCase());
							} catch (final Exception e) {
								tz = TimeZone.getDefault();
							}
						}
					}
					
					final Calendar calendar = Calendar.getInstance(tz);
					
					calendar.set(Calendar.DAY_OF_MONTH, matcherData.group("date") != null ? Integer.parseInt(matcherData.group("date")) : 1);
					calendar.set(Calendar.MONTH, matcherData.group("month") != null ? Integer.parseInt(matcherData.group("month")) - 1 : 0);
					calendar.set(Calendar.YEAR, matcherData.group("year") != null ? Integer.parseInt(matcherData.group("year")) : 1970);
					
					calendar.set(Calendar.HOUR_OF_DAY, matcherData.group("hours") != null ? Integer.parseInt(matcherData.group("hours")) : 0);
					calendar.set(Calendar.MINUTE, matcherData.group("minutes") != null ? Integer.parseInt(matcherData.group("minutes")) : 0);
					calendar.set(Calendar.SECOND, matcherData.group("seconds") != null ? Integer.parseInt(matcherData.group("seconds")) : 0);
					
					return calendar.getTime();
				}
//				else {
//					super.convert(value, type);
//				}
			} else if (log.isDebugEnabled()) { log.debug("Value is an empty string (either \"\" or a string made only of spaces)"); }
		}
		else if (log.isDebugEnabled()) { log.debug("Value is null."); }
		
		return null;
	}
}
