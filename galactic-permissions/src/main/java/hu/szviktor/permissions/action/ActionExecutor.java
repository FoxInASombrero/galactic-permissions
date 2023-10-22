package hu.szviktor.permissions.action;

import java.util.concurrent.CompletableFuture;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * Ez az interfész reprezentál minden művelet végrehajtására alkalmazás
 * osztályt.
 * 
 * @author szviktor
 *
 */

public interface ActionExecutor {

	/**
	 * Művelet végrehajtása.
	 * 
	 * @param <T>    - PermissionAction
	 * @param source - Végrehajtandó művelet.
	 * @return - A művelet végeredménye, rendszerint még egy
	 *         {@link CompletableFuture}
	 */

	<T extends PermissionAction> void executeAction(@NotNull CommandSender executor,
			@NotNull CompletableFuture<T> source);

}
