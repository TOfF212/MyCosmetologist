package com.hfad.mycosmetologist.domain.session

sealed interface SessionState {
    data object Unauthorized : SessionState

    data class Authorized(
        val workerId: String,
    ) : SessionState
}
