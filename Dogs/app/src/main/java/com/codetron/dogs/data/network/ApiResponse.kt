package com.codetron.dogs.data.network

enum class StatusResponse {
    SUCCESS,
    EMPTY,
    ERROR
}

class ApiResponse<T> private constructor(
    val status: StatusResponse,
    val body: T?,
    val error: Throwable?
) {
    companion object {
        fun <T> success(body: T): ApiResponse<T> {
            return ApiResponse(StatusResponse.SUCCESS, body, null)
        }

        fun <T> empty(): ApiResponse<T> {
            return ApiResponse(StatusResponse.EMPTY, null, null)
        }

        fun <T> error(error: Throwable): ApiResponse<T> {
            return ApiResponse(StatusResponse.ERROR, null, error)
        }
    }
}