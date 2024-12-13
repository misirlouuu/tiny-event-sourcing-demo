package ru.quipy.projections.repository

import org.springframework.data.mongodb.repository.MongoRepository
import ru.quipy.projections.views.TaskView
import java.util.UUID

interface TaskRepository: MongoRepository<TaskView.Task, UUID> {
    fun findAllByProjectId(projectId: UUID): MutableIterable<TaskView.Task>?

//    fun findAllByStatusName(statusName: String): MutableIterable<TaskView.Task>?

    fun findAllByProjectIdAndStatusName(projectId: UUID, statusName: String): MutableIterable<TaskView.Task>?
}