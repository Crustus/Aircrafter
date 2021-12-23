package cz.crusty.aircrafter.repository.remote

sealed class Result<out RESPONSE> {
    data class Success<out RESPONSE>(val body: RESPONSE) : Result<RESPONSE>()

    data class Error(val error: Throwable?) : Result<Nothing>() {
        constructor(code: Int, msg: String) : this(null)
    }
}
