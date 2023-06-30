package com.creations.bawender.radiusdevtask.data.data_source

import com.creations.bawender.radiusdevtask.data.entities.ExclusionEntity
import com.creations.bawender.radiusdevtask.data.entities.FacilityEntity
import com.creations.bawender.radiusdevtask.data.entities.FacilityOptionEntity
import com.creations.bawender.radiusdevtask.data.entities.InternalExclusionEntity
import com.creations.bawender.radiusdevtask.data.entities.OptionEntity
import com.creations.bawender.radiusdevtask.data.models.Facility
import com.creations.bawender.radiusdevtask.data.models.FacilityOptionKey
import com.creations.bawender.radiusdevtask.data.models.Option
import com.creations.bawender.radiusdevtask.infrastructure.preference.UserSharedPreference
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.toRealmList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.nio.file.Files.delete
import java.time.LocalDate
import javax.inject.Inject

/**
 * Similar to Dao class, handles local database interactions, but has a
 * generic name since there is only one usecase.
 */
class LocalDataSource @Inject constructor(
    private val realm: Realm,
    private val sharedPreference: UserSharedPreference
) {

    val updateNeeded: Boolean
        get() = sharedPreference.dateLastUpdate?.isBefore(LocalDate.now()) ?: true

    suspend fun getAllFacilities() = withContext(Dispatchers.IO) {
        realm.query(FacilityEntity::class)
            .find()
            .map { entity ->
                Facility(
                    facility_id = entity.facilityId,
                    name = entity.name,
                    options = entity.options.map {
                        Option(
                            icon = it.icon,
                            id = it.id,
                            name = it.name
                        )
                    }
                )
            }
            .toList()
    }

    suspend fun getAllExclusions() = withContext(Dispatchers.IO) {
        realm.query(ExclusionEntity::class)
            .first()
            .find()
            ?.exclusions
            ?.map {
                it.internalExclusions.map { FacilityOptionKey(it.facility_id, it.options_id) }
                    .toList()
            }
            ?.toList()
            ?: emptyList()
    }

    suspend fun save(
        facilities: List<Facility>,
        excl: List<List<FacilityOptionKey>>
    ) = withContext(Dispatchers.IO) {

        sharedPreference.dateLastUpdate = LocalDate.now()

        realm.writeBlocking {
            //Delete all elements first to replace them with new data
            val fEntities = query(FacilityEntity::class).find()
            delete(fEntities)

            val foEntity = query(FacilityOptionEntity::class).find()
            delete(foEntity)

            val oEntity = query(OptionEntity::class).find()
            delete(oEntity)

            val eEntities = query(ExclusionEntity::class).find()
            delete(eEntities)

            val ieEntity = query(InternalExclusionEntity::class).find()
            delete(ieEntity)

            facilities.forEach { facility ->
                copyToRealm(FacilityEntity.from(facility))
            }

            copyToRealm(
                ExclusionEntity().apply {
                    exclusions = excl.map { exclusionList ->
                        InternalExclusionEntity().apply {
                            internalExclusions = exclusionList.map { FacilityOptionEntity.from(it) }.toRealmList()
                        }
                    }.toRealmList()
                }
            )
        }

    }

}