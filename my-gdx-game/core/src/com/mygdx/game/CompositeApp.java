package com.mygdx.game;

import java.util.LinkedHashSet;
import java.util.Set;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;

public class CompositeApp implements ApplicationListener {

	private Set<ApplicationListener> childApps = new LinkedHashSet<ApplicationListener>();

	@Override
	public void create() {
		// Initialize the set of child app listeners.
		// childApps = new LinkedHashSet<ApplicationListener>();
	}

	@Override
	public void resize(int width, int height) {
		// Resize all child app listeners.
		for (ApplicationListener app : childApps) {
			app.resize(width, height);
		}
		// If continuous rendering is disabled, we need to trigger a render.
		if (!Gdx.graphics.isContinuousRendering()) {
			Gdx.graphics.requestRendering();
		}
	}

	@Override
	public void render() {
		// Render all child app listeners.
		for (ApplicationListener app : childApps) {
			app.render();
		}
	}

	@Override
	public void pause() {
		// Pause all child app listeners.
		for (ApplicationListener app : childApps) {
			app.pause();
		}
	}

	@Override
	public void resume() {
		// Resume all child app listeners.
		for (ApplicationListener app : childApps) {
			app.resume();
		}
	}

	@Override
	public void dispose() {
		// Dispose all child app listeners.
		for (ApplicationListener app : childApps) {
			app.dispose();
		}
		childApps.clear();
		childApps = null;
	}

	public boolean attach(ApplicationListener app) {
		boolean canAttach = app != null && !childApps.contains(app);
		// If the child can be added, give it the same create/resize startup.
		if (canAttach) {
			app.create();
			Graphics appGraphics = Gdx.graphics;
			app.resize(appGraphics.getWidth(), appGraphics.getHeight());
			// Finally, add it to the set of children.
			childApps.add(app);
		}
		return canAttach;
	}

	public boolean detach(ApplicationListener app) {
		// If the child could be removed, give it the same disposal at close.
		boolean detached = childApps.remove(app);
		if (detached) {
			app.dispose();
		}
		return detached;
	}
}
