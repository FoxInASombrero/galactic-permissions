package hu.szviktor.permissions.permissible;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import hu.szviktor.permissions.action.PermissionAction;

/**
 * Az alábbi interfész reprezentál minden olyan objektumot, mely prefixel,
 * suffixal és jogosultsággokal tud rendelkezni.
 * 
 * Jelenleg ezen objektumok egyike lehet egy játékos és egy rang.
 * 
 * @author szviktor
 *
 */

public interface Permissible extends ConfigurationSerializable {

	/**
	 * Objektum nevének kikérése.
	 * <p>
	 * Játékos esetén a játékos nevét, rang esetén a rang nevét reprezentálja.
	 * </p>
	 * 
	 * @return Objektum neve. (pl. "valaki123", vagy "Jatekos")
	 */

	@NotNull
	String getName();

	/**
	 * Objektum prefixének kikérése.
	 * 
	 * @return Objektum prefixe.
	 */

	@NotNull
	String getPrefix();

	/**
	 * Objektum suffixának kikérése.
	 * 
	 * @return Objektum suffixa.
	 */

	@NotNull
	String getSuffix();

	/**
	 * Objektum jogosultságainak kikérése.
	 * <p>
	 * Az alábbi lista tartalmazza a játékos, vagy a rang jogosultágait.<br>
	 * Játékos esetén a rang és a játékos egyéni jogai is a lista részét képezik.
	 * </p>
	 * 
	 * @return Objektum jogosultságai.
	 */

	@NotNull
	List<String> getPermissions();

	/**
	 * Objektum prefixének beállítása.
	 * 
	 * @param prefix - Az újonnan használni kívánt prefix.
	 * 
	 * @return A művelet végrehajtásának sikeressége.
	 */

	@NotNull
	CompletableFuture<PermissionAction> setPrefix(@NotNull String prefix);

	/**
	 * Objektum suffixának beállítása.
	 * 
	 * @param suffix - Az újonnan használni kívánt suffix.
	 * 
	 * @return A művelet végrehajtásának sikeressége.
	 */

	@NotNull
	CompletableFuture<PermissionAction> setSuffix(@NotNull String suffix);

	/**
	 * Jogosultság hozzáadása az objektumhoz.
	 * 
	 * @param permission - A hozzáadni kívánt jogosultság.
	 * 
	 * @return A művelet végrehajtásának sikeressége.
	 */

	@NotNull
	CompletableFuture<PermissionAction> addPermission(@NotNull String permission);

	/**
	 * Jogosultság megvonása az objektumtól.
	 * 
	 * @param permission - A megvonni kívánt jogosultság.
	 * 
	 * @return A művelet végrehajtásának sikeressége.
	 */

	@NotNull
	CompletableFuture<PermissionAction> revokePermission(@NotNull String permission);

}
