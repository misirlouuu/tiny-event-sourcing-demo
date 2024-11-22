package ru.quipy.api

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.*

const val USER_REGISTERED_EVENT = "USER_REGISTERED_EVENT"
const val USER_NAME_UPDATED_EVENT = "USER_NAME_UPDATED_EVENT"

// API
@DomainEvent(name = USER_REGISTERED_EVENT)
class UserRegisteredEvent( 
    val userId: UUID,
    val nickname: String,
    val email: String,
    val userName: String,
    val password: String,
    createdAt: Long = System.currentTimeMillis(),
) : Event<UserAggregate>(
    name = USER_REGISTERED_EVENT,
    createdAt = createdAt,
)

@DomainEvent(name = USER_NAME_UPDATED_EVENT)
class UserNameUpdatedEvent(
    val userId: UUID,
    val userName: String,
    createdAt: Long = System.currentTimeMillis(),
) : Event<UserAggregate>(
    name = USER_NAME_UPDATED_EVENT,
    createdAt = createdAt
)