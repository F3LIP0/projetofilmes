package com.example.projetofilmes.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.projetofilmes.data.MovieDetail
import com.example.projetofilmes.viewmodel.MovieViewModel

@Composable
fun MovieDetailScreen(
    movieId: String,
    movieViewModel: MovieViewModel = viewModel()
) {
    val movieDetail by movieViewModel.movieDetail.observeAsState()
    val isLoading by movieViewModel.isLoading.observeAsState(initial = true)
    val errorMessage by movieViewModel.errorMessage.observeAsState()

    // Busca os detalhes do filme quando o Composable é iniciado
    LaunchedEffect(key1 = movieId) {
        movieViewModel.fetchMovieById(movieId)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            errorMessage != null -> {
                Text(
                    text = errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center).padding(16.dp)
                )
            }
            movieDetail != null -> {
                DetailContent(movie = movieDetail!!)
            }
        }
    }
}

@Composable
fun DetailContent(movie: MovieDetail) {
    var userRating by remember { mutableStateOf(0) }
    var userReview by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        AsyncImage(
            model = movie.posterUrl,
            contentDescription = "Pôster do filme ${movie.title}",
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(movie.title, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Text("${movie.year} | ${movie.rated} | ${movie.runtime}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(movie.plot, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Diretor: ${movie.director}", style = MaterialTheme.typography.bodyMedium)
        Text("Gênero: ${movie.genre}", style = MaterialTheme.typography.bodyMedium)
        Text("Nota IMDb: ${movie.imdbRating}", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)

        // Seção de Avaliação
        Spacer(modifier = Modifier.height(24.dp))
        Divider()
        Spacer(modifier = Modifier.height(16.dp))
        Text("Sua Avaliação", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))

        // Campo de Nota
        RatingBar(rating = userRating, onRatingChanged = { userRating = it })

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de Review
        OutlinedTextField(
            value = userReview,
            onValueChange = { userReview = it },
            label = { Text("Escreva sua avaliação") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )
    }
}

@Composable
fun RatingBar(rating: Int, onRatingChanged: (Int) -> Unit) {
    Row {
        (1..5).forEach { star ->
            Icon(
                imageVector = if (star <= rating) Icons.Filled.Star else Icons.Filled.StarBorder,
                contentDescription = null,
                tint = if (star <= rating) Color.Yellow else Color.Gray,
                modifier = Modifier
                    .size(40.dp)
                    .clickable { onRatingChanged(star) }
            )
        }
    }
}
