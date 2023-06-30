package com.creations.bawender.radiusdevtask.presentation.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creations.bawender.radiusdevtask.data.models.FacilityOptionKey
import com.creations.bawender.radiusdevtask.data.models.Facility
import com.creations.bawender.radiusdevtask.data.repositories.FacilitiesRepository
import com.creations.bawender.radiusdevtask.infrastructure.annotations.Computed
import com.creations.bawender.radiusdevtask.infrastructure.annotations.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@State
data class MainState(
    val isLoading: Boolean = true,
    val error: Boolean = false,
    val facilities: List<Facility> = emptyList(),
    val exclusionMap: Map<FacilityOptionKey, FacilityOptionKey> = emptyMap(),
    val selectionMap: Map<String, String> = emptyMap(),
) {

    @Computed
    val disabled: List<FacilityOptionKey>
        get() = selectionMap
            .map { FacilityOptionKey(it.key, it.value) }
            .mapNotNull { exclusionMap[it] }

}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: FacilitiesRepository,
) : ViewModel() {

    var state by mutableStateOf(MainState())
        private set

    init {
        viewModelScope.launch {
            repo.getFacilities()
                .onSuccess { response ->
                    val map: MutableMap<FacilityOptionKey, FacilityOptionKey> = mutableMapOf()

                    response.exclusions.forEach { exclusions ->
                        map[exclusions[0]] = exclusions[1]
                    }

                    state = state.copy(
                        isLoading = false,
                        facilities = response.facilities,
                        exclusionMap = map,
                    )
                }
                .onFailure {
                    state = state.copy(isLoading = false, error = true)
                }
        }
    }

    fun selectOption(facilityId: String, optionId: String) {

        /*The same chip is selected again*/
        if (state.selectionMap[facilityId] == optionId) {
            val updated = state.selectionMap.toMutableMap()
            updated.remove(facilityId)
            state = state.copy(selectionMap = updated)
        } else {
            val updated = state.selectionMap.toMutableMap()
            updated[facilityId] = optionId

            /**
             * Case when we add a selection and an exclusion value is already added to the
             * selection map previously.
             */
            state.exclusionMap[FacilityOptionKey(facilityId, optionId)]?.let {
                if (updated[it.facility_id] == it.options_id) {
                    updated.remove(it.facility_id)
                }
            }

            state = state.copy(selectionMap = updated)
        }
    }
}
