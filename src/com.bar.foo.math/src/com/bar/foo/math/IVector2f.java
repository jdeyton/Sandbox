package com.bar.foo.math;

interface IVector2f<V extends IVector2f<V>> extends IVectorf<V> {

	Float getX();

	Float getY();

	V set(float x, float y);

	V add(float x, float y);

	V add(float x, float y, V cache);

	V subtract(float x, float y);

	V subtract(float x, float y, V cache);

	V multiply(float x, float y);

	V multiply(float x, float y, V cache);

	Float dot(float x, float y);

	// ---- Properties ---- //

	Float distanceSquared(float x, float y);

	Float distance(float x, float y);

	// -------------------- //

	Float cross(float x, float y);

	Float cross(V v);

}
