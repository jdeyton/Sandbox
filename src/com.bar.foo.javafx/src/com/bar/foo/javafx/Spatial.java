/**
 * 
 */
package com.bar.foo.javafx;

import javafx.scene.Group;

import com.bar.foo.math.FloatMath;
import com.sun.javafx.geom.Matrix3f;
import com.sun.javafx.geom.Quat4f;
import com.sun.javafx.geom.Vec3f;

/**
 * This class wraps a {@link Group} and provides 3D transformation capabilities
 * in a straightforward fashion.
 * 
 * @author Jordan Deyton
 *
 */
public class Spatial extends Group {

	// To flip from right-hand to left-hand coordinate systems, reverse the
	// order of the vertices and multiply the z coordinate by -1.

	private final Quat4f rotation = new Quat4f();
	private final Vec3f scale = new Vec3f();
	private final Vec3f translation = new Vec3f();

	public void rotate(float xAngle, float yAngle, float zAngle) {

	}

	public void rotate(Vec3f angles) {

	}

	public void rotate(Quat4f quaternion) {
		
	}

	public void setRotation(float xAngle, float yAngle, float zAngle) {
		Quat4f rotation = getQuaternionFromAngles(xAngle, yAngle, zAngle);
		this.rotation.x = rotation.x;
		this.rotation.y = rotation.y;
		this.rotation.z = rotation.z;
		this.rotation.w = rotation.w;
	}

	public void setRotation(Vec3f angles) {
		setRotation(angles.x, angles.y, angles.z);
	}
	
	private static Quat4f getQuaternionFromAngles(float xAngle, float yAngle,
			float zAngle) {

		xAngle *= 0.5f;
		yAngle *= 0.5f;
		zAngle *= 0.5f;

		// I don't think this is right...
		
		float cosX = FloatMath.cos(xAngle);
		float sinX = FloatMath.sin(xAngle);
		float cosY = FloatMath.cos(yAngle);
		float sinY = FloatMath.sin(yAngle);
		float cosZ = FloatMath.cos(zAngle);
		float sinZ = FloatMath.sin(zAngle);

		float cosXcosY = cosX * cosY;
		float sinXcosY = sinX * cosY;
		float cosXsinY = cosX * sinY;
		float sinXsinY = sinX * sinY;
		
		float x = cosXcosY * cosZ + sinXsinY * sinZ;
		float y = sinXcosY * cosZ - cosXsinY * sinZ;
		float z = cosXsinY * cosZ + sinXcosY * sinZ;
		float w = cosXcosY * sinZ - sinXsinY * cosZ;

		// The constructor normalizes it.
		return new Quat4f(x, y, z, w);
	}

	public void setRotation(Matrix3f rotation) {
		this.rotation.set(rotation);
	}

	public void setRotation(Quat4f quaternion) {
		rotation.x = quaternion.x;
		rotation.y = quaternion.y;
		rotation.z = quaternion.z;
		rotation.w = quaternion.w;
	}

	public void scale(float scale) {
		scale(scale, scale, scale);
	}

	public void scale(float xScale, float yScale, float zScale) {
		scale.x *= xScale;
		scale.y *= yScale;
		scale.z *= zScale;
	}

	public void scale(Vec3f scales) {
		scale(scales.x, scales.y, scales.z);
	}

	public void setScale(float scale) {
		setScale(scale, scale, scale);
	}

	public void setScale(float xScale, float yScale, float zScale) {
		scale.set(xScale, yScale, zScale);
	}

	public void setScale(Vec3f scales) {
		scale.set(scales);
	}

	public void translate(float xOffset, float yOffset, float zOffset) {
		translation.x += xOffset;
		translation.y += yOffset;
		translation.z += zOffset;
	}

	public void translate(Vec3f offsets) {
		translation.add(offsets);
	}

	public void setTranslation(float x, float y, float z) {
		translation.set(x, y, z);
	}

	public void setTranslation(Vec3f location) {
		translation.set(location);
	}

	public void refreshTransform() {
		// TODO
	}
	
}
