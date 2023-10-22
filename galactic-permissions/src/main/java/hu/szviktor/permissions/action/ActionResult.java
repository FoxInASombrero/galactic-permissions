package hu.szviktor.permissions.action;

import org.jetbrains.annotations.NotNull;

import com.google.common.base.Preconditions;

public enum ActionResult {

	SUCCESS("A művelet sikeresen végrehajtva!"), FAILURE("Nem sikerült a művelet végrehajtása!");

	@NotNull
	private String result;

	private ActionResult(@NotNull String result) {
		Preconditions.checkArgument(result != null, "Az eredmény nem lehet null!");

		this.result = result;
	}

	public @NotNull String getResultMessage() {
		return this.result;
	}

}
