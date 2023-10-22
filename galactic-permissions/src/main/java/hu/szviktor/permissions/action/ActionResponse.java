package hu.szviktor.permissions.action;

import org.jetbrains.annotations.NotNull;

import com.google.common.base.Preconditions;

public class ActionResponse {

	private ActionResult result;

	@NotNull
	private String message;

	public ActionResponse(ActionResult result, @NotNull String message) {
		Preconditions.checkArgument(message != null, "Az üzenet nem lehet null!");

		this.result = result;
		this.message = message;
	}

	public ActionResult getResult() {
		return this.result;
	}

	public @NotNull String getMessage() {
		return this.message;
	}

}
