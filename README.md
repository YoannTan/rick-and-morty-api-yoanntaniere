# Rick and Morty API – KMP App

This application is a demonstration of a clean architecture project built with **Kotlin Multiplatform (KMP)** using data from the Rick & Morty API. It is built with **Jetpack Compose** on Android and partially supports Desktop.

## Project Architecture

The project is structured following **Clean Architecture principles**, with separation between **domain**, **data**, and **ui** layers. It supports both **Android** and **Desktop** platforms.

### commonMain

Contains **shared code** between platforms (Android, Desktop).

#### domain/

- Contains **business logic**.
- Defines **repository interfaces** (e.g., `CharacterRepository`, `LocationRepository`).
- Contains **domain models** (`Character`, `Episode`, `Location`, etc.).

#### data/

- Contains **data sources** (local and remote).
- `local/objects/`: Room entities (e.g., `CharacterObject`).
- `remote/responses/`: API responses (e.g., `CharacterResponse`).
- `repositories/`: Concrete implementations of repositories.
- `validators/`: Data validators.

#### ui/

- Shared UI components.
- `core/composables/`: Reusable UI elements (`CharacterCard`, `LocationCard`, etc.).
- `screens/`: Organized by screen features (`characterdetails`, `locationdetails`, etc.).
- `extensions/`, `theme/`, `Navigation.kt`: UI theming, utilities, and navigation setup.

#### common/

- Utility functions used across platforms (e.g., `SoundPlayer`, `Flows.kt`, etc.).

### androidMain

Platform-specific code for **Android**:

- `ui/`: Contains `MainActivity`, `Application` class.
- `data/`: Room configuration and Android-specific implementations.
- `SoundPlayer.kt`: Manages audio playback using `MediaPlayer`.
- `res/raw/portal_click_sound.mp3`: Sound played on specific UI actions.
- `DataModule.android.kt`: Android Koin dependency module.

### desktopMain

Platform-specific code for **Desktop**:

- Desktop-specific `HttpClient` implementation.
- Koin dependency module for Desktop.

## Key Concepts

- **Layered architecture** (domain, data, ui)
- **Koin** for dependency injection
- **Room** for local persistence
- **Ktor** for networking
- **Jetpack Compose** for UI
- **KMP** for shared business logic

## Features

- Character list and detailed views
- Navigation between characters, episodes, and locations
- Caching with Room
- Sound played on certain interactions
- Dynamic loading of location residents

## Getting Started

1. Clone the repository
2. Open in Android Studio with KMP support
3. Run the Android configuration (`MainActivity`)
4. (Optional) Run the Desktop version from `desktopMain/Main.kt`