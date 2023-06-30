package com.creations.bawender.radiusdevtask.infrastructure.network

import com.creations.bawender.radiusdevtask.data.models.FacilitiesResponse
import retrofit2.http.GET

interface FacilitiesApi {

    @GET("ad-assignment/db")
    suspend fun getAllFacilities(): FacilitiesResponse
}