package com.hfad.mycosmetologist.domain.useCase.session

import com.hfad.mycosmetologist.domain.repository.SessionRepository
import com.hfad.mycosmetologist.domain.session.SessionState
import jakarta.inject.Inject
import kotlinx.coroutines.flow.mapNotNull

class ObserveAuthorizedWorkerId
@Inject
constructor(
    private val sessionRepository: SessionRepository,
) {
    operator fun invoke() =
        sessionRepository
            .observeSession()
            .mapNotNull { session ->
                (session as? SessionState.Authorized)?.workerId
            }
}
