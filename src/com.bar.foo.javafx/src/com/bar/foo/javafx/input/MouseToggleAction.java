/**
 * 
 */
package com.bar.foo.javafx.input;

import javafx.scene.input.MouseEvent;

/**
 * This is a convenience class for mouse-based "toggle" actions. For instances
 * of this class, mouse-press and mouse-release are treated as two distinct,
 * single actions and are <i>not</i> triggered repeatedly.
 * 
 * <p>
 * This class is particularly useful for handling mouse clicks. For instance,
 * you might use the following code:
 * 
 * <pre>
 * <code>
 * 	controlManager.mouse.addToggle(MouseCode.BUTTON_PRIMARY, new MouseToggleAction() {
 * 		{@literal @}Override
 * 		public void pressed(float timePerFrame, MouseEvent event) {
 * 			// The mouse button was just pressed down...
 * 		}
 * 		{@literal @}Override
 * 		public void released(float timePerFrame, MouseEvent event) {
 * 			// The mouse button was just released...
 * 		}
 * 	});
 * </code>
 * </pre>
 * 
 * </p>
 * 
 * @author Jordan Deyton
 *
 */
public abstract class MouseToggleAction extends ToggleAction<MouseEvent> {

}
