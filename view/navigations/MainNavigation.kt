package com.ozpehlivantugrul.passmanapp.view.navigations

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ozpehlivantugrul.passmanapp.utils.RouteNames
import com.ozpehlivantugrul.passmanapp.view.main.GeneratePasswordView
import com.ozpehlivantugrul.passmanapp.view.main.HomeView
import com.ozpehlivantugrul.passmanapp.view.main.SettingsView
import com.ozpehlivantugrul.passmanapp.view.main.detail.CredentialDetailView

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = RouteNames.home
    ) {
        composable(RouteNames.home) { HomeView(navController = navController) }
        composable(
            route = RouteNames.detail,
            arguments = listOf(navArgument("credentialId") { type = NavType.StringType })
        ) { backStackEntry ->
            val credentialId = backStackEntry.arguments?.getString("credentialId")
            CredentialDetailView(navController = navController, credentialId = credentialId ?: "")
        }
        composable(RouteNames.generatePassword) { GeneratePasswordView(navController = navController) }
        composable(RouteNames.settings) { SettingsView(navController = navController) }

    }
}