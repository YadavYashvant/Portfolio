package org.yashvant.portofolio.data

data class Project(
    val name: String,
    val description: String,
    val techStack: String,
    val link: String,
    val demoType: ProjectDemoType = ProjectDemoType.LINK
)

data class Experience(
    val role: String,
    val company: String,
    val dates: String,
    val points: List<String>
)

data class Achievement(
    val description: String
)

data class FileNode(
    val name: String,
    val type: FileType,
    val content: String = "",
    val children: List<FileNode> = emptyList(),
    val isExpanded: Boolean = false,
    val hasRunButton: Boolean = false,
    val project: Project? = null
)

enum class FileType {
    FOLDER,
    KOTLIN_FILE,
    MARKDOWN_FILE
}

enum class ProjectDemoType {
    LINK,
    IMAGE_ANALYSIS,
    APP_BACKUP,
    GITHUB_LINK
}

data class TabItem(
    val fileNode: FileNode,
    val isActive: Boolean = false
)
