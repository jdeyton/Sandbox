package com.bar.foo.math;

public interface IVector<V extends IVector<V, T>, T extends Number> {

	int n();

	int size();

	V set(V v);

	V add(V v);

	V add(V v, V cache);

	V subtract(V v);

	V subtract(V v, V cache);

	V multiply(V v);

	V multiply(V v, V cache);

	T dot(V v);
	// ---- Properties ---- //
	T lengthSquared();

	T length();

	V normalize();

	V normalize(V cache);

	V negate();

	V negate(V cache);

	T distanceSquared(V v);

	T distance(V v);

	// -------------------- //

	// ---- Equals/Hash ---- //
	boolean equals(Object object);

	int hashCode();
	// --------------------- //

}
