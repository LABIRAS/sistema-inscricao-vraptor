package cc.labiras.web.util.controller.helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import cc.labiras.web.util.vraptor.Internationalization;
import cc.labiras.web.util.vraptor.Validate;
import cc.labiras.web.util.vraptor.component.Config;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;

/**
 * Helper class to simplfy the creation of CRUDs. It dictates the default CRUD methods that should be implemented
 * in order to have a functional CRUD.
 * 
 * @author g0dkar
 *
 * @param <T> Entity the CRUD is all about.
 */
public abstract class CRUDController<T> extends ControllerHelper {
	protected final Validate validator;
	
	public CRUDController(final Result result, final HttpServletRequest request, final HttpServletResponse response, final Session session, final Validate validator, final Internationalization localization, final Config config) {
		super(result, request, response, session, localization, config);
		this.validator = validator;
	}
	
	/**
	 * First page of a CRUD. Default: Redirect to the Listing page.
	 */
	@Path("/")
	public void index() {
		if (log.isDebugEnabled()) { log.debug("index() - Forwarding to list()"); }
		result.redirectTo(this).list();
	}
	
	/**
	 * "View" page of the entity CRUD
	 * @param id Entity ID
	 */
//	@Path("/view/{id:\\d+}")
	public abstract void view(final String id);
	
	/**
	 * "Listing" page of the entity CRUD
	 */
	@Path("/list")
	public abstract void list();
	
	/**
	 * "Create" page for the entity CRUD
	 */
//	@Path("/create")
	public abstract void create(T obj);
	
	/**
	 * Saves a new entity on the Database.
	 * @param obj Entity that should be saved
	 */
//	@Path("/save")
	public abstract void save(final T obj);
	
	/**
	 * "Update" page for the entity CRUD
	 * @param id Entity ID
	 */
//	@Path("/edit/{id:\\d+}")
	public abstract void edit(final String id);
	
	/**
	 * Applies between the entity passed as a parameter and the an existing entity on the Database.
	 * @param obj The updated entity
	 */
//	@Path("/update")
	public abstract void update(final T obj);
	
	/**
	 * Removes an entity from the Database.
	 * @param id Entity ID
	 */
//	@Path("/delete/{id:\\d+}")
	public abstract void delete(final String id);
	
	/**
	 * Execute the validation of a single entity.
	 * @param obj The entity to be validated
	 */
	public abstract void validate(final T obj);
}
