package ru.quipy.api

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.*

const val PROJECT_CREATED_EVENT = "PROJECT_CREATED_EVENT"
const val TASK_CREATED_EVENT = "TASK_CREATED_EVENT"
const val TASK_INFO_UPDATED_EVENT = "TASK_INFO_UPDATED_EVENT"
const val PARTICIPANT_ADDED_EVENT = "PARTICIPANT_ADDED_EVENT"
const val PARTICIPANT_DELETED_EVENT = "PARTICIPANT_DELETED_EVENT"
const val ASSIGNEE_ADDED_EVENT = "ASSIGNEE_ADDED_EVENT"
const val ASSIGNEE_DELETED_EVENT = "ASSIGNEE_DELETED_EVENT"
const val STATUS_CREATED_EVENT = "STATUS_CREATED_EVENT"
const val STATUS_DELETED_EVENT = "STATUS_DELETED_EVENT"
const val TASK_STATUS_CHANGED_EVENT = "TASK_STATUS_CHANGED_EVENT"

// API
@DomainEvent(name = PROJECT_CREATED_EVENT)
class ProjectCreatedEvent(
    val projectId: UUID,
    val title: String,
    val creatorId: UUID,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = PROJECT_CREATED_EVENT,
    createdAt = createdAt,
)

@DomainEvent(name = TASK_CREATED_EVENT)
class TaskCreatedEvent(
    val projectId: UUID,
    val taskId: UUID,
    val taskName: String,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = TASK_CREATED_EVENT,
    createdAt = createdAt
)

@DomainEvent(name = TASK_INFO_UPDATED_EVENT)
class TaskInfoUpdatedEvent(
    val projectId: UUID,
    val taskId: UUID,
    val taskName: String,
    val taskBody: String,
    createdAt: Long = System.currentTimeMillis(),
): Event<ProjectAggregate>(
    name = TASK_INFO_UPDATED_EVENT,
    createdAt = createdAt
)

@DomainEvent(name = PARTICIPANT_ADDED_EVENT)
class ParticipantAddedEvent(
    val projectId: UUID,
    val participantId: UUID,
    createdAt: Long = System.currentTimeMillis(),
): Event<ProjectAggregate>(
    name = PARTICIPANT_ADDED_EVENT,
    createdAt = createdAt
)

@DomainEvent(name = PARTICIPANT_DELETED_EVENT)
class ParticipantDeletedEvent(
    val projectId: UUID,
    val participantId: UUID,
    createdAt: Long = System.currentTimeMillis(),
): Event<ProjectAggregate>(
    name = PARTICIPANT_DELETED_EVENT,
    createdAt = createdAt
)

@DomainEvent(name = ASSIGNEE_ADDED_EVENT)
class AssigneeAddedEvent(
    val projectId: UUID,
    val taskId: UUID,
    val assigneeId: UUID,
    createdAt: Long = System.currentTimeMillis(),
): Event<ProjectAggregate>(
    name = ASSIGNEE_ADDED_EVENT,
    createdAt = createdAt
)

@DomainEvent(name = ASSIGNEE_DELETED_EVENT)
class AssigneeDeletedEvent(
    val projectId: UUID,
    val taskId: UUID,
    val assigneeId: UUID,
    createdAt: Long = System.currentTimeMillis(),
): Event<ProjectAggregate>(
    name = ASSIGNEE_DELETED_EVENT,
    createdAt = createdAt
)

@DomainEvent(name = STATUS_CREATED_EVENT)
class StatusCreatedEvent(
    val projectId: UUID,
    val statusName: String,
    val statusColor: String,
    createdAt: Long = System.currentTimeMillis(),
): Event<ProjectAggregate>(
    name =STATUS_CREATED_EVENT,
    createdAt = createdAt
)

@DomainEvent(name = STATUS_DELETED_EVENT)
class StatusDeletedEvent(
    val projectId: UUID,
    val statusName: String,
    createdAt: Long = System.currentTimeMillis(),
): Event<ProjectAggregate>(
    name =STATUS_DELETED_EVENT,
    createdAt = createdAt
)

@DomainEvent(name = TASK_STATUS_CHANGED_EVENT)
class TaskStatusChangedEvent(
    val projectId: UUID,
    val taskId: UUID,
    val statusName: String,
    createdAt: Long = System.currentTimeMillis(),
): Event<ProjectAggregate>(
    name = TASK_STATUS_CHANGED_EVENT,
    createdAt = createdAt
)