package ru.quipy.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.quipy.api.ProjectAggregate
import ru.quipy.core.EventSourcingService
import ru.quipy.logic.ProjectAggregateState
import ru.quipy.projections.service.ProjectionsService
import ru.quipy.projections.views.ProjectView
import ru.quipy.projections.views.StatusView
import ru.quipy.projections.views.TaskView
import ru.quipy.projections.views.UserView
import java.util.*

@RestController
@RequestMapping("/aggregate")
class ProjectionsController(
    var projectionsService: ProjectionsService
) {
    @GetMapping("/project/{projectId}")
    fun getProjectsById(@PathVariable projectId: UUID): ProjectView.Project {
        return projectionsService.getProjectById(projectId)
    }

    @GetMapping("/projects-by-user-id/{userId}")
    fun getProjectsByUserId(@PathVariable userId: UUID): MutableIterable<ProjectView.Project> {
        return projectionsService.getProjectsByUserId(userId)
    }

//    @GetMapping("/task/{taskId}")
//    fun getTasksById(@PathVariable taskId: UUID): TaskView.Task? {
//        return projectionsService.getTasksById(taskId)
//    }
//
//    @GetMapping("/tasks-by-project-id/{projectId}")
//    fun getTasksByProjectId(@PathVariable projectId: UUID): MutableIterable<TaskView.Task>? {
//        return projectionsService.getTasksByProjectId(projectId)
//    }
//
//    @GetMapping("/tasks-by-user-id/{userId}")
//    fun getTasksByUserId(@PathVariable userId: UUID): List<TaskView.Task>? {
//        return projectionsService.getTasksByUserId(userId)
//    }

    @GetMapping("/user/{userName}")
    fun getUserInfo(@PathVariable userName: String): UserView.User? {
        return projectionsService.getUser(userName)
    }

    @GetMapping("/statuses/{projectId}")
    fun getStatusesByProjectId(@PathVariable projectId: UUID): MutableIterable<StatusView.Status>? {
        return projectionsService.getStatusesByProjectId(projectId)
    }

}