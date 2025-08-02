package org.yashvant.portofolio.viewmodel

import androidx.compose.runtime.*
import org.yashvant.portofolio.data.*

class PortfolioViewModel {

    private val _fileTree = mutableStateOf(createFileTree())
    val fileTree: State<FileNode> = _fileTree

    private val _openTabs = mutableStateOf<List<TabItem>>(emptyList())
    val openTabs: State<List<TabItem>> = _openTabs

    private val _activeTab = mutableStateOf<FileNode?>(null)
    val activeTab: State<FileNode?> = _activeTab

    private val _showProjectDialog = mutableStateOf(false)
    val showProjectDialog: State<Boolean> = _showProjectDialog

    private val _selectedProject = mutableStateOf<Project?>(null)
    val selectedProject: State<Project?> = _selectedProject

    init {
        // Open README.md by default
        val readmeFile = findReadmeFile(fileTree.value)
        readmeFile?.let { onFileClicked(it) }
    }

    fun onFileClicked(file: FileNode) {
        if (file.type == FileType.FOLDER) {
            toggleFolder(file)
        } else {
            openFile(file)
        }
    }

    fun onTabClosed(file: FileNode) {
        val updatedTabs = _openTabs.value.filter { it.fileNode != file }
        _openTabs.value = updatedTabs

        if (_activeTab.value == file) {
            _activeTab.value = updatedTabs.lastOrNull()?.fileNode
        }
    }

    fun onTabClicked(file: FileNode) {
        _activeTab.value = file
        updateTabsActiveState()
    }

    fun onRunProjectClicked(project: Project) {
        _selectedProject.value = project
        _showProjectDialog.value = true
    }

    fun onCloseProjectDialog() {
        _showProjectDialog.value = false
        _selectedProject.value = null
    }

    private fun openFile(file: FileNode) {
        val existingTab = _openTabs.value.find { it.fileNode == file }
        if (existingTab == null) {
            val newTab = TabItem(file)
            _openTabs.value = _openTabs.value + newTab
        }
        _activeTab.value = file
        updateTabsActiveState()
    }

    private fun updateTabsActiveState() {
        _openTabs.value = _openTabs.value.map { tab ->
            tab.copy(isActive = tab.fileNode == _activeTab.value)
        }
    }

    private fun toggleFolder(folder: FileNode) {
        _fileTree.value = toggleFolderInTree(_fileTree.value, folder)
    }

    private fun toggleFolderInTree(node: FileNode, target: FileNode): FileNode {
        if (node == target) {
            return node.copy(isExpanded = !node.isExpanded)
        }
        return node.copy(children = node.children.map { toggleFolderInTree(it, target) })
    }

    private fun findReadmeFile(node: FileNode): FileNode? {
        if (node.name == "README.md") return node
        for (child in node.children) {
            val found = findReadmeFile(child)
            if (found != null) return found
        }
        return null
    }

    private fun createFileTree(): FileNode {
        return FileNode(
            name = "portfolio",
            type = FileType.FOLDER,
            isExpanded = true,
            children = listOf(
                createExperienceFolder(),
                createProjectsFolder(),
                createSkillsFolder(),
                createAchievementsFile(),
                createReadmeFile()
            )
        )
    }

