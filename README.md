# Yashvant Yadav - Android Developer Portfolio

A personal portfolio website built with Kotlin and Compose for Web, designed to replicate the Android Studio IDE experience with the Darcula theme.

## Features

- **Android Studio-like Interface**: Complete replica of the Android Studio IDE with Darcula theme
- **File Explorer**: Interactive file tree with expandable folders
- **Code Editor**: Syntax highlighting for Kotlin and Markdown files
- **Project Demos**: Interactive project demonstrations with modal dialogs
- **Responsive Design**: Optimized for desktop viewing
- **Kotlin-First**: Built entirely with Kotlin and Compose for Web

## Technology Stack

- **Language**: Kotlin
- **UI Framework**: Compose for Web
- **Architecture**: MVVM (Model-View-ViewModel)
- **Compilation Target**: Kotlin/Wasm
- **Theme**: Android Studio Darcula

## Project Structure

```
src/wasmJsMain/kotlin/
├── data/
│   └── Models.kt              # Data classes for portfolio content
├── view/
│   ├── FileExplorerView.kt    # File tree component
│   ├── EditorView.kt          # Code editor with tabs
│   ├── StatusBarView.kt       # Status bar component
│   ├── ProjectDemoDialog.kt   # Project demo modal
│   └── PortfolioRootView.kt   # Main layout orchestrator
├── viewmodel/
│   └── PortfolioViewModel.kt  # MVVM ViewModel
└── Main.kt                    # Application entry point
```

## Build Instructions

### Prerequisites

- Java 11 or higher
- Gradle 8.4 or higher

### Building the Project

1. Clone the repository:
```bash
git clone <repository-url>
cd portfolio
```

2. Build the project:
```bash
./gradlew build
```

3. Run the development server:
```bash
./gradlew wasmJsBrowserDevelopmentRun
```

4. Open your browser and navigate to `http://localhost:8080`

### Building for Production

To build the production version:

```bash
./gradlew wasmJsBrowserProductionRun
```

The built files will be in `build/distributions/` and can be deployed to any static web host.

## Portfolio Content

The portfolio includes:

- **Personal Information**: Contact details and professional links
- **Experience**: Internship details at AGOMUC and DAPS
- **Projects**: 
  - Zyptra (Android app backup solution)
  - ImGC (Image analysis with Gemini Vision API)
  - Codev (Collaborative code development platform)
  - Diaensho (Digital fashion platform)
- **Skills**: Programming languages, frameworks, and tools
- **Achievements**: Competition wins and problem-solving records

## Interactive Features

- **File Navigation**: Click on files in the explorer to open them
- **Tab Management**: Open multiple files with closeable tabs
- **Project Demos**: Click "Run Project" buttons to see interactive demos
- **Syntax Highlighting**: Kotlin and Markdown files with proper highlighting

## Deployment

The built application consists of static files that can be deployed to:

- GitHub Pages
- Netlify
- Vercel
- Any static web hosting service

## License

MIT License - see LICENSE file for details.