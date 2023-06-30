package com.creations.bawender.radiusdevtask.data.entities

import com.creations.bawender.radiusdevtask.data.models.FacilityOptionKey
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import java.util.UUID

class ExclusionEntity : RealmObject {
    var exclusions = realmListOf<InternalExclusionEntity>()
}

class InternalExclusionEntity : RealmObject {
    var internalExclusions  = realmListOf<FacilityOptionEntity>()
}


class FacilityOptionEntity : RealmObject {

    @PrimaryKey
    var key: String = UUID.randomUUID().toString()

    var facility_id = ""
    var options_id = ""

    companion object {
        fun from(facilityOptionKey: FacilityOptionKey): FacilityOptionEntity {
            return FacilityOptionEntity()
                .apply {
                    facility_id = facilityOptionKey.facility_id
                    options_id = facilityOptionKey.options_id
                }
        }
    }
}