    private fun createExperienceFolder(): FileNode {
        return FileNode(
            name = "experience",
            type = FileType.FOLDER,
            children = listOf(
                FileNode(
                    name = "AGOMUC.kt",
                    type = FileType.KOTLIN_FILE,
                    content = """/**
 * AGOMUC - Kotlin Intern Experience
 * 
 * Company: AGOMUC
 * Duration: May 2025 – Present
 * Location: Hybrid
 * 
 * Key Responsibilities:
 * - Worked with experienced developers to develop android apps in Jetpack compose
 * - Developed apps with Kiosk and sensor connection features
 * 
 * Technologies: Kotlin, Jetpack Compose, Android SDK, Sensor Integration
 */

package org.yashvant.portfolio.experience

class AGOMUCExperience {
    companion object {
        const val ROLE = "Kotlin Intern (Paid)"
        const val COMPANY = "AGOMUC"
        const val DURATION = "May 2025 – Present"
        const val LOCATION = "Hybrid"
        
        val responsibilities = listOf(
            "Worked with experienced developers to develop android apps in Jetpack compose",
            "Developed apps with Kiosk and sensor connection features"
        )
        
        val technologies = listOf(
            "Kotlin",
            "Jetpack Compose", 
            "Android SDK",
            "Sensor Integration",
            "Kiosk Applications"
        )
    }
}"""
                ),
                FileNode(
                    name = "DAPS.kt",
                    type = FileType.KOTLIN_FILE,
                    content = """/**
 * DAPS - Android Developer Intern Experience
 * 
 * Company: DAPS
 * Duration: October 2024 – December 2024
 * Location: Remote
 * 
 * Key Responsibilities:
 * - Created UI forms for apps as per client requirement
 * - Integrated 10+ backend APIs to the frontend of different apps
 * - Collaborated with a team of 10+ developers to create apps as per requirements
 * 
 * Technologies: Kotlin, Android SDK, REST APIs, UI/UX Development
 */

package org.yashvant.portfolio.experience

class DAPSExperience {
    companion object {
        const val ROLE = "Android Developer Intern (Paid)"
        const val COMPANY = "DAPS"
        const val DURATION = "October 2024 – December 2024"
        const val LOCATION = "Remote"
        
        val responsibilities = listOf(
            "Created UI forms for apps as per client requirement",
            "Integrated 10+ backend APIs to the frontend of different apps",
            "Collaborated with a team of 10+ developers to create apps as per requirements"
        )
        
        val technologies = listOf(
            "Kotlin",
            "Android SDK",
            "REST APIs", 
            "UI/UX Development",
            "Team Collaboration"
        )
    }
}"""
                ),
                createExtracurricularFolder()
            )
        )
    }

    private fun createExtracurricularFolder(): FileNode {
        return FileNode(
            name = "extracurricular",
            type = FileType.FOLDER,
            children = listOf(
                FileNode(
                    name = "DSC_KIET.kt",
                    type = FileType.KOTLIN_FILE,
                    content = """/**
 * DSC KIET - Android Lead Experience
 * 
 * Organization: Developer Student Clubs (DSC) KIET
 * Duration: March 2023 – Present
 * Role: Android Lead
 * Institution: KIET Group of Institutions
 * 
 * Key Contributions:
 * - Collaborated with a 7-member team on Android development projects
 * - Participated in hackathons, tech talks, coding contests, and KotlinConf Global
 * - Organized an offline hackathon, a 4-day Android bootcamp with 30+ attendees, and a Git/GitHub workshop
 * 
 * Leadership & Impact: Led Android development initiatives and mentored fellow students
 */

package org.yashvant.portfolio.experience.extracurricular

class DSCKIETExperience {
    companion object {
        const val ROLE = "Android Lead"
        const val ORGANIZATION = "DSC KIET"
        const val DURATION = "March 2023 – Present"
        const val INSTITUTION = "KIET Group of Institutions"
        
        val achievements = listOf(
            "Collaborated with a 7-member team on Android development projects",
            "Participated in hackathons, tech talks, coding contests, and KotlinConf Global",
            "Organized an offline hackathon with significant participation",
            "Conducted a 4-day Android bootcamp with 30+ attendees",
            "Led Git/GitHub workshop for fellow students"
        )
    }
}"""
                ),
                FileNode(
                    name = "NSCC_KIET.kt",
                    type = FileType.KOTLIN_FILE,
                    content = """/**
 * NSCC KIET - Java Team Member Experience
 * 
 * Organization: Newton School Coding Club (NSCC) KIET
 * Duration: March 2024 – Present
 * Role: Java Team Member
 * Institution: KIET Group of Institutions
 * 
 * Key Contributions:
 * - Built projects and held lectures on Spring Boot teaching juniors
 * - Focused on backend development and Java ecosystem
 * 
 * Technologies: Java, Spring Boot, Backend Development
 */

package org.yashvant.portfolio.experience.extracurricular

class NSCCKIETExperience {
    companion object {
        const val ROLE = "Java Team Member"
        const val ORGANIZATION = "NSCC KIET"
        const val DURATION = "March 2024 – Present"
        const val INSTITUTION = "KIET Group of Institutions"
        
        val contributions = listOf(
            "Built projects using Java and Spring Boot",
            "Held lectures on Spring Boot framework",
            "Taught and mentored junior students",
            "Contributed to backend development initiatives"
        )
    }
}"""
                ),
                FileNode(
                    name = "FOSSCU.kt",
                    type = FileType.KOTLIN_FILE,
                    content = """/**
 * FOSSCU - Spring Boot/Android Developer Experience
 * 
 * Organization: Free and Open Source Software Community (FOSSCU)
 * Duration: April 2024 – Present
 * Role: Spring Boot/Android Developer
 * Institution: KIET Group of Institutions
 * 
 * Key Contributions:
 * - Contributed to 5+ open-source projects involving Spring Boot and Android
 * - Active participant in open source community initiatives
 * 
 * Technologies: Spring Boot, Android, Open Source Development
 */

package org.yashvant.portfolio.experience.extracurricular

class FOSSCUExperience {
    companion object {
        const val ROLE = "Spring Boot/Android Developer"
        const val ORGANIZATION = "FOSSCU"
        const val DURATION = "April 2024 – Present"
        const val INSTITUTION = "KIET Group of Institutions"
        
        val contributions = listOf(
            "Contributed to 5+ open-source projects",
            "Specialized in Spring Boot backend development",
            "Android application development",
            "Active community participant and contributor"
        )
    }
}"""
                )
            )
        )
    }

