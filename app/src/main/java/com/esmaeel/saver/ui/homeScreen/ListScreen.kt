package com.esmaeel.saver.ui.homeScreen

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.sharp.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.esmaeel.saver.R
import com.esmaeel.saver.base.ViewState
import com.esmaeel.saver.db.CardItemEntity
import com.esmaeel.saver.db.CardItemType
import com.esmaeel.saver.ui.theme.SaverTheme
import com.esmaeel.saver.utils.HSpace
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import kotlin.random.Random

@ExperimentalAnimationApi
@Composable
fun ListScreen(
    navController: NavHostController? = null,
    viewModel: AppViewModel? = getViewModel(),
    onRefresh: (() -> Unit)? = null,
    onEditClicked: ((id: Int) -> Unit)? = null
) {


    val openDialog = remember { mutableStateOf(false) }
    fun switchDialog() = run { openDialog.value = !openDialog.value }
    fun openDialog() = run { openDialog.value = true }
    fun closeDialog() = run { openDialog.value = false }
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    fun add(title: String, itemType: CardItemType) = run {
        viewModel?.addCard(
            cardItemEntity = CardItemEntity(
                title = title,
                icon = when (itemType) {
                    CardItemType.COUNTABLE -> R.drawable.ic_dollar
                    CardItemType.TODO -> R.drawable.ic_to_do_list
                    CardItemType.NOTE -> R.drawable.ic_note
                },
                type = itemType.type_id
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Save Notes, Todo, Expenses.") }
            )
        },

        floatingActionButton = {
            FloatingActionButton(onClick = {
                switchDialog()
            }) {
                Text(text = stringResource(id = R.string.add))
            }
        }
    ) {
        Box(Modifier.fillMaxSize()) {
            val state = viewModel?.state?.collectAsState()

            BaseScafold(state) {

                when (state?.value) {


                    is ViewState.Empty -> {
                        Surface(
                            Modifier
                                .fillMaxSize()
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = "Empty Data for now,\n try to add new Items.")
                            }
                        }
                    }

                    is HomeScreenState.OnAddSuccessfully -> {
                        val data = (state.value as HomeScreenState.OnAddSuccessfully).response.cards
                        ListPageContent(scrollState = scrollState, data = data, onEditClicked = {
                            onEditClicked?.invoke(it)
                        })
                        coroutineScope.launch {
                            scrollState.animateScrollTo(data.size - 1)
                        }
                    }

                    is HomeScreenState.OnCardsLoaded -> {
                        val data = (state.value as HomeScreenState.OnCardsLoaded).response.cards
                        ListPageContent(scrollState, data = data, onEditClicked = {
                            onEditClicked?.invoke(it)
                        })
                    }

                }
            }

            AddNewListItemDialog(
                openDialog,
                Modifier.align(Alignment.Center),
                onDialogClicked = {},
                onClose = {
                    closeDialog()
                },
                onConfirmClicked = { title, type ->
                    closeDialog()
                    add(title, type)
                }
            )
        }
    }

}

@Composable
fun BaseScafold(state: State<ViewState>, doThis: () -> Unit) {
    when (state) {
        is ViewState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        else -> doThis()
    }
}

@ExperimentalAnimationApi
@Composable
fun ListPageContent(
    scrollState: ScrollState,
    data: List<CardItemEntity>,
    onEditClicked: (id: Int) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        ListComp(
            data,
            onEditClicked = { id -> onEditClicked?.invoke(id) },
            scrollState = scrollState
        )
    }
}

