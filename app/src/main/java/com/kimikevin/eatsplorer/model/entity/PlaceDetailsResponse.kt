package com.kimikevin.eatsplorer.model.entity

data class PlaceDetailsResponse(
    val nationalPhoneNumber: String?,
    val websiteUri: String?,
    val regularOpeningHours: OpeningHours?
) {
    val isOpenNow: Boolean get() = regularOpeningHours?.openNow ?: false

    data class OpeningHours(
        val openNow: Boolean,
        val weekdayDescriptions: List<String>?
    )
}
