package ru.quipy.projections.service

import ru.quipy.api.ProjectAggregate
import ru.quipy.api.StatusCreatedEvent
import ru.quipy.api.StatusDeletedEvent
import ru.quipy.projections.repository.StatusRepository
import ru.quipy.projections.views.StatusView
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.*
import javax.annotation.PostConstruct

class StatusViewService(
    private val statusRepository: StatusRepository,
    private val subscriptionsManager: AggregateSubscriptionsManager
) {
    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(ProjectAggregate::class, "status-event-stream") {
            `when`(StatusCreatedEvent::class) { event ->
                createStatus(event)
            }

            `when`(StatusDeletedEvent::class) { event ->
                removeStatus(event)
            }
        }
    }

    private fun createStatus(event: StatusCreatedEvent) {
        statusRepository.save(
            StatusView.Status(
                event.statusId,
                event.projectId,
                event.statusName,
                event.statusColor,
                event.orderNumber
            )
        )
    }

    private fun removeStatus(event: StatusDeletedEvent) {
        val status = statusRepository.findByStatusName(event.statusName)
        if (status != null) {
            statusRepository.delete(status)
        }
    }

    fun getStatusesByProjectId(projectId: UUID): MutableIterable<StatusView.Status>? {
        return statusRepository.findAllByProjectId(projectId)
    }

    fun getStatusesByStatusColor(statusColor: String): MutableIterable<StatusView.Status>? {
        return statusRepository.findAllByStatusColor(statusColor)
    }
}