@Composable
fun AddNewListItemDialog(
    openDialog: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    onDialogClicked: () -> Unit,
    onClose: () -> Unit,
    onConfirmClicked: (title: String, itemType: CardItemType) -> Unit
) {

    val dialogSize by animateIntAsState(
        if (openDialog.value) 500 else 0,
        animationSpec = if (openDialog.value) spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow,
        ) else spring(visibilityThreshold = Int.VisibilityThreshold)
    )
    val curve by animateIntAsState(
        if (openDialog.value) 20 else 0
    )

    Surface(
        shape = RoundedCornerShape(curve.dp),
        elevation = 10.dp,
        modifier = modifier
            .width(dialogSize.dp)
            .padding(24.dp)
            .clickable { onDialogClicked() }
    ) {
        Column() {

            Box(Modifier.fillMaxWidth()) {
                Surface(
                    color = Color.LightGray,
                    shape = CircleShape,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "",
                        modifier
                            .padding(8.dp)
                            .clickable {
                                onClose()
                            }
                    )
                }
            }

            Column(Modifier.padding(16.dp)) {

                var titleText by remember { mutableStateOf("Payments") }
                var selectedType by remember { mutableStateOf(CardItemType.NOTE) }
                var isMenuExpanded by remember { mutableStateOf(false) }
                fun switchMenu() = run { isMenuExpanded = !isMenuExpanded }
                fun openMenu() = run { isMenuExpanded = true }
                fun closeMenu() = run { isMenuExpanded = false }

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "", Modifier.clickable {
                                titleText = ""
                            }
                        )
                    },
                    value = titleText,
                    onValueChange = { titleText = it },
                    label = { Text("Title") }
                )

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Text(text = "Type", style = TextStyle(fontWeight = FontWeight.Light))

                    HSpace()

                    ItemTypesMenu(
                        expanded = isMenuExpanded,
                        onDropMenuSelected = {
                            selectedType = it
                        },
                        onStateChanged = {
                            if (it) openMenu() else closeMenu()
                        }
                    )

                    HSpace()

                    TextButton(onClick = { switchMenu() }) {
                        Text(text = selectedType.name)
                    }
                }


                Button(
                    modifier = Modifier.align(Alignment.End),
                    onClick = {
                        onConfirmClicked(titleText, selectedType)
                        onClose()
                    }) {
                    Text(text = "Done")
                }


            }
        }

    }

}

