package com.creations.bawender.radiusdevtask.presentation.compose.global_components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ChoiceChip(
    title: String,
    selected: Boolean = false,
    enabled: Boolean = true,
    icon: @Composable () -> Unit,
    onClick: () -> Unit
) {
    val content = @Composable {
        Row {
            icon()
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = title, style = MaterialTheme.typography.labelMedium)
        }
    }

    if (!selected)
        OutlinedButton(
            enabled = enabled,
            modifier = Modifier.padding(4.dp),
            onClick = onClick
        ) {
            content()
        }

    if (selected)
        Button(
            modifier = Modifier.padding(4.dp),
            onClick = onClick
        ) {
            content()
        }
}