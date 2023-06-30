package com.creations.bawender.radiusdevtask.infrastructure.preference

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDate
import javax.inject.Inject

class UserSharedPreference @Inject constructor(
    @ApplicationContext context: Context,
) {

    private val prefs =
        context.getSharedPreferences("radius_shared_preference", Context.MODE_PRIVATE)


    var dateLastUpdate: LocalDate?
        get() {
            val value = prefs.getLong(KEY_LAST_UPDATE, -1L)
            return if (value == -1L) null
            else LocalDate.ofEpochDay(value)
        }
        set(value) {
            if (value == null) return
            prefs.edit().putLong(KEY_LAST_UPDATE, value.toEpochDay()).apply()
        }

    companion object {
        private const val KEY_LAST_UPDATE = "key_last_update"
    }
}