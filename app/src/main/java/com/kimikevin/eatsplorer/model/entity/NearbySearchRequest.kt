package com.kimikevin.eatsplorer.model.entity

import com.google.gson.annotations.SerializedName

data class NearbySearchRequest(
    @SerializedName("includedTypes")
    val includedTypes: Array<String> = arrayOf("restaurant"),
    @SerializedName("maxResultCount")
    val maxResultCount: Int = 20,
    val locationRestriction: LocationRestriction
) {
    constructor(latitude: Double, longitude: Double, radiusMeters: Double) : this(
        locationRestriction = LocationRestriction(
            circle = Circle(
                center = Center(latitude, longitude),
                radius = radiusMeters
            )
        )
    )

    data class LocationRestriction(
        @SerializedName("circle")
        val circle: Circle
    )

    data class Circle(
        @SerializedName("center")
        val center: Center,
        @SerializedName("radius")
        val radius: Double
    )

    data class Center(
        val latitude: Double,
        val longitude: Double
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as NearbySearchRequest
        if (!includedTypes.contentEquals(other.includedTypes)) return false
        return true
    }

    override fun hashCode(): Int {
        return includedTypes.contentHashCode()
    }
}
