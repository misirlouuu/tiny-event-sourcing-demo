package ru.quipy.projections.repository

import org.springframework.data.mongodb.repository.MongoRepository
import ru.quipy.projections.views.UserView
import java.util.UUID

interface UserRepository: MongoRepository<UserView.User, UUID> {
    fun findByUserName(userName: String): UserView.User?
}