    private fun createProjectsFolder(): FileNode {
        val zyptraProject = Project(
            name = "Zyptra",
            description = "Developed a full-fledged Android application backup solution allowing users to extract and backup APKs to Google Drive",
            techStack = "Kotlin, Jetpack Compose, Clean Architecture, Google Drive API, Hilt",
            link = "https://github.com/yashvant/zyptra",
            demoType = ProjectDemoType.APP_BACKUP
        )

        val imgcProject = Project(
            name = "ImGC",
            description = "Developed an app allowing users to receive contextual descriptions and perform conversational Q&A about any image from their gallery",
            techStack = "Kotlin, Jetpack Compose, Gemini-Vision API, MVVM",
            link = "https://github.com/yashvant/imgc",
            demoType = ProjectDemoType.IMAGE_ANALYSIS
        )

        val codevProject = Project(
            name = "Codev",
            description = "Built a collaborative platform for developers, enabling users to share and showcase projects with project matching and team-formation features",
            techStack = "Kotlin, Jetpack Compose, Firebase, Dagger-Hilt, MVVM",
            link = "https://github.com/yashvant/codev",
            demoType = ProjectDemoType.GITHUB_LINK
        )

        val diaenshoProject = Project(
            name = "Diaensho",
            description = "Engineered an AI-powered audio diary to capture and summarize spoken thoughts, featuring an always-on hotword listener built with a persistent Foreground Service",
            techStack = "Kotlin, Jetpack Compose, Coroutines, Hilt, Room, WorkManager",
            link = "https://github.com/yashvant/diaensho",
            demoType = ProjectDemoType.GITHUB_LINK
        )

        return FileNode(
            name = "projects",
            type = FileType.FOLDER,
            isExpanded = true,
            children = listOf(
                FileNode(
                    name = "Zyptra.kt",
                    type = FileType.KOTLIN_FILE,
                    hasRunButton = true,
                    project = zyptraProject,
                    content = """/**
 * Zyptra - Android Application Backup Solution
 * Project Duration: May 2025 - June 2025
 * 
 * Description:
 * Developed a full-fledged Android application backup solution allowing users to extract 
 * and backup APKs to Google Drive. Engineered the app using Clean Architecture principles 
 * with MVVM for a highly scalable and maintainable codebase.
 * 
 * Features:
 * - Extract and backup APK files from installed applications
 * - Google Drive integration for cloud storage
 * - Clean Architecture with MVVM pattern
 * - Dependency injection using Hilt
 * - Material Design UI with Jetpack Compose
 * 
 * Tech Stack: Kotlin, Jetpack Compose, Clean Architecture, Google Drive API, Hilt
 */

package org.yashvant.portfolio.projects

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*

class ZyptraProject {
    companion object {
        const val NAME = "Zyptra"
        const val DURATION = "May 2025 - June 2025"
        const val DESCRIPTION = "Android Application Backup Solution"
        const val TECH_STACK = "Kotlin, Jetpack Compose, Clean Architecture, Google Drive API, Hilt"
    }
    
    fun extractApks(): List<String> {
        // APK extraction logic would be implemented here
        return listOf("app1.apk", "app2.apk", "app3.apk")
    }
    
    fun backupToGoogleDrive(apks: List<String>) {
        // Google Drive backup logic would be implemented here
        println("Backing up ${"$"}{apks.size} APKs to Google Drive...")
    }
}"""
                ),
                FileNode(
                    name = "ImGC.kt",
                    type = FileType.KOTLIN_FILE,
                    hasRunButton = true,
                    project = imgcProject,
                    content = """/**
 * ImGC - Image Analysis with Gemini Vision API
 * Project Duration: March 2024 - April 2024
 * 
 * Description:
 * Developed an app allowing users to receive contextual descriptions and perform 
 * conversational Q&A about any image from their gallery. Integrated the Google 
 * Gemini-Vision Pro API to power the core visual understanding and analysis features.
 * 
 * Features:
 * - Image selection from gallery
 * - Contextual image descriptions using Gemini Vision
 * - Conversational Q&A about image content
 * - Real-time image analysis
 * - MVVM architecture for clean code structure
 * 
 * Tech Stack: Kotlin, Jetpack Compose, Gemini-Vision API, MVVM
 */

package org.yashvant.portfolio.projects

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*

class ImGCProject {
    companion object {
        const val NAME = "ImGC"
        const val DURATION = "March 2024 - April 2024"
        const val DESCRIPTION = "Image Analysis with Gemini Vision API"
        const val TECH_STACK = "Kotlin, Jetpack Compose, Gemini-Vision API, MVVM"
    }
    
    suspend fun analyzeImage(imageUri: String): String {
        // Gemini Vision API integration would be implemented here
        return "Image analysis completed successfully"
    }
    
    fun generateImageDescription(imageData: ByteArray): String {
        // Image description generation logic
        return "Generated contextual description of the image"
    }
}"""
                ),
                FileNode(
                    name = "Codev.kt",
                    type = FileType.KOTLIN_FILE,
                    hasRunButton = true,
                    project = codevProject,
                    content = """/**
 * Codev - Collaborative Development Platform
 * Project Duration: November 2023 - April 2024
 * 
 * Description:
 * Built a collaborative platform for developers, enabling users to share and showcase 
 * projects. Enhanced collaboration by introducing project matching and team-formation 
 * features for better developer networking.
 * 
 * Features:
 * - Developer project showcase platform
 * - Project matching algorithm
 * - Team formation features
 * - Real-time collaboration tools
 * - Firebase backend integration
 * 
 * Tech Stack: Kotlin, Jetpack Compose, Firebase, Dagger-Hilt, MVVM
 */

package org.yashvant.portfolio.projects

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*

class CodevProject {
    companion object {
        const val NAME = "Codev"
        const val DURATION = "November 2023 - April 2024"
        const val DESCRIPTION = "Collaborative Development Platform"
        const val TECH_STACK = "Kotlin, Jetpack Compose, Firebase, Dagger-Hilt, MVVM"
    }
    
    fun matchProjects(userSkills: List<String>): List<String> {
        // Project matching algorithm implementation
        return listOf("Android Project", "Web Development", "AI/ML Project")
    }
}"""
                ),
                FileNode(
                    name = "Diaensho.kt",
                    type = FileType.KOTLIN_FILE,
                    hasRunButton = true,
                    project = diaenshoProject,
                    content = """/**
 * Diaensho - AI-Powered Audio Diary
 * Project Duration: July 2025 - Present
 * 
 * Description:
 * Engineered an AI-powered audio diary to capture and summarize spoken thoughts, 
 * featuring an always-on hotword listener built with a persistent Foreground Service.
 * Implemented an offline-first architecture using Room and managed complex background 
 * tasks for habit tracking and data sync with WorkManager.
 * 
 * Features:
 * - AI-powered audio diary with speech recognition
 * - Always-on hotword listener with Foreground Service
 * - Offline-first architecture using Room database
 * - Background task management with WorkManager
 * - Habit tracking and data synchronization
 * 
 * Tech Stack: Kotlin, Jetpack Compose, Coroutines, Hilt, Room, WorkManager
 */

package org.yashvant.portfolio.projects

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*

class DiaenshoProject {
    companion object {
        const val NAME = "Diaensho"
        const val DURATION = "July 2025 - Present"
        const val DESCRIPTION = "AI-Powered Audio Diary"
        const val TECH_STACK = "Kotlin, Jetpack Compose, Coroutines, Hilt, Room, WorkManager"
    }
    
    fun startHotwordListener() {
        // Foreground service for hotword detection
        println("Hotword listener started in foreground service")
    }
    
    fun captureAndSummarize(audioData: ByteArray): String {
        // AI-powered audio processing and summarization
        return "Audio captured and summarized successfully"
    }
    
    fun syncData() {
        // WorkManager background sync implementation
        println("Data sync scheduled with WorkManager")
    }
}"""
                )
            )
        )
    }

