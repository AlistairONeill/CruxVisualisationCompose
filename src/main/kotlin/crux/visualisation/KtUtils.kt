package crux.visualisation

// Sometimes IntelliJ isn't smart enough to see that nested sealed whens are exhaustive. This forces it to
val Any.exhaustive get() = this

data class MutableDerivative<T>(val value: T, val set: (T) -> Unit)
