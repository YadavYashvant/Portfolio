package org.yashvant.portofolio.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.yashvant.portofolio.data.FileNode
import org.yashvant.portofolio.data.FileType
import org.yashvant.portofolio.data.Project
import org.yashvant.portofolio.data.TabItem
import org.yashvant.portofolio.ui.theme.DarculaColors

@Composable
fun EditorView(
    openTabs: List<TabItem>,
    activeTab: FileNode?,
    onTabClicked: (FileNode) -> Unit,
    onTabClosed: (FileNode) -> Unit,
    onRunProject: (Project) -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(DarculaColors.Background)
    ) {
        val isMobile = maxWidth < 600.dp
        val isLaptop = maxWidth >= 600.dp && maxWidth < 1200.dp

        Column(modifier = Modifier.fillMaxSize()) {
            // Tab bar
            if (openTabs.isNotEmpty()) {
                TabBar(
                    tabs = openTabs,
                    isMobile = isMobile,
                    isLaptop = isLaptop,
                    onTabClicked = onTabClicked,
                    onTabClosed = onTabClosed
                )
            }

            // Editor content
            if (activeTab != null) {
                EditorContent(
                    fileNode = activeTab,
                    isMobile = isMobile,
                    isLaptop = isLaptop,
                    onRunProject = onRunProject,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                WelcomeScreen(
                    isMobile = isMobile,
                    isLaptop = isLaptop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
private fun TabBar(
    tabs: List<TabItem>,
    isMobile: Boolean,
    isLaptop: Boolean,
    onTabClicked: (FileNode) -> Unit,
    onTabClosed: (FileNode) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarculaColors.TabInactive)
            .border(1.dp, DarculaColors.TabBorder),
        contentPadding = if (isMobile) PaddingValues(horizontal = 4.dp) else PaddingValues(0.dp)
    ) {
        items(tabs) { tab ->
            Tab(
                tab = tab,
                isMobile = isMobile,
                isLaptop = isLaptop,
                onClicked = { onTabClicked(tab.fileNode) },
                onClosed = { onTabClosed(tab.fileNode) }
            )
        }
    }
}

@Composable
private fun Tab(
    tab: TabItem,
    isMobile: Boolean,
    isLaptop: Boolean,
    onClicked: () -> Unit,
    onClosed: () -> Unit
) {
    val backgroundColor = if (tab.isActive) DarculaColors.TabActive else DarculaColors.TabInactive
    val tabPadding = when {
        isMobile -> PaddingValues(horizontal = 16.dp, vertical = 12.dp)
        isLaptop -> PaddingValues(horizontal = 14.dp, vertical = 10.dp)
        else -> PaddingValues(horizontal = 14.dp, vertical = 10.dp)
    }

    Row(
        modifier = Modifier
            .background(backgroundColor)
            .clickable { onClicked() }
            .padding(tabPadding)
            .border(
                width = if (tab.isActive) 0.dp else 1.dp,
                color = DarculaColors.TabBorder
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = getTabIcon(tab.fileNode),
            contentDescription = null,
            tint = getTabIconColor(tab.fileNode),
            modifier = Modifier.size(
                when {
                    isMobile -> 16.dp
                    isLaptop -> 16.dp
                    else -> 16.dp
                }
            )
        )

        Spacer(modifier = Modifier.width(
            when {
                isMobile -> 8.dp
                isLaptop -> 8.dp
                else -> 8.dp
            }
        ))

        Text(
            text = tab.fileNode.name,
            style = when {
                isMobile -> MaterialTheme.typography.bodyLarge
                isLaptop -> MaterialTheme.typography.titleSmall
                else -> MaterialTheme.typography.titleSmall // Increased from bodyMedium
            },
            color = if (tab.isActive) DarculaColors.OnSurface else DarculaColors.Comment,
            maxLines = 1,
            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.width(
            when {
                isMobile -> 12.dp
                isLaptop -> 10.dp
                else -> 10.dp
            }
        ))

        IconButton(
            onClick = onClosed,
            modifier = Modifier.size(
                when {
                    isMobile -> 24.dp
                    isLaptop -> 20.dp
                    else -> 20.dp
                }
            )
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close tab",
                tint = DarculaColors.Comment,
                modifier = Modifier.size(
                    when {
                        isMobile -> 16.dp
                        isLaptop -> 14.dp
                        else -> 14.dp
                    }
                )
            )
        }
    }
}

@Composable
private fun EditorContent(
    fileNode: FileNode,
    isMobile: Boolean,
    isLaptop: Boolean,
    onRunProject: (Project) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarculaColors.Background)
    ) {
        // File header with run button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarculaColors.SurfaceVariant)
                .padding(
                    when {
                        isMobile -> 12.dp
                        isLaptop -> 12.dp
                        else -> 12.dp
                    }
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = getTabIcon(fileNode),
                    contentDescription = null,
                    tint = getTabIconColor(fileNode),
                    modifier = Modifier.size(
                        when {
                            isMobile -> 20.dp
                            isLaptop -> 18.dp
                            else -> 18.dp
                        }
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = fileNode.name,
                    style = when {
                        isMobile -> MaterialTheme.typography.titleMedium
                        isLaptop -> MaterialTheme.typography.titleMedium
                        else -> MaterialTheme.typography.titleMedium // Increased from bodyMedium
                    },
                    fontWeight = FontWeight.Bold,
                    color = DarculaColors.OnSurface,
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
            }

            if (fileNode.hasRunButton && fileNode.project != null) {
                Button(
                    onClick = { onRunProject(fileNode.project) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DarculaColors.Primary
                    ),
                    modifier = Modifier.height(
                        when {
                            isMobile -> 40.dp
                            isLaptop -> 36.dp
                            else -> 36.dp
                        }
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Run",
                        modifier = Modifier.size(
                            when {
                                isMobile -> 20.dp
                                isLaptop -> 18.dp
                                else -> 18.dp
                            }
                        )
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        "Run",
                        style = when {
                            isMobile -> MaterialTheme.typography.bodyLarge
                            isLaptop -> MaterialTheme.typography.titleSmall
                            else -> MaterialTheme.typography.titleSmall // Increased from bodyMedium
                        }
                    )
                }
            }
        }

        // File content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    when {
                        isMobile -> 12.dp
                        isLaptop -> 16.dp
                        else -> 16.dp
                    }
                )
        ) {
            items(fileNode.content.lines()) { line ->
                CodeLine(
                    line = line,
                    isMobile = isMobile,
                    isLaptop = isLaptop
                )
            }
        }
    }
}

