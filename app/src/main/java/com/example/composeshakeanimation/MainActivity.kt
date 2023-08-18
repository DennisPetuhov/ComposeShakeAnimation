package com.example.composeshakeanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.composeshakeanimation.ui.theme.ComposeShakeAnimationTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeShakeAnimationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier,                    color = MaterialTheme.colorScheme.background
                ) {

                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    LoginExample()
}

//@Composable
//fun Shaker() {
//    val shake = remember { Animatable(0f) }
//    var trigger by remember { mutableStateOf(0L) }
//    LaunchedEffect(trigger) {
//        if (trigger != 0L) {
//            for (i in 0..10) {
//                when (i % 2) {
//                    0 -> shake.animateTo(5f, spring(stiffness = 100_000f))
//                    else -> shake.animateTo(-5f, spring(stiffness = 100_000f))
//                }
//            }
//            shake.animateTo(0f)
//        }
//    }
//
//    Box(
//        modifier = Modifier
//            .clickable { trigger = System.currentTimeMillis() }
//            .offset { IntOffset(shake.value.roundToInt(), 0) }
//            .padding(horizontal = 124.dp, vertical = 111.dp)
//
//    ) {
//        Text(text = "Shake me")
//    }
//}


@Composable
fun Shaker() {
    val shakeController = rememberShakeController()
    Box(
        modifier = Modifier
            .clickable {
                shakeController.shake(ShakeConfig(10, translateX = 5f))
            }
            .shake(shakeController)
            .padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        Text(text = "Shake me")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeShakeAnimationTheme {
        Greeting("Android")
    }
}