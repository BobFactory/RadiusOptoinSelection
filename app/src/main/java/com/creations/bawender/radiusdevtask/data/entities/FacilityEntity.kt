package com.creations.bawender.radiusdevtask.data.entities

import com.creations.bawender.radiusdevtask.data.models.Facility
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class FacilityEntity : RealmObject {
    @PrimaryKey
    var facilityId = ""

    var name = ""

    var options: RealmList<OptionEntity> = realmListOf()

    companion object {
        fun from(facility: Facility): FacilityEntity {
            return  FacilityEntity().apply {
                facilityId = facility.facility_id
                name = facility.name
                options = realmListOf<OptionEntity>().also {
                    val options = facility.options.map { option ->
                        OptionEntity().apply {
                            id = option.id
                            name = option.name
                            icon = option.icon
                        }
                    }
                    it.addAll(options)
                }
            }
        }
    }

}

class OptionEntity : RealmObject {

    @PrimaryKey
    var id = ""

    var icon = ""

    var name = ""
}