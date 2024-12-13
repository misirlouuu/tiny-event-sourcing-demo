package ru.quipy.projections.service

import org.springframework.stereotype.Service
import ru.quipy.projections.views.ProjectView
import ru.quipy.projections.views.StatusView
import ru.quipy.projections.views.TaskView
import ru.quipy.projections.views.UserView
import java.util.*

@Service
class ProjectionsService(
    private val projectViewService: ProjectViewService,
    private val userViewService: UserViewService,
    private val taskViewService: TaskViewService,
    private val statusViewService: StatusViewService
) {
    fun getProjectById(projectId: UUID): ProjectView.Project {
        return projectViewService.getProjectById(projectId)
    }

    fun getProjectsByUserId(userId: UUID): MutableIterable<ProjectView.Project> {
        return userViewService.getProjectsByUserId(userId)
    }

//    fun getTasksById(taskId: UUID): TaskView.Task? {
//        return taskViewService.getTasksById(taskId)
//    }

//    fun getTasksByProjectId(projectId: UUID): MutableIterable<TaskView.Task>? {
//        return taskViewService.getTasksByProjectId(projectId)
//    }
//
//    fun getTasksByUserId(userId: UUID): List<TaskView.Task> {
//        return taskViewService.getTasksByUserId(userId)
//    }

    fun getUser(userName: String): UserView.User? {
        return userViewService.getUser(userName)
    }

    fun getStatusesByProjectId(projectId: UUID): MutableIterable<StatusView.Status>? {
        return statusViewService.getStatusesByProjectId(projectId)
    }
}