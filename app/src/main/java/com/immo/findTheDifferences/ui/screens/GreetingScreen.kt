package com.immo.findTheDifferences.ui.screens

import android.graphics.Insets.add
import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.immo.findTheDifferences.R
import com.immo.findTheDifferences.localization.Vocabulary
import com.immo.findTheDifferences.localization.*
import com.immo.findTheDifferences.ui.theme.Typography

@Composable
fun GreetingScreen(isGameScreen: MutableState<Boolean>) {
    val localization = Vocabulary.localization
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
                Image(
                    painter = painterResource(id = R.drawable.ic_info),
                    contentDescription = "info",
                    modifier = Modifier.padding(end = 20.dp)
                )
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