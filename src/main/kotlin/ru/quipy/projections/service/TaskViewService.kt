package ru.quipy.projections.service

import org.springframework.stereotype.Service
import ru.quipy.api.*
import ru.quipy.projections.repository.StatusRepository
import ru.quipy.projections.repository.TaskRepository
import ru.quipy.projections.views.TaskView
import ru.quipy.streams.AggregateSubscriptionsManager
import javax.annotation.PostConstruct

@Service
class TaskViewService(
    private val taskRepository: TaskRepository,
    private val statusRepository: StatusRepository,
    private val subscriptionsManager: AggregateSubscriptionsManager
) {
    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(ProjectAggregate::class, "task-event-stream") {
            `when`(TaskCreatedEvent::class) { event ->
                createTask(event)
            }

//            `when`(TaskInfoUpdatedEvent::class) { event ->
//                updateTaskInfo(event)
//            }
//
//            `when`(AssigneeAddedEvent::class) { event ->
//                addAssignee(event)
//            }
//
//            `when`(AssigneeDeletedEvent::class) { event ->
//                deleteAssignee(event)
//            }
//
//            `when`(TaskStatusChangedEvent::class) { event ->
//                changeTaskStatus(event)
//            }
        }
    }

    private fun createTask(event: TaskCreatedEvent) {
        taskRepository.save(
            TaskView.Task(
                event.projectId,
                event.taskId,
                event.taskName,
                event.statusName,
                event.createdAt,
                mutableListOf()
            )
        )
    }


}