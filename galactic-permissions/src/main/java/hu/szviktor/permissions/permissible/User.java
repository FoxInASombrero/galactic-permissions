package hu.szviktor.permissions.permissible;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.NotNull;

import hu.szviktor.permissions.action.PermissionAction;

/**
 * Ez az osztály reprezentálja a felhasználókat.
 * 
 * @author szviktor
 *
 */

public class User implements Permissible {

	@NotNull
	private UUID uniqueId;

	public User(@NotNull UUID uniqueId) {

	}

	@Override
	public @NotNull String getName() {
		return null;
	}

	@Override
	public @NotNull String getPrefix() {
		return null;
	}

	@Override
	public @NotNull String getSuffix() {
		return null;
	}

	@Override
	public @NotNull List<String> getPermissions() {
		return null;
	}

	public @NotNull String getGroupName() {
		return null;
	}

	@Override
	public Map<String, Object> serialize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull CompletableFuture<PermissionAction> addPermission(@NotNull String permission) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull CompletableFuture<PermissionAction> revokePermission(@NotNull String permission) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull CompletableFuture<PermissionAction> setPrefix(@NotNull String prefix) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull CompletableFuture<PermissionAction> setSuffix(@NotNull String suffix) {
		// TODO Auto-generated method stub
		return null;
	}

}
