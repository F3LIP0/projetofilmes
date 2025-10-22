package com.example.projetofilmes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.projetofilmes.ui.MovieAppScreen
import com.example.projetofilmes.ui.MovieDetailScreen
import com.example.projetofilmes.ui.theme.ProjetoFilmesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjetoFilmesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "movie_list") {
                        composable("movie_list") {
                            MovieAppScreen(navController = navController)
                        }
                        composable(
                            "movie_detail/{movieId}",
                            arguments = listOf(navArgument("movieId") { type = NavType.StringType })
                        ) {
                            val movieId = it.arguments?.getString("movieId") ?: ""
                            MovieDetailScreen(movieId = movieId)
                        }
                    }
                }
            }
        }
    }
}
