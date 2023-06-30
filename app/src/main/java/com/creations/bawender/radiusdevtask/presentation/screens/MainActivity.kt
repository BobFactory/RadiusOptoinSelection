@file:OptIn(ExperimentalMaterial3Api::class)

package com.creations.bawender.radiusdevtask.presentation.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.creations.bawender.radiusdevtask.R
import com.creations.bawender.radiusdevtask.data.models.Facility
import com.creations.bawender.radiusdevtask.presentation.screens.components.FacilityCard
import com.creations.bawender.radiusdevtask.presentation.compose.theme.RadiusDevTaskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RadiusDevTaskTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                modifier = Modifier.drawBehind {
                                    drawLine(
                                        color = Color.LightGray,
                                        start = Offset(0f, size.height),
                                        end = Offset(size.width, size.height),
                                    )
                                },
                                title = {
                                    Text(
                                        text = stringResource(R.string.app_name),
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            letterSpacing = 1.5.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    )
                                }
                            )
                        }
                    ) { paddingValues ->

                        val vm: MainViewModel = hiltViewModel()
                        val state = vm.state

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues), contentAlignment = Alignment.Center
                        ) {
                            AnimatedVisibility(
                                visible = !state.isLoading && !state.error,
                                enter = fadeIn(),
                                exit = fadeOut()
                            ) {
                                MainView()
                            }

                            AnimatedVisibility(
                                visible = state.isLoading,
                                enter = fadeIn(),
                                exit = fadeOut()
                            ) {
                                CircularProgressIndicator()
                            }

                            AnimatedVisibility(
                                visible = !state.isLoading && state.error,
                                enter = fadeIn(),
                                exit = fadeOut()
                            ) {
                                Text(
                                    modifier = Modifier.padding(horizontal = 32.dp),
                                    text = getString(R.string.msg_internet_failure),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun MainView() {
    val vm: MainViewModel = hiltViewModel()
    val state = vm.state

    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        item {
            Text(
                text = stringResource(R.string.title_property_specification),
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.8.sp,
                    color = Color.LightGray

                )
            )
            Spacer(modifier = Modifier.height(20.dp))
        }

        items(state.facilities) { facility: Facility ->
            FacilityCard(
                facility = facility,
                onOptionSelected = {
                    vm.selectOption(facility.facility_id, it.id)
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

