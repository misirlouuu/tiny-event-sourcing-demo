package ru.quipy.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.quipy.api.ProjectAggregate
import ru.quipy.api.ProjectCreatedEvent
import ru.quipy.api.TaskCreatedEvent
import ru.quipy.api.TaskInfoUpdatedEvent
import ru.quipy.api.ParticipantAddedEvent
import ru.quipy.api.ParticipantDeletedEvent
import ru.quipy.api.AssigneeAddedEvent
import ru.quipy.api.AssigneeDeletedEvent
import ru.quipy.api.StatusCreatedEvent
import ru.quipy.api.StatusDeletedEvent
import ru.quipy.api.TaskStatusChangedEvent
import ru.quipy.core.EventSourcingService
import ru.quipy.logic.ProjectAggregateState
import ru.quipy.logic.addTask
import ru.quipy.logic.updateTaskInfo
import ru.quipy.logic.addParticipant
import ru.quipy.logic.deleteParticipant
import ru.quipy.logic.addAssignee
import ru.quipy.logic.deleteAssignee
import ru.quipy.logic.createStatus
import ru.quipy.logic.deleteStatus
import ru.quipy.logic.changeTaskStatus
import ru.quipy.logic.create
import java.util.*

@RestController
@RequestMapping("/projects")
class ProjectController(
    val projectEsService: EventSourcingService<UUID, ProjectAggregate, ProjectAggregateState>
) {

    @PostMapping("/{projectTitle}")
    fun createProject(@PathVariable projectTitle: String, @RequestParam creatorId: UUID) : ProjectCreatedEvent {
        return projectEsService.create { it.create(UUID.randomUUID(), projectTitle, creatorId) }
    }

    @GetMapping("/{projectId}")
    fun getProject(@PathVariable projectId: UUID) : ProjectAggregateState? {
        return projectEsService.getState(projectId)
    }

    @PostMapping("/{projectId}/tasks/{taskName}")
    fun createTask(@PathVariable projectId: UUID, @PathVariable taskName: String) : TaskCreatedEvent {
        return projectEsService.update(projectId) {
            it.addTask(UUID.randomUUID(), taskName)
        }
    }

    @PostMapping("/{projectId}/tasks/{taskId}/updateInfo")
    fun updateTaskInfo(@PathVariable projectId: UUID, @PathVariable taskId: UUID, @RequestParam taskName: String, @RequestParam taskBody: String) : TaskInfoUpdatedEvent {
        return projectEsService.update(projectId) {
            it.updateTaskInfo(taskId, taskName, taskBody)
        }
    }

    @PostMapping("/{projectId}/participants/{participantId}")
    fun addParticipant(@PathVariable projectId: UUID, @PathVariable participantId: UUID) : ParticipantAddedEvent {
        return projectEsService.update(projectId) {
            it.addParticipant(participantId)
        }
    }

    @DeleteMapping("/{projectId}/participants/{participantId}")
    fun deleteParticipant(@PathVariable projectId: UUID, @PathVariable participantId: UUID) : ParticipantDeletedEvent {
        return projectEsService.update(projectId) {
            it.deleteParticipant(participantId)
        }
    }

    @PostMapping("/{projectId}/tasks/{taskId}/assignees/{assigneeId}")
    fun addAssignee(@PathVariable projectId: UUID, @PathVariable taskId: UUID, @PathVariable assigneeId: UUID) : AssigneeAddedEvent {
        return projectEsService.update(projectId) {
            it.addAssignee(taskId, assigneeId)
        }
    }

    
    @DeleteMapping("/{projectId}/tasks/{taskId}/assignees/{assigneeId}")
    fun deleteAssignee(@PathVariable projectId: UUID, @PathVariable taskId: UUID, @PathVariable assigneeId: UUID) : AssigneeDeletedEvent {
        return projectEsService.update(projectId) {
            it.deleteAssignee(taskId, assigneeId)
        }
    }

    @PostMapping("/{projectId}/statuses/{statusName}")
    fun createStatus(@PathVariable projectId: UUID, @PathVariable statusName: String, @RequestParam statusColor: String) : StatusCreatedEvent {
        return projectEsService.update(projectId) {
            it.createStatus(statusName, statusColor)
        }
    }

    @DeleteMapping("/{projectId}/statuses/{statusName}")
    fun deleteStatus(@PathVariable projectId: UUID, @PathVariable statusName: String) : StatusDeletedEvent {
        return projectEsService.update(projectId) {
            it.deleteStatus(statusName)
        }
    }

    @PostMapping("/{projectId}/tasks/{taskId}/statuses/{statusName}") //color should be enum
    fun changeTaskStatus(@PathVariable projectId: UUID, @PathVariable taskId: UUID, @PathVariable statusName: String) : TaskStatusChangedEvent {
        return projectEsService.update(projectId) {
            it.changeTaskStatus(taskId, statusName)
        }
    }

}