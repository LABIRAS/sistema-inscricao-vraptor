package cc.labiras.web.util.vraptor;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides an easy way to use i18n messages through code and JSP.
 * 
 * @author Rafael Lins - g0dkar
 *
 */
@RequestScoped
public class Internationalization {
	private final ResourceBundle bundle;
	private static final Logger LOG = LoggerFactory.getLogger(Internationalization.class);
	
	/** CDI. @deprecated */
	@Deprecated Internationalization() {
//		bundle = ResourceBundle.getBundle("messages", Locale.getDefault());
		this(null);
	}
	
	@Inject
	public Internationalization(final ResourceBundle bundle) {
		this.bundle = bundle;
	}
	
	public String get(final String key) {
		return get(key, (Object[]) null);
	}
	
	public String get(final String key, final Object... params) {
		if (LOG.isWarnEnabled() && !bundle.containsKey(key)) {
			LOG.warn("I18N Key {} not found!", key);
		}
		
		final String message = bundle.getString(key);
		if (message != null) {
			return MessageFormat.format(message, params);
		}
		else {
			return null;
		}
	}
}
