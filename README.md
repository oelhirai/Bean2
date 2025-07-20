# Bean2 - Coffee Journal App

Bean2 is an Android application built with Jetpack Compose that helps coffee enthusiasts track and manage their coffee beans, roasts, and brewing parameters. The app provides a clean, modern interface for logging coffee bags, recording tasting notes, and tracking your favorite brewing recipes.

## Features

- **Coffee Bag Management**: Add, view, edit, and delete coffee bags with details like name, roaster, and coffee type
- **Brewing Parameters**: Track specific brewing parameters for different coffee types (Pour Over, Espresso, etc.)
- **Tasting Notes**: Record your thoughts and ratings for each coffee
- **Filtering**: Filter your coffee bags by type for easy organization
- **Dark/Light Theme**: Supports both light and dark themes for comfortable viewing in any lighting condition

## Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose with Material 3 Design
- **Architecture**: MVVM (Model-View-ViewModel)
- **Dependency Injection**: Hilt
- **Local Database**: Room
- **Navigation**: Compose Navigation
- **Asynchronous**: Kotlin Coroutines & Flow
- **JSON Parsing**: Moshi

## Setup

1. Clone the repository
2. Open the project in Android Studio (Flamingo or later recommended)
3. Sync the project with Gradle files
4. Build and run the app on an emulator or physical device

## Requirements

- Android Studio Flamingo (2022.2.1) or later
- Android SDK 33 or higher
- Kotlin 1.8.0 or higher
- JDK 11 or higher

## Screenshots

*Screenshots will be added in a future update*

## TODO List

### Features to Add

1. **Coffee Bag Management**
   - Dropdown selection for common coffee-to-water ratios
   - Backup and restore functionality
     - Export coffee bag data to a backup file
     - Import from backup to restore data

2. **UI Improvements**
   - Automatic screen adjustment when keyboard is shown
   - Prevent text squishing in list view
     - Add ellipsis for long coffee names
     - Ensure consistent spacing for primary details (grind, temp, ratio)

3. **Brewing Parameters**
   - Add visual indicators for different brew methods (espresso/pour over icons)
   - Standardize ratio input with common presets

### Bug Fixes & Improvements

1. **UI/UX**
   - Add espresso and pour over icons in list view for quick visual identification
   - Fix text layout in list items to prevent squishing of primary details
   - Improve form validation and error messages

2. **Performance**
   - Optimize database queries for faster loading
   - Implement proper state management for forms

3. **Testing**
   - Add unit tests for ViewModels
   - Add UI tests for critical user flows

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
