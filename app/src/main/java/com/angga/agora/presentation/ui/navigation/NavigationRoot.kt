package com.angga.agora.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.angga.agora.presentation.ui.login.LoginScreenRoot
import com.angga.agora.presentation.ui.register.RegisterScreenRoot

@Composable
fun NavigationRoot(
    navController: NavHostController,
    isLoggedIn: Boolean,
) {
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) "run" else Destination.Auth
    ) {
        authGraph(navController)
    }
}

private fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation<Destination.Auth>(
        startDestination = Destination.Login
    ) {
        composable<Destination.Register> {
            RegisterScreenRoot(
                onSignInClick = {
                    navController.navigate(Destination.Login) {
                        popUpTo(Destination.Register) {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                },
                onSuccessfulRegistration = {
                    navController.navigate(Destination.Login)
                }
            )
        }

        composable<Destination.Login> {
            LoginScreenRoot(
                onLoginSuccess = {

                },
                onSignUpClick = {
                    navController.navigate(Destination.Register) {
                        popUpTo(Destination.Register) {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                }
            )
        }
    }
}