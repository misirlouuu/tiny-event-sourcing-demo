package ru.quipy.projections.repository

import org.springframework.data.mongodb.repository.MongoRepository
import ru.quipy.projections.views.TaskView
import java.util.UUID

interface TaskRepository: MongoRepository<TaskView.Task, UUID> {
    fun findAllByProjectId(projectId: UUID): MutableIterable<TaskView.Task>?
}