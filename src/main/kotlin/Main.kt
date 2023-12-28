import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition

fun main() = application {
    PreComposeApp {

        val navigator = rememberNavigator()
        val windowState = rememberWindowState(
            position = WindowPosition.Aligned(Alignment.Center),
            size = DpSize(1600.dp, 900.dp)
        )

        Window(
            onCloseRequest = ::exitApplication,
            state = windowState
        ) {
            Column {
                Row {
                    Icon(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .clickable {
                                navigator.goBack()
                            },
                        imageVector = Icons.Filled.ArrowBackIos,
                        contentDescription = null,
                        tint = Color.Black
                    )
                    Icon(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape),
                        imageVector = Icons.Filled.ArrowForwardIos,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }

                val buttons = remember {
                    movableContentOf {
                        Row {
                            ('a'..'d').forEach {
                                Column(Modifier.clickable { navigator.navigate("$it", options = NavOptions(launchSingleTop = true, includePath = true)) }.padding(20.dp)) {
                                    Text("Destination $it")
                                }
                            }
                        }
                    }
                }

                NavHost(
                    initialRoute = "a",
                    navigator = navigator,
                    navTransition = NavTransition(
                        createTransition = fadeIn(),
                        destroyTransition = fadeOut(),
                        pauseTransition = ExitTransition.None,
                        resumeTransition = EnterTransition.None,
                        enterTargetContentZIndex = 0.0f,
                        exitTargetContentZIndex = 0.0f
                    ),

                ) {
                    scene("a") {
                        Scaffold(
                            topBar = {
                                buttons()
                            }
                        ) {
                            Box(Modifier.fillMaxSize().background(Color.Blue))
                        }
                    }
                    scene("b") {
                        Scaffold(
                            topBar = {
                                buttons()
                            }
                        ) {
                            Box(Modifier.fillMaxSize().background(Color.Red))
                        }
                    }
                    scene("c") {
                        Scaffold(
                            topBar = {
                                buttons()
                            }
                        ) {
                            Box(Modifier.fillMaxSize().background(Color.Green))
                        }
                    }
                    scene("d") {
                        Scaffold(
                            topBar = {
                                buttons()
                            }
                        ) {
                            Box(Modifier.fillMaxSize().background(Color.Cyan))
                        }
                    }
                }
            }
        }
    }
}
