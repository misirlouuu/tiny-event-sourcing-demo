package ru.quipy.projections.service

import org.springframework.stereotype.Service
import ru.quipy.api.UserAggregate
import ru.quipy.api.UserRegisteredEvent
import ru.quipy.logic.UserAggregateState
import ru.quipy.projections.repository.ProjectRepository
import ru.quipy.projections.repository.UserRepository
import ru.quipy.projections.views.ProjectView
import ru.quipy.projections.views.UserView
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.*
import javax.annotation.PostConstruct

@Service
class UserViewService(
    private val projectRepository: ProjectRepository,
    private val subscriptionsManager: AggregateSubscriptionsManager,
    private val userRepository: UserRepository
) {
    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(UserAggregate::class, "user-event-stream") {
            `when`(UserRegisteredEvent::class) { event ->
                register(event)
            }
        }
    }

    private fun register(event: UserRegisteredEvent) {
        userRepository.save(
            UserView.User(
                event.userId,
                event.nickname,
                event.userName,
                event.password,
                arrayListOf(),
                arrayListOf()
            )
        )
    }

    fun getProjectsByUserId(userId: UUID): MutableIterable<ProjectView.Project> {
        val user = userRepository.findById(userId).orElseThrow()
        return projectRepository.findAllById(user.projectsIds)
    }

    fun getUser(userName: String): UserView.User? {
        return userRepository.findByUserName(userName)
    }
}