@Composable
fun ItemTypesMenu(
    expanded: Boolean = false,
    onDropMenuSelected: (CardItemType) -> Unit,
    onStateChanged: (expanded: Boolean) -> Unit,
) {

    DropdownMenu(expanded = expanded, onDismissRequest = {
        onStateChanged(false)
    }) {

        DropdownMenuItem(onClick = {
            onDropMenuSelected(CardItemType.NOTE)
            onStateChanged(false)
        }) {
            Text(text = CardItemType.NOTE.name)
        }

        DropdownMenuItem(onClick = {
            onDropMenuSelected(CardItemType.TODO)
            onStateChanged(false)
        }) {
            Text(text = CardItemType.TODO.name)
        }

        DropdownMenuItem(onClick = {
            onDropMenuSelected(CardItemType.COUNTABLE)
            onStateChanged(false)
        }) {
            Text(text = CardItemType.COUNTABLE.name)
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun ListComp(
    data: List<CardItemEntity>,
    onEditClicked: (id: Int) -> Unit,
    scrollState: ScrollState
) {
    // Remember a CoroutineScope to be able to launch
    val coroutineScope = rememberCoroutineScope()

    Box() {
        AnimatedVisibility(visible = scrollState.value != 0) {
            Surface {
                IconButton(modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp),
                    onClick = {
                        coroutineScope.launch {
                            // Animate scroll to the first item
                            scrollState.animateScrollTo(0)
                        }
                    }
                ) {
                    Surface(
                        shape = CircleShape,
                        elevation = 8.dp,
                        modifier = Modifier.size(32.dp)
                    ) {

                        Icon(
                            imageVector = Icons.Sharp.KeyboardArrowUp,
                            contentDescription = ""
                        )

                    }
                }
            }
        }


        Column(Modifier.verticalScroll(scrollState)) {
            data.forEach { item ->
                ListItemUi(
                    cardItemEntity = item,
                    onCardExpanded = {},
                    onCardCollapsed = {},
                    onCardItemClicked = {},
                    onEditClicked = { id ->
                        onEditClicked(id)
                    }
                )
            }
        }

        /*LazyColumn(modifier = Modifier.fillMaxHeight(), state = scrollState) {
            items(items = data, key = { it.id }, itemContent = { item: CardItemEntity ->
                ListItemUi(
                    cardItemEntity = item,
                    onCardExpanded = {},
                    onCardCollapsed = {},
                    onCardItemClicked = {},
                    onEditClicked = { id ->
                        onEditClicked(id)
                    }
                )
            })
        }*/


    }
}


@ExperimentalAnimationApi
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListItemUi(
    cardItemEntity: CardItemEntity,
    onCardExpanded: () -> Unit,
    onCardCollapsed: () -> Unit,
    onCardItemClicked: () -> Unit,
    onEditClicked: (id: Int) -> Unit
) {

    var isInEditMode by remember { mutableStateOf(false) }

    val radius by animateFloatAsState(if (isInEditMode) 24f else 8f)
    val height by animateFloatAsState(if (isInEditMode) 280f else 120f)
    val iconSize by animateIntAsState(if (isInEditMode) 40 else 24)


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = height.dp)
            .padding(16.dp)
            .clickable {
                onCardItemClicked()
                isInEditMode = !isInEditMode
                if (isInEditMode) onCardExpanded() else onCardCollapsed()
            }
            .animateContentSize(),
        elevation = 24.dp,
        shape = RoundedCornerShape(radius.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Surface(
                    shape = RoundedCornerShape(percent = 20),
                    modifier = Modifier.size(iconSize.dp)
                ) {
                    Image(
                        painter = painterResource(id = cardItemEntity.icon),
                        contentDescription = "",
                    )
                }

                Column {
                    Text(
                        text = cardItemEntity.title.toString(),
                        style = TextStyle(fontWeight = FontWeight(700))
                    )

                    Text(
                        text = cardItemEntity.subtitle.toString(),
                        style = TextStyle(fontWeight = FontWeight(200))
                    )
                }

                HSpace()

                AnimatedVisibility(visible = isInEditMode) {
                    Icon(
                        painter = painterResource(id = R.drawable.edit_24),
                        contentDescription = "",
                        Modifier
                            .clickable {
                                onEditClicked(cardItemEntity.id)
                            }
                    )
                    HSpace()
                }


            }


            if (isInEditMode)
                when (cardItemEntity.type) {
                    CardItemType.COUNTABLE.type_id -> {
                        CountableItemDetails(cardItemEntity = cardItemEntity)
                    }
                    CardItemType.TODO.type_id -> {
                        TodoItemDetails(cardItemEntity = cardItemEntity)
                    }
                    CardItemType.NOTE.type_id -> {
                        NoteItemDetails(cardItemEntity = cardItemEntity)
                    }
                }


        }
    }
}


@ExperimentalAnimationApi
@Composable
fun CountableItemDetails(cardItemEntity: CardItemEntity) {
    Text(text = "Countable view")
}

@ExperimentalAnimationApi
@Composable
fun TodoItemDetails(
    cardItemEntity: CardItemEntity = CardItemEntity(
        title = "Payments",
        subtitle = "Buy groceries",
        icon = R.drawable.ic_to_do_list,
        type = CardItemType.TODO.type_id,
    )
) {
    // show list of todos
    SaverTheme {
        Column {

            HSpace(36)

            Row {
                val checkedState = rememberSaveable { mutableStateOf(true) }
                val text = rememberSaveable { mutableStateOf(" TODO ".repeat(Random.nextInt(3))) }
                Checkbox(checked = checkedState.value,
                    onCheckedChange = { checkedState.value = it }
                )
                Text(text = text.value)
            }

        }

    }
}

@ExperimentalAnimationApi
@Composable
fun NoteItemDetails(cardItemEntity: CardItemEntity) {
    // TODO: 4/18/21 if in edit mode  show input
    val scrollState = rememberScrollState()
    Column(Modifier.verticalScroll(scrollState)) {
        repeat(100) {
            Text(text = "Countable")
        }
    }
}


@Preview
@Composable
fun Test() {
    SaverTheme {

    }
}
