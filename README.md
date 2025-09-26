# Square Repos - GitHub Repository Explorer

A modern Android application that showcases Square Inc.'s open-source repositories from GitHub with beautiful animations, offline support, and bookmark functionality.

## Features

### Core Functionality
- Repository Listing: Browse all Square Inc. repositories with pagination support
- Repository Details: Detailed view with star count and bookmark management
- Offline Support: Full offline capability with local database
- Smart Pagination: Automatic loading of next pages with smooth scrolling

### Bookmark System
- Add/Remove Bookmarks: One-tap bookmarking from details screen
- Visual Indicators: Bookmarked repositories show a star icon in the list
- Local Storage: Bookmarks persist locally on device

## Technical Implementation

### Architecture
- MVVM Architecture with Clean Architecture principles
- Repository Pattern for single source of truth
- Dependency Injection for managed dependencies

### Data Flow
GitHub API → Repository Layer → ViewModel → Compose UI
Network operations are cached locally and displayed reactively from database

### Key Features Implementation

#### Repository List Screen
- Infinite scrolling with automatic pagination
- Visual bookmark indicators
- Network state awareness
- Offline-first approach

#### Repository Details Screen
- Reactive data reading from local database
- Bookmark toggle functionality
- Smooth shared element transitions
- Offline capability

#### Bookmark System
- Local persistence using Room database
- Reactive UI updates across screens
- Seamless add/remove operations
- Consistent state management


## Project Standards

### Code Quality
- Clean, documented code following best practices
- Scalable architecture patterns
- Proper separation of concerns
- Maintainable and testable code structure

### Modern Android Development
- 100% Kotlin with Jetpack Compose
- Coroutines for asynchronous operations
- Modern Jetpack libraries integration
- Material Design 3 implementation

### Scalability Considerations
- Modular architecture ready for feature expansion
- Configurable components for easy maintenance
- Extensible design patterns
- Future-proof technology choices

## User Journey

1. Launch app to view Square's repositories
2. Scroll seamlessly through paginated list
3. Tap any repository for detailed view
4. Bookmark favorite repositories with one tap
5. See visual indicators for bookmarked items
6. All data works offline after initial sync
