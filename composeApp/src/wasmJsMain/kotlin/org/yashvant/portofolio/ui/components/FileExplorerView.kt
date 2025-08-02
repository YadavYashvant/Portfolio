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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.yashvant.portofolio.data.FileNode
import org.yashvant.portofolio.data.FileType
import org.yashvant.portofolio.ui.theme.DarculaColors

@Composable
fun FileExplorerView(
    fileTree: FileNode,
    onFileClicked: (FileNode) -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier
    ) {
        val isMobile = maxWidth < 600.dp

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .then(
                    if (isMobile) Modifier.fillMaxWidth()
                    else Modifier.widthIn(min = 250.dp, max = 350.dp)
                )
                .background(DarculaColors.Surface)
                .border(1.dp, DarculaColors.TabBorder)
        ) {
            // Explorer header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DarculaColors.SurfaceVariant)
                    .padding(if (isMobile) 12.dp else 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Folder,
                    contentDescription = "Explorer",
                    tint = DarculaColors.FolderIcon,
                    modifier = Modifier.size(if (isMobile) 20.dp else 16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "EXPLORER",
                    style = if (isMobile) MaterialTheme.typography.titleSmall else MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = DarculaColors.OnSurface
                )
            }

            // File tree
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = if (isMobile) PaddingValues(4.dp) else PaddingValues(0.dp)
            ) {
                items(flattenFileTree(fileTree)) { (node, depth) ->
                    FileTreeItem(
                        fileNode = node,
                        depth = depth,
                        isMobile = isMobile,
                        onClicked = { onFileClicked(node) }
                    )
                }
            }
        }
    }
}

@Composable
private fun FileTreeItem(
    fileNode: FileNode,
    depth: Int,
    isMobile: Boolean,
    onClicked: () -> Unit
) {
    val itemHeight = if (isMobile) 32.dp else 24.dp
    val indentSize = if (isMobile) 24.dp else 20.dp
    val iconSize = if (isMobile) 20.dp else 16.dp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClicked() }
            .padding(
                start = (depth * indentSize.value).dp,
                top = if (isMobile) 4.dp else 2.dp,
                bottom = if (isMobile) 4.dp else 2.dp,
                end = if (isMobile) 12.dp else 8.dp
            )
            .height(itemHeight),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Folder expand/collapse icon
        if (fileNode.type == FileType.FOLDER) {
            Icon(
                imageVector = if (fileNode.isExpanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowRight,
                contentDescription = if (fileNode.isExpanded) "Collapse" else "Expand",
                tint = DarculaColors.OnSurface,
                modifier = Modifier.size(iconSize)
            )
        } else {
            Spacer(modifier = Modifier.width(iconSize))
        }
        
        Spacer(modifier = Modifier.width(4.dp))
        
        // File/folder icon
        Icon(
            imageVector = getFileIcon(fileNode),
            contentDescription = null,
            tint = getFileIconColor(fileNode),
            modifier = Modifier.size(iconSize)
        )
        
        Spacer(modifier = Modifier.width(8.dp))
        
        // File/folder name
        Text(
            text = fileNode.name,
            style = if (isMobile) MaterialTheme.typography.bodyLarge else MaterialTheme.typography.bodyMedium,
            color = DarculaColors.OnSurface,
            maxLines = 1,
            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
        )
    }
}

private fun getFileIcon(fileNode: FileNode): ImageVector {
    return when {
        fileNode.type == FileType.FOLDER -> Icons.Default.Folder
        fileNode.name.endsWith(".kt") -> Icons.Default.Code
        fileNode.name.endsWith(".md") -> Icons.Default.Description
        else -> Icons.Default.InsertDriveFile
    }
}

private fun getFileIconColor(fileNode: FileNode): Color {
    return when {
        fileNode.type == FileType.FOLDER -> DarculaColors.FolderIcon
        fileNode.name.endsWith(".kt") -> DarculaColors.KotlinFileIcon
        fileNode.name.endsWith(".md") -> DarculaColors.MarkdownFileIcon
        else -> DarculaColors.OnSurface
    }
}

private fun flattenFileTree(node: FileNode, depth: Int = 0): List<Pair<FileNode, Int>> {
    val result = mutableListOf<Pair<FileNode, Int>>()
    result.add(node to depth)
    
    if (node.type == FileType.FOLDER && node.isExpanded) {
        node.children.forEach { child ->
            result.addAll(flattenFileTree(child, depth + 1))
        }
    }
    
    return result
}
