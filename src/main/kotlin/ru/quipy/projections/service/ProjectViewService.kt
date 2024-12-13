package ru.quipy.projections.service

import ru.quipy.api.ParticipantAddedEvent
import ru.quipy.api.ParticipantDeletedEvent
import ru.quipy.api.ProjectAggregate
import ru.quipy.api.ProjectCreatedEvent
import ru.quipy.projections.repository.ProjectRepository
import ru.quipy.projections.repository.UserRepository
import ru.quipy.projections.views.ProjectView
import ru.quipy.projections.views.StatusView
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.*
import javax.annotation.PostConstruct

class ProjectViewService(
    private val projectRepository: ProjectRepository,
    private val subscriptionsManager: AggregateSubscriptionsManager,
    private val userRepository: UserRepository
) {
    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(ProjectAggregate::class, "projects-event-stream") {
            `when`(ProjectCreatedEvent::class) { event ->
                createProject(event)
            }

            `when`(ParticipantAddedEvent::class) { event ->
                addParticipant(event)
            }

            `when`(ParticipantDeletedEvent::class) { event ->
                deleteParticipant(event)
            }
        }
    }

    private fun createProject(event: ProjectCreatedEvent) {
        projectRepository.save(
            ProjectView.Project(
                event.projectId,
                event.title
            )
        )
    }

    private fun addParticipant(event: ParticipantAddedEvent) {
        val user = userRepository.findById(event.participantId).orElseThrow()
        var project = projectRepository.findById(event.projectId).orElseThrow()
        user.projectsIds.add(event.projectId)
        userRepository.save(user)
    }

    private fun deleteParticipant(event: ParticipantDeletedEvent) {
        val user = userRepository.findById(event.participantId).orElseThrow()
        if (user != null) {
            userRepository.delete(user)
        }
    }

    fun getProjectById(projectId: UUID): ProjectView.Project {
        return projectRepository.findById(projectId).orElseThrow()
    }
}