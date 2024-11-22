package ru.quipy.logic

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
import java.util.*


// Commands : takes something -> returns event
// Here the commands are represented by extension functions, but also can be the class member functions

fun ProjectAggregateState.create(id: UUID, title: String, creatorId: UUID): ProjectCreatedEvent {
    return ProjectCreatedEvent(
        projectId = id,
        title = title,
        creatorId = creatorId,
    )
}

fun ProjectAggregateState.addTask(taskId: UUID, name: String): TaskCreatedEvent {
    return TaskCreatedEvent(projectId = this.getId(), taskId = taskId, taskName = name)
}

fun ProjectAggregateState.updateTaskInfo(taskId: UUID, taskName: String, taskBody: String): TaskInfoUpdatedEvent {
    if (this.tasks.find { task -> task.id == taskId } == null) {
        throw IllegalArgumentException("No task with id: $taskId in project")
    }
    return TaskInfoUpdatedEvent(projectId = this.getId(), taskId = taskId, taskName = taskName, taskBody = taskBody)
}

fun ProjectAggregateState.addParticipant(participantId: UUID): ParticipantAddedEvent {
    if (this.participants.contains(participantId)) {
        throw IllegalArgumentException("Participant with id: $participantId already in project")
    }
    return ParticipantAddedEvent(projectId = this.getId(), participantId = participantId)
}

fun ProjectAggregateState.deleteParticipant(participantId: UUID): ParticipantDeletedEvent {
    if (!this.participants.contains(participantId)) {
        throw IllegalArgumentException("No participant with id: $participantId in project")
    }
    return ParticipantDeletedEvent(projectId = this.getId(), participantId = participantId)
}

fun ProjectAggregateState.addAssignee(taskId: UUID, assigneeId: UUID): AssigneeAddedEvent {
    val updatedTask = this.tasks.find {task -> task.id == taskId}
    if (updatedTask == null) {
        throw IllegalArgumentException("No task with id: $taskId in project")
    }
    if (updatedTask.assignees.contains(assigneeId)){
        throw IllegalArgumentException("Assignee with id: $assigneeId already assigned to task")
    }
    if (!this.participants.contains(assigneeId)){
        throw IllegalArgumentException("Assignee is not in the list of project participants")
    }
    return AssigneeAddedEvent(projectId = this.getId(), taskId = taskId, assigneeId = assigneeId)
}

fun ProjectAggregateState.deleteAssignee(taskId: UUID, assigneeId: UUID): AssigneeDeletedEvent {
    val updatedTask = this.tasks.find {task -> task.id == taskId}
    if (updatedTask == null) {
        throw IllegalArgumentException("No task with id: $taskId in project")
    }
    if (!updatedTask.assignees.contains(assigneeId)){
        throw IllegalArgumentException("No assignee with id: $assigneeId in task")
    }
    return AssigneeDeletedEvent(projectId = this.getId(), taskId = taskId, assigneeId = assigneeId)
}

fun ProjectAggregateState.createStatus(statusName: String, statusColor: String): StatusCreatedEvent {
    if (this.statuses.containsKey(statusName)){
        throw IllegalArgumentException("Status with name $statusName already exists")
    }
    return StatusCreatedEvent(projectId = this.getId(), statusName = statusName, statusColor = statusColor)
}

fun ProjectAggregateState.deleteStatus(statusName: String): StatusDeletedEvent {
    if (!this.statuses.containsKey(statusName)){
        throw IllegalArgumentException("No status with name $statusName in project")
    }
    if (statusName == "CREATED") {
        throw IllegalArgumentException("Default status can't be deleted")
    }
    return StatusDeletedEvent(projectId = this.getId(), statusName = statusName)
}

fun ProjectAggregateState.changeTaskStatus(taskId: UUID, statusName: String): TaskStatusChangedEvent {
    if (!this.statuses.containsKey(statusName)){
        throw IllegalArgumentException("No status with name $statusName in project")
    }
    if (this.tasks.find { task -> task.id == taskId } == null) {
        throw IllegalArgumentException("No task with id: $taskId in project")
    }
    return TaskStatusChangedEvent(projectId = this.getId(), taskId = taskId, statusName = statusName)
}
