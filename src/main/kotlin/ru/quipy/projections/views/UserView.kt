package ru.quipy.projections.views

import ru.quipy.domain.Unique
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

class UserView{
    @Document("user-view")
    data class User(
        @Id
        override val id: UUID,
        var nickname: String,
        val userName: String,
        val password: String,
        val projectsIds: MutableList<UUID> = mutableListOf(),
        val tasksAssignedIds: MutableList<UUID> = mutableListOf()
    ) : Unique<UUID>
}