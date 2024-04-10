package me.bmax.apatch.ui.screen

import android.net.Uri
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import android.content.res.ColorStateList
import android.content.Intent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import me.bmax.apatch.BuildConfig
import me.bmax.apatch.R

@Composable
fun Logo(
    icon: Painter,
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    fraction: Float = 0.6f
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = containerColor,
        contentColor = contentColor
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(fraction),
                painter = icon,
                contentDescription = null,
                tint = LocalContentColor.current
            )
        }
    }
}

@Composable
fun HtmlText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    color: Color = LocalContentColor.current,
) {
    val linkTextColor = MaterialTheme.colorScheme.primary
    AndroidView(
        modifier = modifier,
        factory = { TextView(it) },
        update = {
            it.movementMethod = LinkMovementMethod.getInstance()
            it.setLinkTextColor(ColorStateList.valueOf(linkTextColor.toArgb()))
            it.highlightColor = style.background.toArgb()

            it.textSize = style.fontSize.value
            it.setTextColor(color.toArgb())
            it.setBackgroundColor(style.background.toArgb())
            it.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_COMPACT)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun AboutScreen(
    navigator: DestinationsNavigator
) {
    Scaffold(topBar = {
        AboutTopBar(
            onBack = { navigator.popBackStack() },
        )
    }, content = { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Logo(
                icon = painterResource(id = R.drawable.ic_launcher_foreground),
                modifier = Modifier.size(100.dp),
                fraction = 1.50f
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Apatch",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Version ${BuildConfig.VERSION_CODE}.${BuildConfig.VERSION_NAME}",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                AboutButton(
                    icon = painterResource(id = R.drawable.github),
                    text = "GitHub",
                    url = "https://github.com/bmax121/APatch"
                )
                AboutButton(
                    icon = painterResource(id = R.drawable.weblate),
                    text = "Weblate",
                    url = "https://hosted.weblate.org/engage/apatch/"
                )
                AboutButton(
                    icon = painterResource(id = R.drawable.telegram),
                    text = "Telegram",
                    url = "https://t.me/APatchChannel"
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedCard(
                modifier = Modifier.padding(horizontal = 20.dp),
                shape = RoundedCornerShape(15.dp),
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.outline)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Universal root solution provided for Android kernel versions 3.18 - 6.1",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    HtmlText(
                        text = stringResource(
                            id = R.string.about_made_by,
                            "<a href=https://github.com/bmax121>bmax</a>"
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AboutTopBar(onBack: () -> Unit = {}) {
    TopAppBar(
        title = { Text(stringResource(R.string.about)) },
        navigationIcon = {
            IconButton(
                onClick = onBack
            ) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null) }
        },
        )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutButton(
    icon: Painter,
    text: String,
    url: String
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            }
            .padding(12.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = icon,
                contentDescription = text,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 12.sp
            )
        }
    }
}
