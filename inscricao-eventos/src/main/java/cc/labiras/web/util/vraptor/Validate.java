package cc.labiras.web.util.vraptor;

import java.util.Collection;
import java.util.ResourceBundle;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import cc.labiras.web.util.StringUtils;
import cc.labiras.web.util.ValidationUtils;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.caelum.vraptor.view.Results;

/**
 * Makes VRaptor new {@link Validator} validations simple. They f_cked up pretty bad with this one.
 * 
 * This object tries to solve some of it's major problems such as having to create many objects that will probably never be used.
 * 
 * Please be aware of Java's rules for determining which method to call because all methods here have a {@code Object...} parameter.
 * 
 * @author g0dkar
 *
 */
@RequestScoped
public class Validate {
	private final Validator validator;
	private final ResourceBundle bundle;
	
	/** CDI needs this. Fuck CDI. */
	@Deprecated Validate() { this(null, null); }
	
	@Inject
	public Validate(final Validator validator, final ResourceBundle bundle) {
		this.validator = validator;
		this.bundle = bundle;
//		bundle = ResourceBundle.getBundle("messages", Locale.getDefault());
	}
	
	public Validator validator() {
		return validator;
	}
	
	public boolean hasErrors() {
		return validator.hasErrors();
	}
	
	public void sendError(final int error) {
		validator.onErrorUse(Results.http()).sendError(error);
	}
	
	public void sendError(final int error, final String message) {
		validator.onErrorUse(Results.http()).sendError(error, message);
	}
	
	public <T> T redirectTo(final T controller) {
		return validator.onErrorRedirectTo(controller);
	}
	
	public <T> T forwardTo(final T controller) {
		return validator.onErrorForwardTo(controller);
	}
	
	/**
	 * @return A {@link I18nMessage} object for the {@code message} I18N bundle key
	 */
	private I18nMessage msg(final String message, final Object... params) {
		return new I18nMessage("validation", message);
	}
	
	/**
	 * @return A {@link I18nMessage} object for the {@code message} I18N bundle key
	 */
	private I18nMessage msg(final String message, final String paramKey, final Object... params) {
		if (paramKey == null) {
			return msg(message);
		}
		
		return new I18nMessage("validation", message, getI18N(paramKey), params);
	}
	
	/**
	 * @param key I18N key
	 * @return The I18N key value or the key itself.
	 */
	private String getI18N(final String key) {
		return bundle.containsKey(key) ? bundle.getString(key) : key;
	}
	
	/**
	 * A custom validation. If {@code result} is {@code true} then it's valid.
	 */
	public Validate custom(final boolean result, final String fieldName, final String message, final Object... params) {
		if (!result) {
			validator.add(msg(message, fieldName, params));
		}
		
		return this;
	}
	
	/**
	 * Value cannot be null
	 */
	public Validate notNull(final Object value, final String fieldName, final Object... params) {
		return notNull(value, fieldName, "validation.null", params);
	}
	
	/**
	 * Value cannot be null
	 */
	public Validate notNull(final Object value, final String fieldName, final String message, final Object... params) {
		return custom(value != null, fieldName, message, params);
	}
	
	/**
	 * Checks if {@code value} is NOT {@code null} or {@link StringUtils#isBlank(String) a bunch for spaces}
	 */
	public Validate notNull(final String value, final String fieldName, final Object... params) {
		return notNull(value, fieldName, "validation.blank", params);
	}
	
	/**
	 * Checks if {@code value} is NOT {@code null} or {@link StringUtils#isBlank(String) a bunch for spaces}
	 */
	public Validate notNull(final String value, final String fieldName, final String message, final Object... params) {
		return custom(!StringUtils.isBlank(value), fieldName, message, params);
	}
	
	/**
	 * Checks if {@code value} is NOT {@code null} or {@link StringUtils#isBlank(String) a bunch for spaces} and is shorter than {@code length}
	 */
	public Validate notNullLen(final String value, final int length, final String fieldName, final Object... params) {
		return notNullLen(value, length, fieldName, "validation.blank", params);
	}
	
	/**
	 * Checks if {@code value} is NOT {@code null} or {@link StringUtils#isBlank(String) a bunch for spaces} and is shorter than {@code length}
	 */
	public Validate notNullLen(final String value, final int length, final String fieldName, final String message, final Object... params) {
		return custom(!StringUtils.isBlank(value) && value.length() <= length, fieldName, message, params);
	}
	
	/**
	 * The value can be {@code null}, but not {@link StringUtils#isBlank(String) blank}.
	 */
	public Validate notBlank(final String value, final String fieldName, final Object... params) {
		return notBlank(value, fieldName, "validation.blank", params);
	}
	
