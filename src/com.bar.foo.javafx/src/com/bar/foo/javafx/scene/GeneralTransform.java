package com.bar.foo.javafx.scene;

import javafx.scene.transform.Transform;

import com.bar.foo.math.Matrix3f;
import com.bar.foo.math.Quaternion;
import com.bar.foo.math.Vector3f;
import com.sun.javafx.geom.transform.Affine3D;
import com.sun.javafx.geom.transform.BaseTransform;

/**
 * This class provides a general 3D transformation that, instead of relying on
 * the JavaFX transform classes, wraps our custom math library to construct a
 * combined transformation matrix for scale, rotation, and translation.
 * <p>
 * To change the transformation, modify the {@link #scale}, {@link #rotation},
 * and/or {@link #translation}, then call {@link #refresh(boolean)} to ensure
 * the changes are applied to the underlying rendering engine.
 * </p>
 * 
 * @author Jordan Deyton
 *
 */
public class GeneralTransform extends Transform {

	/**
	 * The transformation's scale. Any of the x, y, and z coordinates can be
	 * specified individually. This is applied before rotating or translating.
	 */
	public final Vector3f scale = new Vector3f(Vector3f.IDENTITY);
	/**
	 * The transformation's rotation. This is represented with a quaternion to
	 * enable operations such as slerp. This is applied after scaling but before
	 * translating.
	 */
	public final Quaternion rotation = new Quaternion(Quaternion.IDENTITY);
	/**
	 * The transformation's translation or position. This is applied after
	 * scaling and rotating.
	 */
	public final Vector3f translation = new Vector3f();

	/**
	 * The transformation matrix representing the scale and rotation. JavaFX
	 * uses a 3x4 transformation matrix (the 4th column is the translation
	 * vector). Instead, we separate the 3x3 scale and rotation elements from
	 * the translation vector.
	 * <p>
	 * When the {@link #scale} or {@link #rotation} is changed, this will need
	 * to be updated via {@link #recomputeTransformMatrix()} before calling
	 * {@link #transformChanged()}.
	 * </p>
	 */
	private final Matrix3f transformMatrix = new Matrix3f(Matrix3f.IDENTITY);

	/**
	 * Applies any changes with the transformation to the underlying rendering
	 * system.
	 * 
	 * @param scaledOrRotated
	 *            Whether or not the scale or rotation has been changed. If
	 *            true, the transformation matrix will need to be recomputed.
	 */
	public void refresh(boolean scaledOrRotated) {
		// If the scale or rotation has changed, recompute the 3x3
		// transformation matrix.
		if (scaledOrRotated) {
			recomputeTransformMatrix();
		}
		transformChanged();
	}

	/**
	 * Recomputes the elements of the {@link #transformMatrix transformation
	 * matrix} that contains the scale and rotation.
	 */
	private void recomputeTransformMatrix() {
		// To combine transformation matrices, you multiply them. A scale
		// transformation matrix is represented by the identity matrix with the
		// x, y, and z scales down the diagonal. Fortunately, multiplying scale
		// matrix A with rotation matrix B is a relatively trivial computation
		// where the first row of the rotation matrix is multiplied by the x
		// scale, the second row by the y scale, and the third row by the z
		// scale. Thus, we first fill the transform matrix with the rotation,
		// then add in the axis scales.
		rotation.fillRotationMatrix(transformMatrix);
		transformMatrix.m00 *= scale.x;
		transformMatrix.m01 *= scale.x;
		transformMatrix.m02 *= scale.x;
		transformMatrix.m10 *= scale.y;
		transformMatrix.m11 *= scale.y;
		transformMatrix.m12 *= scale.y;
		transformMatrix.m20 *= scale.z;
		transformMatrix.m21 *= scale.z;
		transformMatrix.m22 *= scale.z;

		return;
	}

	// ---- Transformation matrix getters from class Transform ---- //
	// TODO We may also be able to override the transform methods (e.g.,
	// transformPoint3D) instead of using the transformation matrix. The number
	// of mathematical operations from computing the transformation
	// matrix/multiplying it by points will need to be compared with directly
	// applying the scale, rotation, and translation to the point.

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.scene.transform.Transform#getMxx()
	 */
	@Override
	public double getMxx() {
		return transformMatrix.m00;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.scene.transform.Transform#getMxy()
	 */
	@Override
	public double getMxy() {
		return transformMatrix.m01;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.scene.transform.Transform#getMxz()
	 */
	@Override
	public double getMxz() {
		return transformMatrix.m02;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.scene.transform.Transform#getMyx()
	 */
	@Override
	public double getMyx() {
		return transformMatrix.m10;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.scene.transform.Transform#getMyy()
	 */
	@Override
	public double getMyy() {
		return transformMatrix.m11;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.scene.transform.Transform#getMyz()
	 */
	@Override
	public double getMyz() {
		return transformMatrix.m12;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.scene.transform.Transform#getMzx()
	 */
	@Override
	public double getMzx() {
		return transformMatrix.m20;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.scene.transform.Transform#getMzy()
	 */
	@Override
	public double getMzy() {
		return transformMatrix.m21;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.scene.transform.Transform#getMzz()
	 */
	@Override
	public double getMzz() {
		return transformMatrix.m22;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.scene.transform.Transform#getTx()
	 */
	@Override
	public double getTx() {
		return translation.x;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.scene.transform.Transform#getTy()
	 */
	@Override
	public double getTy() {
		return translation.y;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.scene.transform.Transform#getTz()
	 */
	@Override
	public double getTz() {
		return translation.z;
	}

	// ------------------------------------------------------------ //

	// ---- Extends Transform ---- //
	// These methods must be implemented. As of JDK 1.8.0u25, if these methods
	// are not implemented, you may get null pointer exceptions when added to
	// a JavaFX Node's transform list.

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javafx.scene.transform.Transform#impl_apply(com.sun.javafx.geom.transform
	 * .Affine3D)
	 */
	@Override
	public void impl_apply(Affine3D t) {
		t.concatenate(getMxx(), getMxy(), getMxz(), getTx(), getMyx(),
				getMyy(), getMyz(), getTy(), getMzx(), getMzy(), getMzz(),
				getTz());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javafx.scene.transform.Transform#impl_derive(com.sun.javafx.geom.transform
	 * .BaseTransform)
	 */
	@Override
	public BaseTransform impl_derive(BaseTransform t) {
		return t.deriveWithConcatenation(getMxx(), getMxy(), getMxz(), getTx(),
				getMyx(), getMyy(), getMyz(), getTy(), getMzx(), getMzy(),
				getMzz(), getTz());
	}
	// --------------------------- //
}
