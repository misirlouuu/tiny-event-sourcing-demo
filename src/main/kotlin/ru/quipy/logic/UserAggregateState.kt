package ru.quipy.logic

import ru.quipy.api.*
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.util.*

// Service's business logic
class UserAggregateState : AggregateState<UUID, UserAggregate> {
    private lateinit var userId: UUID
    lateinit var nickname: String
    var createdAt: Long = System.currentTimeMillis()
    var updatedAt: Long = System.currentTimeMillis()

    lateinit var email: String
    lateinit var userName: String
    private lateinit var password: String 

    override fun getId() = userId

    // State transition functions which is represented by the class member function
    @StateTransitionFunc
    fun userRegisteredApply(event: UserRegisteredEvent) {
        userId = event.userId
        nickname = event.nickname
        email = event.email
        userName = event.userName
        password = event.password
        createdAt = event.createdAt
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun userNameUpdatedApply(event: UserNameUpdatedEvent) {
        userName = event.userName
        updatedAt = event.createdAt
    }
}