	/**
	 * The value can be {@code null}, but not {@link StringUtils#isBlank(String) blank}.
	 */
	public Validate notBlank(final String value, final String fieldName, final String message, final Object... params) {
		return custom(value != null && value.matches("\\s*"), fieldName, message, params);
	}
	
	/**
	 * The value can be {@code null}, but not {@link StringUtils#isBlank(String) blank} and is shorter than {@code length}.
	 */
	public Validate notBlankLen(final String value, final int length, final String fieldName, final Object... params) {
		return notBlankLen(value, length, fieldName, "validation.blank", params, params);
	}
	
	/**
	 * The value can be {@code null}, but not {@link StringUtils#isBlank(String) blank} and is shorter than {@code length}.
	 */
	public Validate notBlankLen(final String value, final int length, final String fieldName, final String message, final Object... params) {
		return custom(value != null && value.length() <= length && !value.matches("\\s*"), fieldName, message, params);
	}
	
	/**
	 * Value can be null. If not, it must obey a RegEx.
	 */
	public Validate regex(final String value, final String regex, final String fieldName, final Object... params) {
		return regex(value, regex, fieldName, "validation.regex", params);
	}
	
	/**
	 * Value can be null. If not, it must obey a RegEx.
	 */
	public Validate regex(final String value, final String regex, final String fieldName, final String message, final Object... params) {
		return custom(value != null && value.matches(regex), fieldName, message, params);
	}
	
	/**
	 * Value can be null. If not, it must obey a RegEx.
	 */
	public Validate regexLen(final String value, final String regex, final int length, final String fieldName, final Object... params) {
		return regexLen(value, regex, length, fieldName, "validation.regex", params);
	}
	
	/**
	 * Value can be null. If not, it must obey a RegEx.
	 */
	public Validate regexLen(final String value, final String regex, final int length, final String fieldName, final String message, final Object... params) {
		return custom(value != null && value.length() <= length && value.matches(regex), fieldName, message, params);
	}
	
	/**
	 * Value cannot be null and must obey a RegEx.
	 */
	public Validate regexNotNull(final String value, final String regex, final String fieldName, final Object... params) {
		return regexNotNull(value, regex, fieldName, "validation.regex", "validation.null", params);
	}
	
	/**
	 * Value cannot be null and must obey a RegEx.
	 */
	public Validate regexNotNull(final String value, final String regex, final String fieldName, final String messageRegEx, final String messageNotNull, final Object... params) {
		if (value == null) {
			validator.add(msg(messageNotNull, fieldName, params));
		}
		else if (!value.matches(regex)) {
			validator.add(msg(messageRegEx, fieldName, params));
		}
		
		return this;
	}
	
	/**
	 * Value cannot be null and must obey a RegEx.
	 */
	public Validate regexNotNullLen(final String value, final String regex, final int length, final String fieldName, final Object... params) {
		return regexNotNullLen(value, regex, length, fieldName, "validation.regex", "validation.null", "validation.length", params);
	}
	
	/**
	 * Value cannot be null and must obey a RegEx.
	 */
	public Validate regexNotNullLen(final String value, final String regex, final int length, final String fieldName, final String messageRegEx, final String messageNotNull, final String messageLen, final Object... params) {
		if (value == null) {
			validator.add(msg(messageNotNull, fieldName, params));
		}
		else if (value.length() > length) {
			validator.add(msg(messageLen, fieldName, params));
		}
		else if (!value.matches(regex)) {
			validator.add(msg(messageRegEx, fieldName, params));
		}
		
		return this;
	}
	
	/**
	 * The {@link Collection} cannot be null and cannot be {@link Collection#isEmpty() empty}
	 */
	public Validate collectionIsNotEmpty(final Collection<?> collection, final String fieldName, final Object... params) {
		return collectionIsNotEmpty(collection, fieldName, "validation.empty", params);
	}
	
	/**
	 * The {@link Collection} cannot be null and cannot be {@link Collection#isEmpty() empty}
	 */
	public Validate collectionIsNotEmpty(final Collection<?> collection, final String fieldName, final String message, final Object... params) {
		return custom(collection != null && !collection.isEmpty(), fieldName, message, params);
	}
	
	/**
	 * The {@link Collection} cannot be null and cannot be {@link Collection#isEmpty() empty}
	 */
	public Validate collectionIsNotEmptyLen(final Collection<?> collection, final int length, final String fieldName, final Object... params) {
		return collectionIsNotEmptyLen(collection, length, fieldName, "validation.empty", params);
	}
	
