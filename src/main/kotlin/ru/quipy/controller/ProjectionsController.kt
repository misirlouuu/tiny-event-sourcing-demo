package ru.quipy.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.quipy.api.ProjectAggregate
import ru.quipy.core.EventSourcingService
import ru.quipy.logic.ProjectAggregateState
import ru.quipy.projections.service.ProjectionsService
import java.util.*

@RestController
@RequestMapping("/aggregate")
class ProjectionsController(
    var projectionsService: ProjectionsService
) {

}