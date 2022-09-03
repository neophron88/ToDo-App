package org.rasulov.todoapp.app.domain.entities

/**
*Note, the order of the items should not change, keep like this [NONE, LOW, MEDIUM, HIGH]
*because many places of the code depend on this order
**/
enum class Priority {
    NONE, LOW, MEDIUM, HIGH
}