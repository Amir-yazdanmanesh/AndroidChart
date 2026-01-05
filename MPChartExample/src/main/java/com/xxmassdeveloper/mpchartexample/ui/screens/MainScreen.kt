package com.xxmassdeveloper.mpchartexample.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.xxmassdeveloper.mpchartexample.ui.navigation.ChartItem
import com.xxmassdeveloper.mpchartexample.ui.navigation.Screen
import com.xxmassdeveloper.mpchartexample.ui.navigation.getChartItems

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigate: (Screen) -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val chartItems = remember { getChartItems() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MPAndroidChart Example") },
                actions = {
                    IconButton(onClick = { showMenu = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("View on GitHub") },
                            onClick = {
                                showMenu = false
                                val intent = Intent(Intent.ACTION_VIEW).apply {
                                    data = Uri.parse("https://github.com/PhilJay/MPAndroidChart")
                                }
                                context.startActivity(intent)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Report Problem") },
                            onClick = {
                                showMenu = false
                                val intent = Intent(Intent.ACTION_SENDTO).apply {
                                    data = Uri.parse("mailto:philjay.librarysup@gmail.com")
                                    putExtra(Intent.EXTRA_SUBJECT, "MPAndroidChart Issue")
                                    putExtra(Intent.EXTRA_TEXT, "Your error report here...")
                                }
                                context.startActivity(Intent.createChooser(intent, "Report Problem"))
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Website") },
                            onClick = {
                                showMenu = false
                                val intent = Intent(Intent.ACTION_VIEW).apply {
                                    data = Uri.parse("http://at.linkedin.com/in/philippjahoda")
                                }
                                context.startActivity(intent)
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(chartItems) { item ->
                ChartListItem(
                    item = item,
                    onClick = {
                        item.screen?.let { onNavigate(it) }
                    }
                )
            }
        }
    }
}

@Composable
private fun ChartListItem(
    item: ChartItem,
    onClick: () -> Unit
) {
    if (item.isSection) {
        Text(
            text = item.title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            if (item.description.isNotEmpty()) {
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
