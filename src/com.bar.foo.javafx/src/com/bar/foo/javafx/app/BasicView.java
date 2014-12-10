package com.bar.foo.javafx.app;

import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.swt.widgets.Composite;

import com.bar.foo.javafx.IFrameRateManager;
import com.bar.foo.javafx.input.ControlManager;
import com.bar.foo.javafx.scene.camera.CameraNode;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;

/**
 * TODO This is a final version of EmbeddedView. This is much simpler than the
 * related jME-based code because we can effectively combine the
 * MasterApplication, EmbeddedView, and ViewAppState all in one package.
 * 
 * @author Jordan Deyton
 *
 */
public abstract class BasicView implements IFrameRateManager {

	// ---- Framerate management ---- //
	// TODO documentation
	private static AnimationTimer timer;
	private static final AtomicInteger viewCount = new AtomicInteger();
	private static float timePerFrame;
	// ------------------------------ //

	private final boolean disposed = false;

	// ---- Basic requirements ---- //
	private Scene scene;
	private CameraNode camera;
	private Composite composite;
	private ControlManager controls;
	// ---------------------------- //

	/**
	 * The default constructor.
	 */
	public BasicView() {
		// If necessary, create and start the timer responsible for updating the
		// timePerFrame.
		if (viewCount.getAndIncrement() == 0) {
			timer = new AnimationTimer() {
				// The nanosecond time of the previous frame.
				private long previous;

				@Override
				public void handle(long now) {
					// This method is called in each frame. Compute the time per
					// frame.
					timePerFrame = (now - previous) * 0.000000001f;
					previous = now;
				}
			};
			timer.start();
		}

		return;
	}

	public Composite createComposite(Composite parent) {
		// TODO 
		return composite;
	}
	
	/**
	 * Disposes the view and releases any of its resources.
	 */
	public void dispose() {
		if (!disposed) {

			// If necessary, stop the timer.
			if (viewCount.decrementAndGet() == 0) {
				timer.stop();
				timePerFrame = 0f;
			}

		}
		return;
	}

	// ---- Implements IFrameRateManager ---- //
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.javafx.IFrameRateManager#getFPS()
	 */
	@Override
	public float getFPS() {
		return 1f / timePerFrame;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.javafx.IFrameRateManager#getTPF()
	 */
	@Override
	public float getTPF() {
		return timePerFrame;
	}
	// -------------------------------------- //
}
