package com.immo.findTheDifferences.ui.screens

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.immo.findTheDifferences.Const
import com.immo.findTheDifferences.R
import com.immo.findTheDifferences.UserState
import com.immo.findTheDifferences.localization.Vocabulary
import com.immo.findTheDifferences.localization.main_play_button
import com.immo.findTheDifferences.localization.main_title
import com.immo.findTheDifferences.ui.components.SimpleButton
import com.immo.findTheDifferences.ui.dialogs.FAQ
import com.immo.findTheDifferences.ui.theme.Typography
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import java.lang.Math.ceil
import java.util.*
import kotlin.collections.ArrayList

@Composable
fun GreetingScreen(isGameScreen: MutableState<Boolean>) {
    val localization = Vocabulary.localization
    val images = arrayListOf(
        R.drawable.image1,
        R.drawable.image9,
        R.drawable.image2,
        R.drawable.image8,
        R.drawable.image3,
        R.drawable.image4,
        R.drawable.image5,
        R.drawable.image6,
        R.drawable.image7,
        R.drawable.image10
    )
    val openDialog = remember {
        mutableStateOf(false)
    }
    if (openDialog.value) {
        FAQ(openDialog = openDialog)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        CrossFade(images = images)
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

                Image(
                    painter = painterResource(id = R.drawable.ic_launcher),
                    contentDescription = null
                )

            }
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Button(
                    onClick = { isGameScreen.value = true },
                    modifier = Modifier
                        .height(150.dp)
                        .width(250.dp)
                ) {
                    Text(
                        text = localization.main_play_button(),
                        style = Typography.h1,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@SuppressLint("UnusedCrossfadeTargetStateParameter")
@Composable
fun CrossFade(images: ArrayList<Int>) {
    val pagerState = rememberPagerState()
    LaunchedEffect(Unit) {
        while(true) {
            yield()
            delay(4000)
            tween<Float>(900)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % (pagerState.pageCount)
            )
        }
    }
    HorizontalPager(
        count = images.size,
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { currentPage ->
        Image(
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            painter = painterResource(images[currentPage]),
            contentDescription = null,
        )
    }
}


//@Composable
//fun GifImage(
//    modifier: Modifier = Modifier,
//) {
//    val context = LocalContext.current
//    val imageLoader = ImageLoader.Builder(context)
//        .components {
//            if (SDK_INT >= 28) {
//                add(ImageDecoderDecoder.Factory())
//            } else {
//                add(GifDecoder.Factory())
//            }
//        }
//        .build()
//    Image(
//        painter = rememberAsyncImagePainter(
//            ImageRequest.Builder(context).data(data = R.drawable.main_gif).apply(block = {
//                size(Size.ORIGINAL)
//            }).build(), imageLoader = imageLoader
//        ),
//        contentScale = ContentScale.FillWidth,
//        contentDescription = null,
//        modifier = modifier,
//    )
//}