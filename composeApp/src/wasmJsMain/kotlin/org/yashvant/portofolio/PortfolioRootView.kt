package org.yashvant.portofolio

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.yashvant.portofolio.ui.components.*
import org.yashvant.portofolio.ui.theme.DarculaColors
import org.yashvant.portofolio.ui.theme.DarculaTheme
import org.yashvant.portofolio.viewmodel.PortfolioViewModel
import org.yashvant.portofolio.data.*

@Composable
fun PortfolioRootView() {
    val viewModel = remember { PortfolioViewModel() }

    val fileTree by viewModel.fileTree
    val openTabs by viewModel.openTabs
    val activeTab by viewModel.activeTab
    val showProjectDialog by viewModel.showProjectDialog
    val selectedProject by viewModel.selectedProject

    DarculaTheme {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(DarculaColors.Background)
        ) {
            val isCompact = maxWidth < 800.dp

            if (isCompact) {
                // Mobile layout - vertical stacking with collapsible explorer
                MobileLayout(
                    viewModel = viewModel,
                    fileTree = fileTree,
                    openTabs = openTabs,
                    activeTab = activeTab,
                    showProjectDialog = showProjectDialog,
                    selectedProject = selectedProject
                )
            } else {
                // Desktop layout - horizontal split
                DesktopLayout(
                    viewModel = viewModel,
                    fileTree = fileTree,
                    openTabs = openTabs,
                    activeTab = activeTab,
                    showProjectDialog = showProjectDialog,
                    selectedProject = selectedProject
                )
            }
        }
    }
}

@Composable
private fun MobileLayout(
    viewModel: PortfolioViewModel,
    fileTree: FileNode,
    openTabs: List<TabItem>,
    activeTab: FileNode?,
    showProjectDialog: Boolean,
    selectedProject: Project?
) {
    var showExplorer by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        // Mobile header with menu button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarculaColors.SurfaceVariant)
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { showExplorer = !showExplorer }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Toggle Explorer",
                    tint = DarculaColors.OnSurface
                )
            }

            Text(
                text = "Yashvant Yadav Portfolio",
                style = MaterialTheme.typography.titleMedium,
                color = DarculaColors.OnSurface
            )

            Spacer(modifier = Modifier.width(48.dp)) // Balance the layout
        }

        if (showExplorer) {
            // Full-screen explorer overlay on mobile
            FileExplorerView(
                fileTree = fileTree,
                onFileClicked = { file ->
                    viewModel.onFileClicked(file)
                    if (file.type != FileType.FOLDER) {
                        showExplorer = false // Hide explorer after file selection
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        } else {
            // Editor view
            EditorView(
                openTabs = openTabs,
                activeTab = activeTab,
                onTabClicked = viewModel::onTabClicked,
                onTabClosed = viewModel::onTabClosed,
                onRunProject = viewModel::onRunProjectClicked,
                modifier = Modifier.weight(1f)
            )

            // Mobile status bar
            StatusBar()
        }
    }

    // Project Demo Dialog
    if (showProjectDialog) {
        val currentProject = selectedProject
        if (currentProject != null) {
            ProjectDemoDialog(
                project = currentProject,
                onDismiss = viewModel::onCloseProjectDialog
            )
        }
    }
}

@Composable
private fun DesktopLayout(
    viewModel: PortfolioViewModel,
    fileTree: FileNode,
    openTabs: List<TabItem>,
    activeTab: FileNode?,
    showProjectDialog: Boolean,
    selectedProject: Project?
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Main IDE layout
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            // File Explorer (left panel)
            FileExplorerView(
                fileTree = fileTree,
                onFileClicked = viewModel::onFileClicked
            )

            // Editor View (right panel)
            EditorView(
                openTabs = openTabs,
                activeTab = activeTab,
                onTabClicked = viewModel::onTabClicked,
                onTabClosed = viewModel::onTabClosed,
                onRunProject = viewModel::onRunProjectClicked,
                modifier = Modifier.weight(1f)
            )
        }

        // Status Bar
        StatusBar()
    }

    // Project Demo Dialog
    if (showProjectDialog) {
        val currentProject = selectedProject
        if (currentProject != null) {
            ProjectDemoDialog(
                project = currentProject,
                onDismiss = viewModel::onCloseProjectDialog
            )
        }
    }
}

@Composable
private fun StatusBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp)
            .background(DarculaColors.StatusBar)
            .border(1.dp, DarculaColors.TabBorder)
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Yashvant Yadav - Android Developer Portfolio",
            style = MaterialTheme.typography.labelMedium,
            color = DarculaColors.OnSurface
        )

        Text(
            text = "Kotlin/Wasm | Compose for Web",
            style = MaterialTheme.typography.labelMedium,
            color = DarculaColors.Comment
        )
    }
}
