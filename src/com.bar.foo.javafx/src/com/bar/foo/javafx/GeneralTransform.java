package com.bar.foo.javafx;

import javafx.scene.transform.Transform;

import com.bar.foo.math.Matrix3f;
import com.bar.foo.math.Quaternion;
import com.bar.foo.math.Vector3f;
import com.sun.javafx.geom.transform.Affine3D;
import com.sun.javafx.geom.transform.BaseTransform;

public class GeneralTransform extends Transform {

	public final Vector3f scale = new Vector3f(Vector3f.IDENTITY);
	public final Quaternion rotation = new Quaternion(Quaternion.IDENTITY);
	public final Vector3f translation = new Vector3f();

	private final Matrix3f transformMatrix = new Matrix3f(Matrix3f.IDENTITY);

	// FIXME Translations are applied without calling this method, but scales
	// and multiplications are not. This is because the getTx(), getTy(), ...
	// methods directly reference the translation vector, but the other methods
	// reference the transformation matrix, which may be stale.
	public GeneralTransform recomputeMatrix() {
		// To combine transformation matrices, you multiply them. A scale
		// transformation matrix is represented by the identity matrix with the
		// x, y, and z scales down the diagonal. Fortunately, multiplying scale
		// matrix A with a rotation matrix B is a relatively trivial computation
		// where the first row of the rotation matrix is multiplied by the x
		// scale, the second row by the y scale, and the third row by the z
		// scale. So first, fill the transform matrix with the rotation, then
		// add in the axis scales.
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
		return this;
	}
	
	public void refresh() {
		transformChanged();
	}

	@Override
	public double getMxx() {
		return transformMatrix.m00;
	}

	@Override
	public double getMxy() {
		return transformMatrix.m01;
	}

	@Override
	public double getMxz() {
		return transformMatrix.m02;
	}

	@Override
	public double getMyx() {
		return transformMatrix.m10;
	}

	@Override
	public double getMyy() {
		return transformMatrix.m11;
	}

	@Override
	public double getMyz() {
		return transformMatrix.m12;
	}

	@Override
	public double getMzx() {
		return transformMatrix.m20;
	}

	@Override
	public double getMzy() {
		return transformMatrix.m21;
	}

	@Override
	public double getMzz() {
		return transformMatrix.m22;
	}

	@Override
	public double getTx() {
		return translation.x;
	}

	@Override
	public double getTy() {
		return translation.y;
	}

	@Override
	public double getTz() {
		return translation.z;
	}

	@Override
	public void impl_apply(Affine3D t) {
		t.concatenate(getMxx(), getMxy(), getMxz(), getTx(), getMyx(),
				getMyy(), getMyz(), getTy(), getMzx(), getMzy(), getMzz(),
				getTz());
	}

	@Override
	public BaseTransform impl_derive(BaseTransform t) {
		return t.deriveWithConcatenation(getMxx(), getMxy(), getMxz(), getTx(),
				getMyx(), getMyy(), getMyz(), getTy(), getMzx(), getMzy(),
				getMzz(), getTz());
	}
}
