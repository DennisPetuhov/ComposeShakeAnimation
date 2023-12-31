package com.example.composeshakeanimation
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

sealed class LogInState {
    object Input : LogInState()
    object Wrong : LogInState()
    object Correct : LogInState()
}

val red = Color(0xFFDD5D5D)
val green = Color(0xFF79DD5D)
val white = Color(0xFFF7F7F7)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun LoginExample() {
    var password by remember { mutableStateOf("") }
    var logInState: LogInState by remember { mutableStateOf(LogInState.Input) }
    val color: Color by animateColorAsState(
        when (logInState) {
            LogInState.Correct -> green
            LogInState.Input -> white
            LogInState.Wrong -> red
        }, label = "Button color"
    )
    val shakeController = rememberShakeController()
    Column() {
        Spacer(modifier = Modifier.height(15.dp))
        TextField(
            value = password,
            onValueChange = {
                logInState = LogInState.Input
                password = it
            },
            isError = logInState == LogInState.Wrong,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )


//    Spacer(modifier = Modifier.height(15.dp))
//    Box(modifier = Modifier.height(12.dp))
    Spacer(modifier = Modifier.height(15.dp))
    Box(
        modifier = Modifier
            .padding(8.dp)
            .shake(shakeController = shakeController)
            .border(2.dp, color, RoundedCornerShape(5.dp))
            .background(color = color.copy(alpha = .1f), shape = RoundedCornerShape(5.dp))
            .pointerInput(Unit) {
                detectTapGestures {
                    logInState = when (password) {
                        "123" -> LogInState.Correct
                        else -> LogInState.Wrong
                    }
                    when (logInState) {
                        LogInState.Correct -> {
                            shakeController.shake(
                                ShakeConfig(
                                    iterations = 4,
                                    intensity = 1_000f,
                                    rotateX = -20f,
                                    translateY = 20f,
                                )
                            )
                        }

                        LogInState.Wrong -> {
                            shakeController.shake(
                                ShakeConfig(
                                    iterations = 4,
                                    intensity = 2_000f,
                                    rotateY = 15f,
                                    translateX = 40f,
                                )
                            )
                        }

                        LogInState.Input -> {}
                    }
                }
            }
            .clip(RoundedCornerShape(5.dp))
            .padding(horizontal = 24.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center,
    ) {
        AnimatedContent(
            targetState = logInState,
            transitionSpec = {
                slideInVertically(spring(stiffness = Spring.StiffnessMedium)) { -it } + fadeIn() with
                        slideOutVertically(spring(stiffness = Spring.StiffnessHigh)) { it } + fadeOut() using SizeTransform(
                    clip = false
                )
            },
            contentAlignment = Alignment.Center
        ) { logInState ->
            Text(
                text = when (logInState) {
                    LogInState.Correct -> "Success"
                    LogInState.Input -> "Login"
                    LogInState.Wrong -> "Try Again"
                },
                color = Color.White,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}}