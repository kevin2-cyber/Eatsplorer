package com.kimikevin.eatsplorer.model.entity

import java.io.Serializable

data class Restaurant(
    val id: String,
    val name: String,
    val category: String,
    val photoRef: String?,
    val rating: Double,
    val address: String,
    val latitude: Double,
    val longitude: Double
) : Serializable
