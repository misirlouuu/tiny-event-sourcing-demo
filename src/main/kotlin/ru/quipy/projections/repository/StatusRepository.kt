package ru.quipy.projections.repository

import org.springframework.data.mongodb.repository.MongoRepository
import ru.quipy.projections.views.StatusView
import java.util.UUID

interface StatusRepository : MongoRepository<StatusView.Status, UUID> {
    fun findAllByStatusName(statusName: String): MutableIterable<StatusView.Status>?

    fun findAllByStatusColor(statusColor: String): MutableIterable<StatusView.Status>?

    fun findAllByProjectId(projectId: UUID): MutableIterable<StatusView.Status>?
}