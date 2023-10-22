package hu.szviktor.permissions.permissible;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import hu.szviktor.permissions.action.ActionResponse;
import hu.szviktor.permissions.action.ActionResult;
import hu.szviktor.permissions.action.PermissionAction;

/**
 * Ez az osztály reprezentálja a rangokat.
 * 
 * @author szviktor
 *
 */

public class Group implements Permissible {

	@NotNull
	private String name;

	@NotNull
	private String prefix;

	@NotNull
	private String suffix;

	boolean isDefault;

	@NotNull
	private List<String> permissions;

	@Nullable
	private List<String> inheritances;

	public Group(@NotNull String name, @NotNull String prefix, @NotNull String suffix, boolean isDefault,
			@NotNull List<String> permissions, @Nullable List<String> inheritances) {
		Preconditions.checkArgument(name != null, "A rang neve nem lehet null!");
		Preconditions.checkArgument(prefix != null, "A rang előtagja nem lehet null!");
		Preconditions.checkArgument(suffix != null, "A rang utótagja nem lehet null!");
		Preconditions.checkArgument(permissions != null, "A rang jogosultsági listája nem lehet null!");

		this.name = name;
		this.prefix = prefix;
		this.suffix = suffix;
		this.isDefault = isDefault;
		this.permissions = permissions;
		this.inheritances = inheritances;
	}

	@Override
	public @NotNull String getName() {
		return this.name;
	}

	@Override
	public @NotNull String getPrefix() {
		return this.prefix;
	}

	@Override
	public @NotNull String getSuffix() {
		return this.suffix;
	}

	public boolean isDefault() {
		return this.isDefault;
	}

	@Override
	public @NotNull List<String> getPermissions() {
		return this.permissions;
	}

	public @Nullable List<String> getInheritances() {
		return this.inheritances;
	}

	@Override
	public @NotNull CompletableFuture<PermissionAction> setPrefix(@NotNull String prefix) {
		Preconditions.checkArgument(prefix != null, "A rang előtagja nem lehet null!");

		this.prefix = prefix;

		return CompletableFuture.completedFuture(new PermissionAction() {
			@Override
			public ActionResponse getResponse() {
				return new ActionResponse(ActionResult.SUCCESS, "A rang új prefixe sikeresen beállítva!");
			}
		});
	}

	@Override
	public @NotNull CompletableFuture<PermissionAction> setSuffix(@NotNull String suffix) {
		Preconditions.checkArgument(prefix != null, "A rang előtagja nem lehet null!");

		this.suffix = suffix;

		return CompletableFuture.completedFuture(new PermissionAction() {
			@Override
			public ActionResponse getResponse() {
				return new ActionResponse(ActionResult.SUCCESS, "A rang új suffixa sikeresen beállítva!");
			}
		});
	}

	public @NotNull CompletableFuture<PermissionAction> setDefault(boolean isDefault) {
		this.isDefault = isDefault;

		return CompletableFuture.completedFuture(new PermissionAction() {
			@Override
			public ActionResponse getResponse() {
				return new ActionResponse(ActionResult.SUCCESS,
						isDefault ? "A rang sikeresen beállítva alapértelmezetté!"
								: "A rang sikeresen átállítva nem alapértelmezetté!");
			}
		});
	}

	@Override
	public @NotNull CompletableFuture<PermissionAction> addPermission(@NotNull String permission) {
		Preconditions.checkArgument(permission != null, "A hozzáadni kívánt jogosultság nem lehet null!");

		boolean success = (!(this.permissions.contains(permission))) && (this.permissions.add(permission));

		if (!success)
			return CompletableFuture.completedFuture(new PermissionAction() {
				@Override
				public ActionResponse getResponse() {
					return new ActionResponse(ActionResult.FAILURE, "A rang már rendelkezik ezzel a jogosultsággal!");
				}
			});

		return CompletableFuture.completedFuture(new PermissionAction() {
			@Override
			public ActionResponse getResponse() {
				return new ActionResponse(ActionResult.SUCCESS, "Jogosultság sikeresen hozzáadva a ranghoz!");
			}
		});
	}

	@Override
	public @NotNull CompletableFuture<PermissionAction> revokePermission(@NotNull String permission) {
		Preconditions.checkArgument(permission != null, "Az eltávolítani kívánt jogosultság nem lehet null!");

		boolean success = (this.permissions.contains(permission)) && (this.permissions.remove(permission));

		if (!success)
			return CompletableFuture.completedFuture(new PermissionAction() {
				@Override
				public ActionResponse getResponse() {
					return new ActionResponse(ActionResult.FAILURE, "A rang nem rendelkezik ezzel a jogosultsággal!");
				}
			});

		return CompletableFuture.completedFuture(new PermissionAction() {
			@Override
			public ActionResponse getResponse() {
				return new ActionResponse(ActionResult.SUCCESS, "Jogosultság sikeresen eltávolítva a rangból!");
			}
		});
	}

	@Override
	public Map<String, Object> serialize() {
		Preconditions.checkArgument(this.name != null, "A rang neve nem lehet null!");
		Preconditions.checkArgument(this.prefix != null, "A rang előtagja nem lehet null!");
		Preconditions.checkArgument(this.suffix != null, "A rang utótagja nem lehet null!");
		Preconditions.checkArgument(this.permissions != null, "A rang jogosultsági listája nem lehet null!");

		Map<String, Object> result = Maps.newHashMap();

		result.put("prefix", this.prefix);
		result.put("suffix", this.suffix);
		result.put("default", this.isDefault);
		result.put("permissions", this.permissions);
		result.put("inheritances", this.inheritances);

		return result;
	}

}
