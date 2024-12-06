package com.example.lemonade

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.collection.emptyLongSet
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                LemonadePreview()
            }
        }
    }
}

@Preview
@Composable
fun LemonadePreview() {
    Lemonade()
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Lemonade(modifier: Modifier = Modifier) {

    var step by remember { mutableStateOf(1) }
    var clickCnt by remember { mutableStateOf(0) }

    var imageResource = when(step) {
        1 -> R.drawable.lemon_tree
        2 -> R.drawable.lemon_squeeze
        3 -> R.drawable.lemon_drink
        else -> R.drawable.lemon_restart
    }

    var stepDescription = when(step) {
        1 -> R.string.step1_desc
        2 -> R.string.step2_desc
        3 -> R.string.step3_desc
        else -> R.string.step4_desc
    }

    var imageDescription = when(step) {
        1 -> R.string.step1_img
        2 -> R.string.step2_img
        3 -> R.string.step3_img
        else -> R.string.step4_img
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Lemonade",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = colorResource(R.color.titleBackGround)
                )
            )
        }
    ) { innerPadding ->
        Surface (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(colorResource(R.color.mainBackGround))
        ) {

            when(step) {
                1 -> {
                    LemonadeTextAndImage(
                        textResourceId = stepDescription,
                        imageResourceId = imageResource,
                        imageDescriptionId = imageDescription,
                        onImageClick = {
                            step = 2
                            clickCnt = (2..4).random()
                        }
                    )
                }
                2 -> {
                    LemonadeTextAndImage(
                        textResourceId = stepDescription,
                        imageResourceId = imageResource,
                        imageDescriptionId = imageDescription,
                        onImageClick = {
                            clickCnt --
                            if(clickCnt == 0) {
                                step = 3
                            }
                        }
                    )
                }
                3 -> {
                    LemonadeTextAndImage(
                        textResourceId = stepDescription,
                        imageResourceId = imageResource,
                        imageDescriptionId = imageDescription,
                        onImageClick = {
                            step = 4
                        }
                    )
                }
                4 -> {
                    LemonadeTextAndImage(
                        textResourceId = stepDescription,
                        imageResourceId = imageResource,
                        imageDescriptionId = imageDescription,
                        onImageClick = {
                            step = 1
                        }
                    )
                }
            }

        }
    }


}

@Composable
fun LemonadeTextAndImage(
    textResourceId: Int,
    imageResourceId: Int,
    imageDescriptionId: Int,
    onImageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        )  {
            Button(
                modifier = Modifier,
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.btnBackGround)
                ),
                contentPadding = PaddingValues(20.dp),
                onClick = onImageClick
            ) {
                Image (
                    painter = painterResource(imageResourceId),
                    contentDescription = stringResource(imageDescriptionId),
                    modifier = Modifier
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = stringResource(textResourceId), fontSize = 16.sp)

        }
    }
}

//@Composable
//fun Lemonade(modifier: Modifier = Modifier
//    .fillMaxSize()
//    .background(color = colorResource(R.color.mainBackGround))) {
//
//
//    var step by remember { mutableStateOf(1) }
//    var clickCnt by remember { mutableStateOf(0) }
//
//    var maxClickCnt = when(step){
//        2 -> (2..4).random()
//        else -> 2
//    }
//    var imageResource = when(step) {
//        1 -> R.drawable.lemon_tree
//        2 -> R.drawable.lemon_squeeze
//        3 -> R.drawable.lemon_drink
//        else -> R.drawable.lemon_restart
//    }
//
//    var stepDescription = stringResource(when(step) {
//        1 -> R.string.step1_desc
//        2 -> R.string.step2_desc
//        3 -> R.string.step3_desc
//        else -> R.string.step4_desc
//    })
//
//    var imageDescription = stringResource(when(step) {
//        1 -> R.string.step1_img
//        2 -> R.string.step2_img
//        3 -> R.string.step3_img
//        else -> R.string.step4_img
//    })
//
//    Column {
//        Column(modifier = Modifier
//            .wrapContentSize(Alignment.TopCenter)
//            .background(color = colorResource(R.color.titleBackGround))
//            .padding(20.dp)
//        ) {
//            Text(text = "Lemonade",
//                fontWeight = FontWeight.Bold,
//                fontSize = 18.sp,
//                color = Color.Black,
//                textAlign = TextAlign.Center,
//                modifier = Modifier.fillMaxWidth()
//            )
//        }
//
//        Column (
//            modifier = modifier.wrapContentSize(Alignment.Center),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        )  {
//            Button(
//                modifier = Modifier,
//                shape = RoundedCornerShape(20.dp),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = colorResource(R.color.btnBackGround)
//                ),
//                contentPadding = PaddingValues(20.dp),
//                onClick = {
//                    /* To Do Something */
//                    if(step < 4) {
//                        if(step == 2)
//                        {
//                            clickCnt ++
//                            if(clickCnt >= maxClickCnt) {
//                                clickCnt = 0
//                                step ++
//                            }
//                        } else {
//                            step ++
//                        }
//                    } else {
//                        step = 1
//                    }
//
//                }
//            ) {
//                Image (
//                    painter = painterResource(imageResource),
//                    contentDescription = imageDescription,
//                    modifier = Modifier
//                )
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Text(text = stepDescription, fontSize = 16.sp)
//
//        }
//    }
//}