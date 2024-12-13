package ru.quipy.projections.views

import ru.quipy.domain.Unique
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

class ProjectView {
    @Document("project-view")
    data class Project(
        @Id
        override val id: UUID,
        var name: String,
        var taskIds: MutableList<UUID> = mutableListOf(),
        var usersIds: MutableList<UUID> = mutableListOf(),
    ) : Unique<UUID>
}