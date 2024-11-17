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
    val userEsService: EventSourcingService<String, UserAggregate, UserAggregateState>
) {
    
    @PostMapping("/{nickname}")
    fun registerUser(@PathVariable nickname: String, @RequestParam email: String, @RequestParam userName: String, @RequestParam password: String) : UserRegisteredEvent {
        return userEsService.create { it.register(nickname, email, userName, password) }
    }

    @GetMapping("/{nickname}") //мб nickname
    fun getUser(@PathVariable nickname: String) : UserAggregateState? {
        return userEsService.getState(nickname)
    }

    @PostMapping("/{nickname}/updateName")
    fun updateUserName(@PathVariable nickname: String, @RequestParam userName: String) : UserNameUpdatedEvent {
        return userEsService.update(nickname) { //вот это че за методы вообще
            it.updateName(userName)
        }
    }
}