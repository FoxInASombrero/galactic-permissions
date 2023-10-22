package hu.szviktor.permissions.action;

/**
 * Az alábbi funkcionális interfész reprezentálja a végrehajtott cselekmény
 * eredményét.
 * 
 * @author szviktor
 *
 */

@FunctionalInterface
public interface PermissionAction {

	/**
	 * A cselekmény végrehajtásának eredményének kikérése.
	 * 
	 * @return A cselekmény státuszát tartalmazó objektum.
	 */

	ActionResponse getResponse();

}
