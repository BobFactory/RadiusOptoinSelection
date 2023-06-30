package com.creations.bawender.radiusdevtask.presentation.screens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.creations.bawender.radiusdevtask.R
import com.creations.bawender.radiusdevtask.data.models.Facility
import com.creations.bawender.radiusdevtask.data.models.FacilityOptionKey
import com.creations.bawender.radiusdevtask.data.models.Option
import com.creations.bawender.radiusdevtask.presentation.compose.global_components.ChoiceChip
import com.creations.bawender.radiusdevtask.presentation.screens.MainViewModel


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FacilityCard(facility: Facility, onOptionSelected: (Option) -> Unit) {
    val vm: MainViewModel = hiltViewModel()
    val state = vm.state

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = facility.name,
                style = MaterialTheme.typography.titleMedium.copy(color = Color.White, letterSpacing = 0.8.sp)
            )
            FlowRow(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .padding(horizontal = 12.dp),
            ) {

                facility.options.forEach {

                    val isDisabled = state.disabled.contains(FacilityOptionKey(facility.facility_id, it.id))
                    val isSelected = state.selectionMap[facility.facility_id] == it.id
                    ChoiceChip(
                        title = it.name,
                        selected = isSelected && !isDisabled,
                        enabled = !isDisabled,
                        icon = {
                            Icon(
                                painter = painterResource(id = getIconForOption(it)),
                                contentDescription = it.name,
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        onClick = { onOptionSelected(it) }
                    )
                }
            }
        }
    }
}

private fun getIconForOption(option: Option): Int {
    return when (option.icon) {
        "apartment" -> R.drawable.apartment

        "condo" -> R.drawable.condo

        "boat" -> R.drawable.boat

        "land" -> R.drawable.land

        "rooms" -> R.drawable.rooms

        "no-room" -> R.drawable.no_room

        "swimming" -> R.drawable.swimming

        "garden" -> R.drawable.garden

        "garage" -> R.drawable.garage

        else -> R.drawable.no_room
    }
}

