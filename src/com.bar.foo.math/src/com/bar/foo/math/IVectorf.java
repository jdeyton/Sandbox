package com.bar.foo.math;

interface IVectorf<V extends IVectorf<V>> extends IVector<V, Float> {

	// ---- Scalar Operations ---- //
	V add(float scalar);

	V add(float scalar, V cache);

	V subtract(float scalar);

	V subtract(float scalar, V cache);

	V multiply(float scalar);

	V multiply(float scalar, V cache);

	V divide(float scalar);

	V divide(float scalar, V cache);
	// --------------------------- //

}