	/**
	 * The {@link Collection} cannot be null and cannot be {@link Collection#isEmpty() empty}
	 */
	public Validate collectionIsNotEmptyLen(final Collection<?> collection, final int length, final String fieldName, final String message, final Object... params) {
		return custom(collection != null && collection.size() <= length && !collection.isEmpty(), fieldName, message, params);
	}
	
	/**
	 * The {@link Collection} cannot be null and cannot be {@link Collection#isEmpty() empty}
	 */
	public Validate collectionLen(final Collection<?> collection, final int length, final String fieldName, final Object... params) {
		return collectionLen(collection, length, fieldName, "validation.collectionLength", params);
	}
	
	/**
	 * The {@link Collection} cannot be null and cannot be {@link Collection#isEmpty() empty}
	 */
	public Validate collectionLen(final Collection<?> collection, final int length, final String fieldName, final String message, final Object... params) {
		return custom(collection != null && collection.size() <= length, fieldName, message, params);
	}
	
	/**
	 * The {@link Collection} cannot be null and must {@link Collection#contains(Object) contain} the {@code element}.
	 */
	public <T> Validate collectionContains(final Collection<T> collection, final T element, final String fieldName, final Object... params) {
		return collectionContains(collection, element, fieldName, "validation.contains", params);
	}
	
	/**
	 * The {@link Collection} cannot be null and must {@link Collection#contains(Object) contain} the {@code element}.
	 */
	public <T> Validate collectionContains(final Collection<T> collection, final T element, final String fieldName, final String message, final Object... params) {
		return custom(collection == null || collection.contains(element), fieldName, message, params);
	}
	
	/**
	 * The value cannot be null and must be a valid value of the {@code enumeration} (case insensitive)
	 */
	public Validate isEnumValue(final Enum<?> enumeration, final String value, final String fieldName, final Object... params) {
		return isEnumValue(enumeration, fieldName, "validation.empty", params);
	}
	
	/**
	 * The value cannot be null and must be a valid value of the {@code enumClass} (case insensitive)
	 */
	public Validate isEnumValue(final Class<? extends Enum> enumClass, String value, final String fieldName, final String message, final Object... params) {
		if (value != null) {
			value = value.toUpperCase();
			boolean valid = false;
			
			for (final Enum<?> enumConst : enumClass.getEnumConstants()) {
				if (enumConst.name().equals(value)) {
					valid = true;
					break;
				}
			}
			
			return custom(valid, fieldName, message, params);
		}
		
		return this;
	}
	
	/**
	 * The value can be {@code null}, but not {@link StringUtils#isBlank(String) blank}.
	 */
	public Validate url(final String value, final String fieldName, final Object... params) {
		return url(value, fieldName, "validation.url", params);
	}
	
	/**
	 * The value can be {@code null}, but not {@link StringUtils#isBlank(String) blank}.
	 */
	public Validate url(final String value, final String fieldName, final String message, final Object... params) {
		return custom(ValidationUtils.url(value, true), fieldName, message, params);
	}
	
	/**
	 * The value can be {@code null}, but not {@link StringUtils#isBlank(String) blank}.
	 */
	public Validate urlNotNull(final String value, final String fieldName, final Object... params) {
		return urlNotNull(value, fieldName, "validation.url", params);
	}
	
	/**
	 * The value can be {@code null}, but not {@link StringUtils#isBlank(String) blank}.
	 */
	public Validate urlNotNull(final String value, final String fieldName, final String message, final Object... params) {
		return custom(ValidationUtils.url(value, false), fieldName, message, params);
	}
	
	/**
	 * The value can be {@code null}, but not {@link StringUtils#isBlank(String) blank}.
	 */
	public Validate color(final String value, final String fieldName, final Object... params) {
		return color(value, fieldName, "validation.url", params);
	}
	
	/**
	 * The value can be {@code null}, but not {@link StringUtils#isBlank(String) blank}.
	 */
	public Validate color(final String value, final String fieldName, final String message, final Object... params) {
		return custom(ValidationUtils.cssColor(value, true), fieldName, message, params);
	}
	
	/**
	 * The value can be {@code null}, but not {@link StringUtils#isBlank(String) blank}.
	 */
	public Validate colorNotNull(final String value, final String fieldName, final Object... params) {
		return colorNotNull(value, fieldName, "validation.url", params);
	}
	
	/**
	 * The value can be {@code null}, but not {@link StringUtils#isBlank(String) blank}.
	 */
	public Validate colorNotNull(final String value, final String fieldName, final String message, final Object... params) {
		return custom(ValidationUtils.cssColor(value, false), fieldName, message, params);
	}
}
