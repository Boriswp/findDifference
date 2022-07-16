package com.immo.findTheDifferences.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.immo.findTheDifferences.R

@Composable
fun SimpleButton(@DrawableRes id: Int, modifier: Modifier = Modifier, onClick: () -> Unit) {
    TextButton(
        shape = RoundedCornerShape(15.dp),
        modifier = modifier,
        onClick = onClick,
        contentPadding = PaddingValues(0.dp),
        elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp)
    ) {
        Image(
            painter = painterResource(id = id),
            contentDescription = "info",
            contentScale = ContentScale.FillBounds
        )
    }

}