    private fun createSkillsFolder(): FileNode {
        return FileNode(
            name = "skills",
            type = FileType.FOLDER,
            children = listOf(
                FileNode(
                    name = "languages.kt",
                    type = FileType.KOTLIN_FILE,
                    content = """/**
 * Programming Languages
 */

package org.yashvant.portfolio.skills

object ProgrammingLanguages {
    val languages = listOf(
        "Kotlin",
        "Java", 
        "C",
        "C++",
        "JavaScript",
        "Python",
        "Dart"
    )
    
    fun getExpertiseLevel(language: String): String {
        return when (language) {
            "Kotlin", "Java" -> "Expert"
            "Python", "JavaScript" -> "Advanced"
            "C", "C++", "Dart" -> "Intermediate"
            else -> "Beginner"
        }
    }
}"""
                ),
                FileNode(
                    name = "frameworks.kt",
                    type = FileType.KOTLIN_FILE,
                    content = """/**
 * Frameworks and Technologies
 */

package org.yashvant.portfolio.skills

object FrameworksAndTechnologies {
    val frameworks = listOf(
        "Android",
        "Full Stack Web",
        "Flutter",
        "Spring Boot",
        "Docker",
        "GitHub",
        "Firebase"
    )
    
    val webTechnologies = listOf(
        "HTML5",
        "CSS3",
        "JavaScript",
        "React",
        "Node.js"
    )
    
    val databases = listOf(
        "SQLite",
        "Room Database",
        "Firebase Firestore",
        "MySQL"
    )
}"""
                ),
                FileNode(
                    name = "tools.kt",
                    type = FileType.KOTLIN_FILE,
                    content = """/**
 * Development Tools
 */

package org.yashvant.portfolio.skills

object DevelopmentTools {
    val editors = listOf(
        "Vim",
        "VS Code",
        "IntelliJ IDEA",
        "Android Studio"
    )
    
    val versionControl = listOf(
        "Git",
        "GitHub",
        "GitLab"
    )
    
    val cloudPlatforms = listOf(
        "Google Cloud Platform (GCP)",
        "Firebase",
        "AWS (Basic)"
    )
    
    val designTools = listOf(
        "Figma",
        "Adobe XD",
        "Sketch"
    )
}"""
                )
            )
        )
    }

