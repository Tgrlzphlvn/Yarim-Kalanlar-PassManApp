package com.ozpehlivantugrul.passmanapp.view.navigations

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ozpehlivantugrul.passmanapp.utils.RouteNames
import com.ozpehlivantugrul.passmanapp.view.login.EntranceView
import com.ozpehlivantugrul.passmanapp.view.login.WelcomeView
import com.ozpehlivantugrul.passmanapp.view.main.HomeView
import com.ozpehlivantugrul.passmanapp.viewModel.EntranceViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginNavigation(viewModel: EntranceViewModel = hiltViewModel()) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = if (viewModel.getLoginState()) RouteNames.mainNav else RouteNames.welcome
    ) {
        composable(RouteNames.welcome) { WelcomeView(navController = navController) }
        composable(RouteNames.entrance) { EntranceView(navController = navController) }
        composable(RouteNames.mainNav) { MainNavigation() }
    }
}