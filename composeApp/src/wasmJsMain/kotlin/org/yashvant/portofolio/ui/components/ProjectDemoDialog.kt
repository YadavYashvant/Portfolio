package org.yashvant.portofolio.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.yashvant.portofolio.data.Project
import org.yashvant.portofolio.data.ProjectDemoType
import org.yashvant.portofolio.ui.theme.DarculaColors

@Composable
fun ProjectDemoDialog(
    project: Project,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.8f),
            colors = CardDefaults.cardColors(
                containerColor = DarculaColors.Surface
            )
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(DarculaColors.SurfaceVariant)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${project.name} - Demo",
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold,
                        color = DarculaColors.OnSurface
                    )

                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = DarculaColors.OnSurface
                        )
                    }
                }

                // Content based on demo type
                when (project.demoType) {
                    ProjectDemoType.IMAGE_ANALYSIS -> ImageAnalysisDemo()
                    ProjectDemoType.APP_BACKUP -> AppBackupDemo()
                    ProjectDemoType.GITHUB_LINK -> GitHubLinkDemo(project)
                    ProjectDemoType.LINK -> GitHubLinkDemo(project)
                }
            }
        }
    }
}

@Composable
private fun ImageAnalysisDemo() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Left side - Sample image
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            Text(
                text = "Sample Image",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = DarculaColors.OnSurface
            )

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(DarculaColors.Background)
                    .border(2.dp, DarculaColors.Primary),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Image,
                        contentDescription = "Sample Image",
                        tint = DarculaColors.Primary,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Sample Photo",
                        style = MaterialTheme.typography.bodyMedium,
                        color = DarculaColors.Comment
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /* Demo action */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarculaColors.Primary
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Analytics,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Analyze with Gemini Vision")
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Right side - Analysis result
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            Text(
                text = "Gemini Vision Analysis",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = DarculaColors.OnSurface
            )

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxSize(),
                colors = CardDefaults.cardColors(
                    containerColor = DarculaColors.Background
                )
            ) {
                LazyColumn(
                    modifier = Modifier.padding(12.dp)
                ) {
                    item {
                        Text(
                            text = "Analysis Results:",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = DarculaColors.Keyword
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "• Object Detection: Mobile phone, person, indoor environment",
                            style = MaterialTheme.typography.bodyMedium,
                            color = DarculaColors.OnBackground
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "• Scene Understanding: Technology demonstration in indoor setting",
                            style = MaterialTheme.typography.bodyMedium,
                            color = DarculaColors.OnBackground
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "• Text Recognition: No visible text detected",
                            style = MaterialTheme.typography.bodyMedium,
                            color = DarculaColors.OnBackground
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "• Confidence Score: 94.7%",
                            style = MaterialTheme.typography.bodyMedium,
                            color = DarculaColors.Number
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Accessibility Description:",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = DarculaColors.Keyword
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "\"A person is holding a smartphone displaying an application interface. The setting appears to be indoors with good lighting, suitable for app demonstration purposes.\"",
                            style = MaterialTheme.typography.bodyMedium,
                            color = DarculaColors.String
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AppBackupDemo() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "App Backup Manager",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = DarculaColors.OnSurface
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Select apps to backup:",
            style = MaterialTheme.typography.bodyMedium,
            color = DarculaColors.OnSurface
        )

        Spacer(modifier = Modifier.height(12.dp))

        val sampleApps = listOf(
            "WhatsApp" to true,
            "Instagram" to false,
            "Gmail" to true,
            "Chrome" to false,
            "Spotify" to true,
            "Netflix" to false,
            "YouTube" to true,
            "Maps" to false
        )

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(sampleApps) { (appName, isSelected) ->
                var checked by remember { mutableStateOf(isSelected) }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { checked = !checked }
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = checked,
                        onCheckedChange = { checked = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = DarculaColors.Primary,
                            uncheckedColor = DarculaColors.Comment
                        )
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Icon(
                        imageVector = Icons.Default.Android,
                        contentDescription = null,
                        tint = DarculaColors.Primary,
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = appName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = DarculaColors.OnSurface
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { /* Demo action */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarculaColors.Primary
                ),
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.CloudUpload,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Backup to Google Drive")
            }

            Button(
                onClick = { /* Demo action */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarculaColors.Secondary
                ),
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.CloudDownload,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Restore from Backup")
            }
        }
    }
}

@Composable
private fun GitHubLinkDemo(project: Project) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Code,
            contentDescription = null,
            tint = DarculaColors.Primary,
            modifier = Modifier.size(64.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = project.name,
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
            color = DarculaColors.OnSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = project.description,
            style = MaterialTheme.typography.bodyLarge,
            color = DarculaColors.OnSurface
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Tech Stack: ${project.techStack}",
            style = MaterialTheme.typography.bodyMedium,
            color = DarculaColors.Comment
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { /* Open GitHub link */ },
            colors = ButtonDefaults.buttonColors(
                containerColor = DarculaColors.Primary
            )
        ) {
            Icon(
                imageVector = Icons.Default.OpenInNew,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("View on GitHub")
        }
    }
}