@Composable
private fun CodeLine(line: String, isMobile: Boolean, isLaptop: Boolean) {
    val annotatedText = buildAnnotatedString {
        if (line.isBlank()) {
            append(" ")
        } else {
            applySyntaxHighlighting(line)
        }
    }

    Text(
        text = annotatedText,
        style = when {
            isMobile -> MaterialTheme.typography.bodyLarge
            isLaptop -> MaterialTheme.typography.bodyLarge // Increased from bodyMedium
            else -> MaterialTheme.typography.bodyLarge // Increased from bodyMedium
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = when {
                    isMobile -> 2.dp
                    isLaptop -> 2.dp
                    else -> 2.dp
                }
            ),
        lineHeight = when {
            isMobile -> 24.sp
            isLaptop -> 24.sp // Increased from 20.sp
            else -> 24.sp // Increased from 20.sp
        }
    )
}

private fun androidx.compose.ui.text.AnnotatedString.Builder.applySyntaxHighlighting(line: String) {
    when {
        line.trimStart().startsWith("//") || line.trimStart().startsWith("*") || line.trimStart().startsWith("/**") -> {
            // Comments
            withStyle(SpanStyle(color = DarculaColors.Comment)) {
                append(line)
            }
        }
        line.contains("package ") || line.contains("import ") -> {
            // Package and import statements
            val parts = line.split(" ")
            parts.forEachIndexed { index, part ->
                when (part) {
                    "package", "import" -> {
                        withStyle(SpanStyle(color = DarculaColors.Keyword)) {
                            append(part)
                        }
                    }
                    else -> {
                        withStyle(SpanStyle(color = DarculaColors.OnBackground)) {
                            append(part)
                        }
                    }
                }
                if (index < parts.size - 1) append(" ")
            }
        }
        else -> {
            // General syntax highlighting
            highlightKotlinSyntax(line)
        }
    }
}

