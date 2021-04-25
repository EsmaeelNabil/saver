package com.esmaeel.saver.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


val testModifier = Modifier.background(Color.Red)

@Composable
fun HSpace(size: Int = 16) {
    Spacer(modifier = Modifier.width(size.dp))
}

@Composable
fun VSpace(size: Int = 16) {
    Spacer(modifier = Modifier.height(size.dp))
}
