package com.creations.bawender.radiusdevtask.data.repositories

import android.util.Log
import com.creations.bawender.radiusdevtask.data.data_source.LocalDataSource
import com.creations.bawender.radiusdevtask.data.models.FacilitiesResponse
import com.creations.bawender.radiusdevtask.infrastructure.network.FacilitiesApi
import javax.inject.Inject

class FacilitiesRepository @Inject constructor(
    private val remoteDataSource: FacilitiesApi,
    private val localDataSource: LocalDataSource,
) {

    suspend fun getFacilities(): Result<FacilitiesResponse> {
        val facilities = localDataSource.getAllFacilities()

        if (localDataSource.updateNeeded || facilities.isEmpty()) {
            return try {
                val response = remoteDataSource.getAllFacilities()
                localDataSource.save(response.facilities, response.exclusions)
                Result.success(response)
            } catch (e: Exception) {
                Log.e("FacilitiesRepository", e.toString())
                Result.failure(e)
            }
        } else {
            val exclusions = localDataSource.getAllExclusions()
            return Result.success(FacilitiesResponse(exclusions, facilities))
        }

    }

}