private fun androidx.compose.ui.text.AnnotatedString.Builder.highlightKotlinSyntax(line: String) {
    val keywords = setOf(
        "class", "object", "interface", "fun", "val", "var", "if", "else", "when", "for", "while",
        "return", "package", "import", "data", "sealed", "enum", "companion", "const", "private",
        "public", "internal", "protected", "override", "open", "abstract", "final", "suspend"
    )

    val tokens = line.split(Regex("\\s+|(?=[{}()\\[\\];,.])|(?<=[{}()\\[\\];,.])")).filter { it.isNotEmpty() }
    var currentIndex = 0

    for (token in tokens) {
        val tokenStart = line.indexOf(token, currentIndex)
        if (tokenStart > currentIndex) {
            // Add whitespace before token
            append(line.substring(currentIndex, tokenStart))
        }

        when {
            keywords.contains(token) -> {
                withStyle(SpanStyle(color = DarculaColors.Keyword, fontWeight = FontWeight.Bold)) {
                    append(token)
                }
            }
            token.startsWith("\"") && token.endsWith("\"") -> {
                withStyle(SpanStyle(color = DarculaColors.String)) {
                    append(token)
                }
            }
            token.matches(Regex("\\d+")) -> {
                withStyle(SpanStyle(color = DarculaColors.Number)) {
                    append(token)
                }
            }
            token.matches(Regex("[A-Z][a-zA-Z0-9]*")) -> {
                withStyle(SpanStyle(color = DarculaColors.Type)) {
                    append(token)
                }
            }
            else -> {
                withStyle(SpanStyle(color = DarculaColors.OnBackground)) {
                    append(token)
                }
            }
        }

        currentIndex = tokenStart + token.length
    }

    // Add any remaining characters
    if (currentIndex < line.length) {
        append(line.substring(currentIndex))
    }
}

@Composable
private fun WelcomeScreen(isMobile: Boolean, isLaptop: Boolean, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                when {
                    isMobile -> 24.dp
                    isLaptop -> 32.dp
                    else -> 32.dp
                }
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Code,
            contentDescription = null,
            tint = DarculaColors.Primary,
            modifier = Modifier.size(
                when {
                    isMobile -> 80.dp
                    isLaptop -> 72.dp
                    else -> 72.dp
                }
            )
        )

        Spacer(modifier = Modifier.height(
            when {
                isMobile -> 20.dp
                isLaptop -> 18.dp
                else -> 18.dp
            }
        ))

        Text(
            text = "Yashvant Yadav Portfolio",
            style = when {
                isMobile -> MaterialTheme.typography.displayLarge
                isLaptop -> MaterialTheme.typography.displayMedium
                else -> MaterialTheme.typography.displayMedium
            },
            color = DarculaColors.OnBackground,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(modifier = Modifier.height(
            when {
                isMobile -> 12.dp
                isLaptop -> 10.dp
                else -> 10.dp
            }
        ))

        Text(
            text = "Android Developer",
            style = when {
                isMobile -> MaterialTheme.typography.titleMedium
                isLaptop -> MaterialTheme.typography.titleMedium
                else -> MaterialTheme.typography.titleMedium // Increased from bodyLarge
            },
            color = DarculaColors.Comment
        )

        Spacer(modifier = Modifier.height(
            when {
                isMobile -> 32.dp
                isLaptop -> 28.dp
                else -> 28.dp
            }
        ))

        Text(
            text = if (isMobile) "Tap the menu to explore files" else "Select a file from the explorer to get started",
            style = when {
                isMobile -> MaterialTheme.typography.bodyLarge
                isLaptop -> MaterialTheme.typography.bodyLarge // Increased from bodyMedium
                else -> MaterialTheme.typography.bodyLarge // Increased from bodyMedium
            },
            color = DarculaColors.Comment,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}

private fun getTabIcon(fileNode: FileNode) = when {
    fileNode.type == FileType.FOLDER -> Icons.Default.Folder
    fileNode.name.endsWith(".kt") -> Icons.Default.Code
    fileNode.name.endsWith(".md") -> Icons.Default.Description
    else -> Icons.Default.InsertDriveFile
}

private fun getTabIconColor(fileNode: FileNode) = when {
    fileNode.type == FileType.FOLDER -> DarculaColors.FolderIcon
    fileNode.name.endsWith(".kt") -> DarculaColors.KotlinFileIcon
    fileNode.name.endsWith(".md") -> DarculaColors.MarkdownFileIcon
    else -> DarculaColors.OnSurface
}
