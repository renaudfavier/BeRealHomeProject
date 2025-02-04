package com.example.snapquest.core.domain

typealias DomainError = Error

sealed interface Result<out D, out E: Error> {
    data class Success<out D>(val data: D): Result<D, Nothing>
    data class Error<out E: DomainError>(val error: E): Result<Nothing, E>
}

inline fun <T, E: Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when(this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }
}

fun <T, E: Error> Result<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map {  }
}

inline fun <T, E: Error> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    return when(this) {
        is Result.Error -> this
        is Result.Success -> {
            action(data)
            this
        }
    }
}
inline fun <T, E: Error> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> {
    return when(this) {
        is Result.Error -> {
            action(error)
            this
        }
        is Result.Success -> this
    }
}

inline fun <T1, T2, E1: Error, E2: Error> Result<T1, E1>.combine(result: Result<T2, E2>): Result<Pair<T1, T2>, Error> {
    return when(this) {
        is Result.Error -> {
            this
        }
        is Result.Success -> {
            val data2 = (result as? Result.Success)?.data ?: return (result as Result.Error)
            Result.Success(Pair(data, data2))
        }
    }
}

typealias EmptyResult<E> = Result<Unit, E>
