package ru.quipy.projections.views

import ru.quipy.domain.Unique
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

class TaskView {
    @Document("task-view")
    data class Task(
        @Id
        override val id: UUID,
        var projectId: UUID,
        var taskName: String,
        var taskDescription: String,
        var statusName: String,
        var statusColor: String,
        var assigneeIds: MutableList<UUID> = mutableListOf(),
    ) : Unique<UUID>
}