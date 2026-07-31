# Gradle Groovy to Kotlin DSL Migration Plan

This plan outlines the migration of the project's Gradle build scripts from Groovy (`.gradle`) to Kotlin DSL (`.gradle.kts`). This migration provides better IDE support, type safety, and consistency with modern Android development practices.

## Proposed Changes

### [Gradle Configuration]

#### [NEW] [settings.gradle.kts](file:///Users/kimikevin/Desktop/github/Eatsplorer/settings.gradle.kts)
#### [DELETE] [settings.gradle](file:///Users/kimikevin/Desktop/github/Eatsplorer/settings.gradle)
- Convert repository declarations to Kotlin syntax.
- Convert plugin declarations to Kotlin syntax.
- Convert project inclusion to Kotlin syntax.

#### [NEW] [build.gradle.kts](file:///Users/kimikevin/Desktop/github/Eatsplorer/build.gradle.kts) (Root)
#### [DELETE] [build.gradle](file:///Users/kimikevin/Desktop/github/Eatsplorer/build.gradle) (Root)
- Convert `buildscript` block, including GitHub Packages repository configuration.
- Convert `plugins` block using `alias` with `apply false`.

#### [NEW] [app/build.gradle.kts](file:///Users/kimikevin/Desktop/github/Eatsplorer/app/build.gradle.kts)
#### [DELETE] [app/build.gradle](file:///Users/kimikevin/Desktop/github/Eatsplorer/app/build.gradle)
- Convert `plugins` block.
- Convert `android` block:
    - Update properties to use assignments (`=`) instead of Groovy property access.
    - Update function calls (e.g., `proguardFiles`, `manifestPlaceholders`).
    - Use `JavaVersion.VERSION_17` for compatibility.
- Convert `dependencies` block:
    - Wrap all dependency declarations in parentheses.
    - Properly use `implementation(platform(libs.xxx))`.

## Verification Plan

### Automated Tests
- Run `./gradlew assembleDebug` to verify the build.
- Run `gradle_sync` to ensure IDE synchronization and that no DSL errors are present.

### Manual Verification
- Verify that the IDE correctly recognizes the `.gradle.kts` files and provides code completion.
