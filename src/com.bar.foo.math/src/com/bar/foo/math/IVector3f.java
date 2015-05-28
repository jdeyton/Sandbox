package com.bar.foo.math;

public interface IVector3f<V extends IVector3f<V>> extends IVectorf<V> {

	Float getX();

	Float getY();

	Float getZ();

	V set(float x, float y, float z);

	V add(float x, float y, float z);

	V add(float x, float y, float z, V cache);

	V subtract(float x, float y, float z);

	V subtract(float x, float y, float z, V cache);

	V multiply(float x, float y, float z);

	V multiply(float x, float y, float z, V cache);

	Float dot(float x, float y, float z);

	// ---- Properties ---- //

	Float distanceSquared(float x, float y, float z);

	Float distance(float x, float y, float z);

	// -------------------- //

	V cross(float x, float y, float z);

	V cross(float x, float y, float z, V cache);

	V cross(V v);

	V cross(V v, V cache);
}
