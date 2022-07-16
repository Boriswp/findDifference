package com.immo.findTheDifferences.ui.screens

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.immo.findTheDifferences.MainActivityViewModel
import com.immo.findTheDifferences.R
import com.immo.findTheDifferences.localization.Vocabulary
import com.immo.findTheDifferences.localization.main_play_button
import com.immo.findTheDifferences.localization.main_title
import com.immo.findTheDifferences.ui.components.SimpleButton
import com.immo.findTheDifferences.ui.dialogs.FAQ
import com.immo.findTheDifferences.ui.theme.Typography

@Composable
fun GreetingScreen(isGameScreen: MutableState<Boolean>, viewModel: MainActivityViewModel) {
    val localization = Vocabulary.localization
    val openDialog = remember {
        mutableStateOf(false)
    }
    if (openDialog.value) {
        FAQ(openDialog = openDialog, viewModel = viewModel)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SimpleButton(
                    id = R.drawable.ic_info, modifier = Modifier
                        .padding(end = 20.dp)
                ) {
                    openDialog.value = true
                }
                Text(text = localization.main_title(), style = Typography.h2)

            }
            GifImage(
                Modifier.fillMaxWidth()
            )
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                TextButton(onClick = { isGameScreen.value = true }) {
                    Text(
                        text = localization.main_play_button(),
                        style = Typography.h1,
                    )
                }
            }
        }
    }
}


@Composable
fun GifImage(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(context).data(data = R.drawable.main_gif).apply(block = {
                size(Size.ORIGINAL)
            }).build(), imageLoader = imageLoader
        ),
        contentScale = ContentScale.FillWidth,
        contentDescription = null,
        modifier = modifier,
    )
}