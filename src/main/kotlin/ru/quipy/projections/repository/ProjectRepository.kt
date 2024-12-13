package ru.quipy.projections.repository

import org.springframework.data.mongodb.repository.MongoRepository
import ru.quipy.projections.views.ProjectView
import java.util.*

interface ProjectRepository : MongoRepository<ProjectView.Project, UUID> {
}