package com.example.photomap.ui.navigation

sealed class Route(val path: String) {
    data object Map : Route("map")
    data object Details : Route("details/{id}") {
        fun create(id: Long) = "details/$id"
    }
}
