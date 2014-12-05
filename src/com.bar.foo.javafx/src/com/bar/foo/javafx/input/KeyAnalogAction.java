/**
 * 
 */
package com.bar.foo.javafx.input;

import javafx.scene.input.KeyEvent;

/**
 * This is a convenience class for key-based "analog" actions. For instances of
 * this class, key-press and key-release are treated as two distinct actions and
 * <i>are triggered repeatedly</i>.
 * 
 * @author Jordan Deyton
 *
 */
public interface KeyAnalogAction extends AnalogAction<KeyEvent> {

}