    private fun createAchievementsFile(): FileNode {
        return FileNode(
            name = "achievements.md",
            type = FileType.MARKDOWN_FILE,
            content = """# Achievements

## 🏆 Competitions & Recognition

### NASA Space Apps Challenge 2023 - Winner
- Won the prestigious NASA Space Apps Challenge 2023
- Developed innovative solution for space-related challenges
- Collaborated with international team of developers

### Competitive Programming
- **Codechef Rating**: Maximum rating of 1427
- **Problem Solving**: 500+ problems solved across platforms
  - LeetCode: Advanced problem solving
  - GeeksforGeeks: Data structures and algorithms
  - CodeChef: Competitive programming contests

## 📈 Technical Milestones

### Open Source Contributions
- Active contributor to Android and Kotlin communities
- Multiple projects showcased on GitHub
- Mentored junior developers in mobile app development

### Academic Excellence
- Strong academic performance in Computer Science
- Specialized in Mobile Application Development
- Research interests in AI/ML and Computer Vision
"""
        )
    }

    private fun createReadmeFile(): FileNode {
        return FileNode(
            name = "README.md",
            type = FileType.MARKDOWN_FILE,
            content = """# Yashvant Yadav - Android Developer Portfolio

## 👨‍💻 About Me

**Name**: Yashvant Yadav  
**Location**: Delhi NCR, Ghaziabad, 201206  
**Specialization**: Android Development  
**Education**: B.Tech in Computer Science (CGPA: 8.5/10) - KIET Group of Institutions (Nov 2022 - June 2026)

## 📞 Contact Information

**Email**: yashvantyadav855@gmail.com  
**Academic Email**: yashvant.2226cs1026@kiet.edu  
**Phone**: +91 8090161990  

## 🔗 Links

**LinkedIn**: [Connect with me on LinkedIn](https://linkedin.com/in/yashvant-yadav)  
**GitHub**: [View my repositories](https://github.com/yashvant-yadav)  

## 💼 Professional Summary

Passionate Android Developer with expertise in Kotlin and modern Android development practices. 
Currently working as a Kotlin Intern at AGOMUC, with previous experience at DAPS as an Android 
Developer Intern. Experienced in building scalable mobile applications using MVVM architecture, 
Jetpack Compose, and contemporary Android development tools.

## 🚀 Current Experience

- **Kotlin Intern (Paid)** at AGOMUC (May 2025 - Present) - Hybrid
- **Android Developer Intern (Paid)** at DAPS (Oct 2024 - Dec 2024) - Remote

## 🏆 Notable Achievements

- **NASA Space Apps Challenge 2023 - Winner**
- **CodeChef Max Rating**: 1427
- **Problem Solving**: 500+ problems solved on LeetCode, GeeksforGeeks, and CodeChef

## 🎯 Core Competencies

- **Languages**: Kotlin, Java, C, C++, JavaScript, Python, Dart
- **Technologies/Frameworks**: Android, Full Stack Web, Flutter, Spring Boot, Docker, GitHub, Firebase
- **Tools**: Vim, VS Code, IntelliJ IDEA, Android Studio, Git, GCP
- **Architecture**: MVVM, Clean Architecture, Dependency Injection
- **UI/UX**: Jetpack Compose, Material Design, Custom Views

## 📱 Featured Projects

### Zyptra (May 2025 - June 2025)
Android application backup solution with Google Drive integration
*Tech Stack: Kotlin, Jetpack Compose, Clean Architecture, Google Drive API, Hilt*

### ImGC (March 2024 - April 2024)  
Image analysis app powered by Gemini Vision API
*Tech Stack: Kotlin, Jetpack Compose, Gemini-Vision API, MVVM*

### Codev (November 2023 - April 2024)
Collaborative platform for developers with project matching features
*Tech Stack: Kotlin, Jetpack Compose, Firebase, Dagger-Hilt, MVVM*

### Diaensho (July 2025 - Present)
AI-powered audio diary with always-on hotword listener
*Tech Stack: Kotlin, Jetpack Compose, Coroutines, Hilt, Room, WorkManager*

---

*Welcome to my portfolio! Explore the file tree to learn more about my experience, projects, and skills.*
"""
        )
    }
}
