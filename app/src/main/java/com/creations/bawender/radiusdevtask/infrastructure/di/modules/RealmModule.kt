package com.creations.bawender.radiusdevtask.infrastructure.di.modules

import com.creations.bawender.radiusdevtask.data.entities.ExclusionEntity
import com.creations.bawender.radiusdevtask.data.entities.FacilityEntity
import com.creations.bawender.radiusdevtask.data.entities.FacilityOptionEntity
import com.creations.bawender.radiusdevtask.data.entities.InternalExclusionEntity
import com.creations.bawender.radiusdevtask.data.entities.OptionEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RealmModule {

    @Singleton
    @Provides
    fun providesRealmInstance(): Realm {
        val config = RealmConfiguration.Builder(
            schema = setOf(
                ExclusionEntity::class,
                InternalExclusionEntity::class,
                FacilityOptionEntity::class,
                FacilityEntity::class,
                OptionEntity::class
            )
        )
            .build()

        return Realm.open(config)
    }
}