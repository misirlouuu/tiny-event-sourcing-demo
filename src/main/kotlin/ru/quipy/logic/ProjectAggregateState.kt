package ru.quipy.logic

import ru.quipy.api.*
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.util.*

// Service's business logic
class ProjectAggregateState : AggregateState<UUID, ProjectAggregate> {
    private lateinit var projectId: UUID
    private var defaultStatus: Status = Status("CREATED", Color.GREY)
    var createdAt: Long = System.currentTimeMillis()
    var updatedAt: Long = System.currentTimeMillis()

    lateinit var projectTitle: String
    lateinit var creatorId: UUID
    var tasks = mutableListOf<TaskEntity>()
    var statuses = mutableMapOf<String, Status>("CREATED" to defaultStatus)
    var participants = mutableListOf<UUID>()

    override fun getId() = projectId

    // State transition functions which is represented by the class member function
    @StateTransitionFunc
    fun projectCreatedApply(event: ProjectCreatedEvent) {
        projectId = event.projectId
        projectTitle = event.title
        creatorId = event.creatorId
        participants.add(event.creatorId)

        createdAt = event.createdAt
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun taskCreatedApply(event: TaskCreatedEvent) {
        tasks.add(TaskEntity(event.taskId, event.taskName, "", defaultStatus, mutableListOf()))
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun taskInfoUpdatedApply(event: TaskInfoUpdatedEvent) {
        val updatedTask = tasks.find { task -> task.id == event.taskId }
        if (updatedTask != null) {
            updatedTask.name = event.taskName
            updatedTask.body = event.taskBody
            updatedAt = event.createdAt
        }
    }

    @StateTransitionFunc
    fun participantAddedApply(event: ParticipantAddedEvent) {
        participants.add(event.participantId)
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun participantDeletedApply(event: ParticipantDeletedEvent) {
        participants.remove(event.participantId)
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun assigneeAddedApply(event: AssigneeAddedEvent) {
        val updatedTask = tasks.find { task -> task.id == event.taskId }
        if (updatedTask != null) {
            updatedTask.assignees.add(event.assigneeId)
            updatedAt = event.createdAt
        }
    }

    @StateTransitionFunc
    fun assigneeDeletedApply(event: AssigneeDeletedEvent) {
        val updatedTask = tasks.find { task -> task.id == event.taskId }
        if (updatedTask != null) {
            updatedTask.assignees.remove(event.assigneeId)
            updatedAt = event.createdAt
        }
    }

    @StateTransitionFunc
    fun statusCreatedApply(event: StatusCreatedEvent) {
        statuses[event.statusName] = Status(event.statusName, Color.valueOf(event.statusColor))
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun statusDeletedApply(event: StatusDeletedEvent) {
        statuses.remove(event.statusName)
        updatedAt = event.createdAt
    }


    @StateTransitionFunc
    fun taskStatusChangedApply(event: TaskStatusChangedEvent) {
        val updatedTask = tasks.find { task -> task.id == event.taskId }
        if (updatedTask != null) {
            val newStatus = statuses[event.statusName]
            if (newStatus != null) {
                updatedTask.status = newStatus
                updatedAt = event.createdAt
            }
        }
    }
   
}

data class TaskEntity(
    val id: UUID,
    var name: String,
    var body: String,
    var status: Status,
    var assignees: MutableList<UUID>
)

enum class Color {
    GREY, BLUE, GREEN, YELLOW, ORANGE, RED
}

data class Status(
    val name: String,
    val color: Color
)
