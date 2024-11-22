package ru.quipy.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.quipy.api.UserAggregate
import ru.quipy.logic.UserAggregateState
import ru.quipy.api.UserRegisteredEvent
import ru.quipy.api.UserNameUpdatedEvent
import ru.quipy.core.EventSourcingService
import ru.quipy.logic.register
import ru.quipy.logic.updateName
import java.util.*

@RestController
@RequestMapping("/users")
class UserController(
    val userEsService: EventSourcingService<UUID, UserAggregate, UserAggregateState>
) {
    
    @PostMapping("/{nickname}")
    fun registerUser(@PathVariable nickname: String, @RequestParam email: String, @RequestParam userName: String, @RequestParam password: String) : UserRegisteredEvent {
        return userEsService.create { it.register(UUID.randomUUID(), nickname, email, userName, password) }
    }

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: UUID) : UserAggregateState? {
        return userEsService.getState(userId)
    }

    @PostMapping("/{userId}/updateName")
    fun updateUserName(@PathVariable userId: UUID, @RequestParam newUserName: String) : UserNameUpdatedEvent {
        return userEsService.update(userId) { 
            it.updateName(newUserName)
        }
    }
}