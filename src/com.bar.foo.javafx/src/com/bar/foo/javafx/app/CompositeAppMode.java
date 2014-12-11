package com.bar.foo.javafx.app;

import java.util.ArrayList;
import java.util.List;

public class CompositeAppMode extends AppMode {

	// TODO Replace this with either a more thread-friendly collection or add
	// some locks to make this list thread-safe (the latter will probably be
	// easier to implement).
	private final List<AppMode> subModes = new ArrayList<AppMode>();

	public boolean addSubMode(AppMode subMode) {
		boolean added = false;
		return added;
	}

	public boolean removeSubMode(AppMode subMode) {
		boolean removed = false;
		return removed;
	}

	@Override
	protected void initMode() {

	}

	@Override
	protected void cleanupMode() {

	}

	@Override
	protected void enableMode() {

	}

	@Override
	protected void disableMode() {

	}

	@Override
	protected void showMode() {

	}

	@Override
	protected void hideMode() {

	}
}
