# Implementation Plan - Convert UI to Jetpack Compose

This plan outlines the migration of the Eatsplorer app from XML-based Views and Fragments to a modern Jetpack Compose architecture. The core logic in ViewModels and Repositories will be preserved, while the UI layer will be entirely rewritten.

## User Review Required

> [!IMPORTANT]
> The app currently uses `SplashScreen` API. In Compose, we will continue to use it in `MainActivity`, but navigation to `Onboarding` or `Home` will be handled within the Compose Navigation graph instead of starting new Activities.

> [!WARNING]
> `Glide` is currently used for image loading. I recommend adding `Coil` for a more idiomatic Compose experience, or we can use `Glide`'s Compose integration if preferred. I will proceed with `Coil` for simplicity and best practices in Compose.

## Proposed Changes

### Dependencies & Setup

#### [MODIFY] [app/build.gradle](file:///Users/kimikevin/Desktop/github/Eatsplorer/app/build.gradle)
- Add `androidx.navigation:navigation-compose`.
- Add `com.google.maps.android:maps-compose`.
- Add `io.coil-kt:coil-compose`.
- Ensure all Compose-related dependencies are up to date.

### Theme and UI Components

#### [NEW] [Theme.kt](file:///Users/kimikevin/Desktop/github/Eatsplorer/app/src/main/java/com/kimikevin/eatsplorer/ui/theme/Theme.kt)
- Define Material 3 ColorScheme, Typography, and Shapes based on current `colors.xml` and `themes.xml`.

#### [NEW] [Components.kt](file:///Users/kimikevin/Desktop/github/Eatsplorer/app/src/main/java/com/kimikevin/eatsplorer/ui/components/Components.kt)
- Reusable components like `RestaurantCard`, `RatingBadge`, and `LoadingIndicator`.

### Screens

#### [NEW] [OnboardingScreen.kt](file:///Users/kimikevin/Desktop/github/Eatsplorer/app/src/main/java/com/kimikevin/eatsplorer/ui/screens/OnboardingScreen.kt)
- Replaces `OnboardingActivity`. Uses `HorizontalPager` for the onboarding flow.

#### [NEW] [MainScreen.kt](file:///Users/kimikevin/Desktop/github/Eatsplorer/app/src/main/java/com/kimikevin/eatsplorer/ui/screens/MainScreen.kt)
- Replaces `MainActivity` layout. Contains the `Scaffold` with `NavigationBar` and the main navigation host.

#### [NEW] [RestaurantListScreen.kt](file:///Users/kimikevin/Desktop/github/Eatsplorer/app/src/main/java/com/kimikevin/eatsplorer/ui/screens/RestaurantListScreen.kt)
- Replaces `RestaurantListFragment`. Uses `LazyColumn` for the list.

#### [NEW] [MapScreen.kt](file:///Users/kimikevin/Desktop/github/Eatsplorer/app/src/main/java/com/kimikevin/eatsplorer/ui/screens/MapScreen.kt)
- Replaces `MapsFragment`. Uses `GoogleMap` from `maps-compose`.

#### [NEW] [DetailScreen.kt](file:///Users/kimikevin/Desktop/github/Eatsplorer/app/src/main/java/com/kimikevin/eatsplorer/ui/screens/DetailScreen.kt)
- Replaces `DetailActivity`. Displays restaurant details and handles actions (Call, Website).

### Navigation & Integration

#### [MODIFY] [MainActivity.java](file:///Users/kimikevin/Desktop/github/Eatsplorer/app/src/main/java/com/kimikevin/eatsplorer/MainActivity.java)
- Convert to a simple entry point that sets the Compose content.
- Note: The request says "leave backend code", but `MainActivity` is largely UI logic. I will likely convert it to Kotlin or keep it in Java but calling a Compose entry point. *Actually, converting the entry point to Kotlin is usually cleaner for Compose.*

### Cleanup

#### [DELETE] Fragments and Activities
- Remove `DetailActivity`, `OnboardingActivity`, `RestaurantListFragment`, `MapsFragment`.
- Remove associated XML layouts in `res/layout`.

## Verification Plan

### Automated Tests
- Since this is a UI migration, I will focus on ensuring the app builds and runs.
- I will verify that `HomeViewModel` and `DetailViewModel` still function correctly when bound to Compose.

### Manual Verification
- Deploy to device/emulator.
- Verify Onboarding flow and persistence (once finished, it shouldn't show again).
- Verify Restaurant list loading and "Spin the Wheel" feature.
- Verify Map markers and user location.
- Verify navigation to Detail screen and external intents (Call/Website).
