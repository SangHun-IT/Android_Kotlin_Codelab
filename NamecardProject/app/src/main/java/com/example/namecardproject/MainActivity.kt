package com.example.namecardproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.namecardproject.ui.theme.NamecardProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NamecardProjectTheme {
                Surface {
                    NamecardMainScreen()
                }
            }
        }
    }
}

@Composable
fun NamecardMainScreen(modifier: Modifier = Modifier) {
    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(210, 232, 212))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            contentAlignment = Alignment.Center
        ) {
            NamecardTitle(
                name = "Jennifer Doe"
            )
        }
        
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp)
        ) {
            NamecardInformation(
                phone = "+11 (123) 444 555 666",
                sns = "@AndroidDev",
                email = "jen.doe@android.com",
                modifier = modifier
            )
        }

    }
}

@Composable
fun NamecardTitle(name: String, modifier: Modifier = Modifier) {
    val image = painterResource(R.drawable.android_logo)

    Column {
        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = modifier
                .background(color = Color(7, 48, 66))
                .height(125.dp)
                .padding(10.dp)
                .align(Alignment.CenterHorizontally),
        )
        Text(
            text = name,
            fontSize = 50.sp,
            modifier = modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(10.dp)
        )
        Text(
            text = "Android Developer Extraordinaire",
            color = Color(0,101,43),
            modifier = modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(10.dp)
        )
    }
}

@Composable
fun NamecardInformation(phone: String, sns: String, email: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        NamecardDetailInfo(
            value = phone,
            icon = Icons.Rounded.Call,
            modifier = modifier
                .weight(1f)
                .padding(top = 5.dp, bottom = 5.dp)
        )
        NamecardDetailInfo(
            value = sns,
            icon = Icons.Rounded.Share,
            modifier = modifier
                .weight(1f)
                .padding(top = 5.dp, bottom = 5.dp)
        )
        NamecardDetailInfo(
            value = email,
            icon = Icons.Rounded.Email,
            modifier = modifier
                .weight(1f)
                .padding(top = 5.dp, bottom = 5.dp)
        )
    }
}

@Composable
fun NamecardDetailInfo(value:String, icon:ImageVector, modifier: Modifier = Modifier) {

    Box(
        modifier = Modifier
            .height(30.dp)
    ) {
        Row() {
            Icon(
                icon,
                contentDescription = null,
                tint = Color(0,101,43),
                modifier = Modifier
                    .width(50.dp)
                    .align(Alignment.CenterVertically)
                    .padding(end = 10.dp)
            )
            Column {
                Text(
                    text = value,
                    modifier = Modifier
                )
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun NamecardMainPreview() {
    NamecardProjectTheme {
        NamecardMainScreen()
    }
}