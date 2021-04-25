package com.esmaeel.saver

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.esmaeel.saver.db.CardItemEntity
import com.esmaeel.saver.db.CardItemType
import com.esmaeel.saver.db.LocalDatabaseDao
import com.esmaeel.saver.ui.editScreen.EditScreen
import com.esmaeel.saver.ui.theme.SaverTheme
import com.esmaeel.saver.ui.homeScreen.AppViewModel
import com.esmaeel.saver.ui.homeScreen.ListScreen
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.random.Random

enum class HomeScreens(val route: String) {
    ListScreen("listScreen"),
    EditScreen("editScreen/")
}


@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {

    private val viewModel: AppViewModel by viewModel()
    private val db: LocalDatabaseDao by inject()
    val argument_id: String = "argument_id"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getData()

        setContent {
            SaverTheme {

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = HomeScreens.ListScreen.route
                ) {

                    composable(HomeScreens.ListScreen.route) {
                        ListScreen(
                            navController = navController,
                            viewModel = viewModel,
                            onRefresh = { viewModel.getCards() },
                            onEditClicked = { id -> navController.navigate("${HomeScreens.EditScreen.route}${id}") }
                        )
                    }

                    composable(
                        "${HomeScreens.EditScreen.route}{${argument_id}}",
                        arguments = listOf(navArgument(argument_id) { type = NavType.IntType })
                    ) {
                        EditScreen(
                            id = it.arguments?.getInt(argument_id) ?: 0,
                            navController = navController,
                            viewModel = viewModel,
                            onBackClicked = { navController.popBackStack() }
                        )
                    }

                }
            }
        }


    }

    private fun getData() {
        viewModel.getCards()
    }


}

@ExperimentalAnimationApi
@Composable
fun DefaultPreview() {
    SaverTheme {
        ListScreen()
    }
}