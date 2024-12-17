package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme
import kotlin.math.min

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                ArtSpaceLayout()
            }
        }
    }
}

@Composable
fun ArtSpaceLayout(modifier : Modifier = Modifier) {

    var currentPage by remember { mutableIntStateOf(1) }

    val maxCnt: Int = 10
    val imageRes:Int = getImageRes(currentPage)
    val imgName:String = stringResource(getImageName(currentPage))
    val imgPainter:String = stringResource(getImagePainter(currentPage))
    val imgYear:String = stringResource(getImageYear(currentPage))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 20.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        ArtSpaceImage(
            imgName = imgName,
            imgYear = imgYear,
            imgPainter = imgPainter,
            imageRes = imageRes,
            modifier = modifier
        )

        Row (
            modifier = Modifier
        ){
            Button(
                onClick = {

                    if(currentPage <= 1){
                        /* Nothing */
                    } else {
                        currentPage --
                    }

                },
                modifier = Modifier
                    .padding(10.dp)
                    .width(150.dp)
            ) {
                Text("Previous")
            }
            Button(
                onClick = {

                    if(currentPage >= maxCnt ){
                        /* Nothing */
                    } else {
                        currentPage ++
                    }

                },
                modifier = Modifier
                    .padding(10.dp)
                    .width(150.dp)
            ) {
                Text("Next")
            }
        }
    }
}

@Composable
fun ArtSpaceImage(
    imgName: String,
    imgYear: String,
    imgPainter: String,
    @DrawableRes imageRes: Int,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
            .width(IntrinsicSize.Max),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = modifier
                .background(color = Color.White)
                .shadow(elevation = 10.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image (
                painter = painterResource(imageRes),
                contentDescription = imgName,
                contentScale = ContentScale.Fit,
                modifier = modifier
                    .padding(40.dp)
                    .height(400.dp)
            )
        }
        Spacer(modifier = modifier.height(30.dp))
        Column(
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .background(color = colorResource(R.color.text_background)),
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = imgName,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .align(alignment = Alignment.Start)
                )
                Row (
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = imgPainter,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "($imgYear)"
                    )
                }
            }
        }
    }
}

fun getImageRes(currentPage: Int): Int {
    return when (currentPage) {
        1 -> R.drawable.___mona_lisa_by_leonardo_da_vinci__1519_
        2 -> R.drawable.___starry_night_by_vincent_van_gogh__1889_
        3 -> R.drawable.___the_scream_by_edvard_munch__1893_
        4 -> R.drawable.___guernica_by_pablo_picasso__1937_
        5 -> R.drawable.___the_persistence_of_memory_by_salvador_dali__1931_
        6 -> R.drawable.___three_musicians_by_pablo_picasso__1921_
        7 -> R.drawable.___a_sunday_afternoon_on_the_island_of_la_grande_jatte_by_georges_seurat__1884_
        8 -> R.drawable.___girl_with_a_pearl_earring_by_johannes_vermeer__1665_
        9 -> R.drawable.___whistler_s_mother_by_james_mcneil_whistler__1871_
        else -> R.drawable._0__portrait_de_l_artiste_sans_barbe_by_vincent_van_gogh__1889_
    }
}

fun getImageName(currentPage: Int): Int {
    return when (currentPage) {
        1 -> R.string.imgName_1
        2 -> R.string.imgName_2
        3 -> R.string.imgName_3
        4 -> R.string.imgName_4
        5 -> R.string.imgName_5
        6 -> R.string.imgName_6
        7 -> R.string.imgName_7
        8 -> R.string.imgName_8
        9 -> R.string.imgName_9
        else -> R.string.imgName_10
    }
}

fun getImagePainter(currentPage: Int): Int {
    return when (currentPage) {
        1 -> R.string.imgPainter_1
        2 -> R.string.imgPainter_2
        3 -> R.string.imgPainter_3
        4 -> R.string.imgPainter_4
        5 -> R.string.imgPainter_5
        6 -> R.string.imgPainter_6
        7 -> R.string.imgPainter_7
        8 -> R.string.imgPainter_8
        9 -> R.string.imgPainter_9
        else -> R.string.imgPainter_10
    }
}

fun getImageYear(currentPage: Int): Int {
    return when (currentPage) {
        1 -> R.string.imgYear_1
        2 -> R.string.imgYear_2
        3 -> R.string.imgYear_3
        4 -> R.string.imgYear_4
        5 -> R.string.imgYear_5
        6 -> R.string.imgYear_6
        7 -> R.string.imgYear_7
        8 -> R.string.imgYear_8
        9 -> R.string.imgYear_9
        else -> R.string.imgYear_10
    }
}


@Preview(showBackground = true)
@Composable
fun ArtSpacePreview() {
    ArtSpaceTheme {
        ArtSpaceLayout()
    }
}