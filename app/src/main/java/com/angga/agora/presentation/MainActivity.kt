package com.angga.agora.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.angga.agora.presentation.ui.components.AgoraMenuItem
import com.angga.agora.presentation.ui.navigation.BottomNavigation
import com.angga.agora.presentation.ui.navigation.Destination
import com.angga.agora.presentation.ui.navigation.NavigationRoot
import com.angga.agora.presentation.ui.theme.AgoraTheme
import com.angga.agora.presentation.ui.utils.Constant
import dagger.hilt.android.AndroidEntryPoint
import io.agora.rtc2.Constants

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AgoraTheme {
                val navController = rememberNavController()

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                    ?: BottomNavigation.HOME::class.qualifiedName.orEmpty()

                val isBottomAppBarVisible = rememberSaveable(navBackStackEntry) {
                    navBackStackEntry?.destination?.route == Destination.Home::class.qualifiedName ||
                            navBackStackEntry?.destination?.route == Destination.Video::class.qualifiedName ||
                            navBackStackEntry?.destination?.route == Destination.Chat::class.qualifiedName ||
                    navBackStackEntry?.destination?.route == Destination.Account::class.qualifiedName
                }

                val isLiveSelected = rememberSaveable(navBackStackEntry) {
                    navBackStackEntry?.destination?.route == Destination.Live::class.qualifiedName
                }


                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding(),
                    bottomBar = {
                        if (isBottomAppBarVisible) {
                            NavigationBar(
                                modifier = Modifier
                                    .padding(24.dp)
                                    .background(MaterialTheme.colorScheme.background, CircleShape),
                                containerColor = Color.Transparent
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(4.dp)
                                        .clip(RoundedCornerShape(34.dp)),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    BottomNavigation.entries
                                        .forEachIndexed { index, bottomNavigation ->
                                            val isSelected by remember(currentRoute) {
                                                derivedStateOf {
                                                    currentRoute == bottomNavigation.route::class.qualifiedName
                                                }
                                            }
                                            AgoraMenuItem(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .align(Alignment.CenterVertically),
                                                label = bottomNavigation.label,
                                                drawableRes = bottomNavigation.icon,
                                                isSelected = isSelected,
                                                onClick = {
                                                    println("=== ${bottomNavigation.route}")
                                                    if (bottomNavigation.label == "") {
                                                        println("=== true")
                                                        navController.navigate(Destination.Live(roleType = Constants.CLIENT_ROLE_BROADCASTER)) {
                                                            popUpTo(navController.graph.findStartDestination().id)
                                                            launchSingleTop = true
                                                        }
                                                    } else {
                                                        println("=== false")
                                                        navController.navigate(bottomNavigation.route) {
                                                            popUpTo(navController.graph.findStartDestination().id)
                                                            launchSingleTop = true
                                                        }
                                                    }
                                                }
                                            )
                                        }
                                }
                            }
                        }
                    }
                ) { _ ->
                    NavigationRoot(
                        navController = navController,
                        isLoggedIn = viewModel.state.isLoggedIn,
                        onJoinClick = {
                            Destination.Live::class.qualifiedName?.let {
                                navController.navigate(Destination.Live(roleType = Constants.CLIENT_ROLE_AUDIENCE)) {
                                    popUpTo(navController.graph.findStartDestination().id)
                                    launchSingleTop = true
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

