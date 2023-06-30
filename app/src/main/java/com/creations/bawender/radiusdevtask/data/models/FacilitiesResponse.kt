package com.creations.bawender.radiusdevtask.data.models

data class FacilitiesResponse(
    val exclusions: List<List<FacilityOptionKey>>,
    val facilities: List<Facility>
)

data class FacilityOptionKey(
    val facility_id: String,
    val options_id: String
)

data class Facility(
    val facility_id: String,
    val name: String,
    val options: List<Option>
)

data class Option(
    val icon: String,
    val id: String,
    val name: String
)