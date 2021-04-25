package com.esmaeel.saver.ui.editScreen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.esmaeel.saver.base.ViewState
import com.esmaeel.saver.ui.homeScreen.AppViewModel
import org.koin.androidx.compose.getViewModel

@ExperimentalAnimationApi
@Composable
fun EditScreen(
    id: Int,
    navController: NavController? = null,
    viewModel: AppViewModel = getViewModel(),
    onBackClicked: (() -> Unit)? = null
) {

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(horizontal = 16.dp),
                backgroundColor = Color.Transparent, elevation = 0.dp,
                title = { Text(text = "Note Title!") },
                navigationIcon = {
                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { onBackClicked?.invoke() },
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = ""
                    )
                }
            )
        },
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            viewModel.getCardDetails(id)
            val state = viewModel.state.collectAsState()

            when (state.value) {

                is ViewState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
            }

            Column {
                Icon(imageVector = Icons.Default.Star, contentDescription = "", Modifier.clickable {
                    onBackClicked?.invoke()
                })

                Text(text = " Hello ".repeat(100))
            }

        }
    }
}