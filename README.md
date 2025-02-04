# SnapQuest

SnapQuest turns everyday photography into a fun, rewarding challenge.

## Overview

This Android application provides users a list of quest and their details. The app demonstrates modern Android development practices, clean architecture principles, and effective use of the latest Android technologies.

## Features

- **Available Quests Screen**
    - Displays today's quest and list of past quests

- **Details Screen**
    - Comprehensive quest information
    - User can join the contest by uploading a photo
    - most liked photo gets the spotlight
    - other submissions can be browsed

- **Fullscreen Screen**
    - Fullscreen image
    - Smooth navigation back to Details

## Technical Specifications

### Architecture
- MVVM (Model-View-ViewModel) architecture pattern
- Clean Architecture principles
- Repository pattern for data management
- Navigation Compose

### Technologies & Libraries
- **Language**: 100% Kotlin
- **UI**: Jetpack Compose
- **Dependency Injection**: Hilt
- **Asynchronous Programming**: Kotlin Coroutines
- **Image Loading**: Coil
- **Navigation**: Jetpack Navigation Compose
- **Testing**: JUnit4, Mockk

## Project Setup

1. Clone the repository
```bash
git clone https://github.com/renaudfavier/SnapQuest
```

2. Open the project in Android Studio

3. Make sure it correctly created `local.properties` directing to your Android SDK, you will need version 35

4. Build and run the project
```bash
./gradlew build
```

## Running Tests

Execute the following commands to run tests:

```bash
# Unit tests
./gradlew test

# Instrumentation tests
./gradlew connectedAndroidTest
```

## Project Structure

```
core/
├── domain/
├── di/
├── presentation/
│   ├── component/
│   └── util/
└── 
quest/
├── data/
├── domain/
│   └── model/
└── presentation/
    ├── component/
    ├── detail/
    ├── fullscreen/
    └── list/
ui/
└── theme/
```

## Contact

Renaud Favier - renaud.favier.blimer@gmail.com
