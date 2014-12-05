/**
 * 
 */
package com.bar.foo.javafx.input;

import javafx.scene.input.MouseEvent;

/**
 * This is a convenience class for mouse-based "analog" actions. For instances
 * of this class, mouse-events <i>are triggered repeatedly</i>.
 * 
 * <p>
 * This class is particularly useful for handling mouse movement. For instance,
 * you might use the following code:
 * 
 * <pre>
 * <code>
 * controlManager.mouse.addAnalog(MouseCode.MOVE, new MouseAnalogAction() {
 *     {@literal @}Override
 *     public void run(float value, float timePerFrame, MouseEvent event) {
 *         // Do interesting things with the mouse.
 * 	       System.out.println("x,y: " + event.getSceneX() + ","
 * 		        + event.getSceneY());
 *     }		
 * });
 * </code>
 * </pre>
 * 
 * </p>
 * 
 * @author Jordan Deyton
 *
 */
public interface MouseAnalogAction extends AnalogAction<MouseEvent> {

}
