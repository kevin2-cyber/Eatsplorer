# Integrate Glide for Optimized Image Loading

The goal is to integrate Glide into the project and use it in the onboarding screen to improve image loading performance (off-main-thread decoding, memory management).

## Proposed Changes

### [Gradle Configuration]

#### [MODIFY] [libs.versions.toml](file:///Users/kimikevin/Desktop/github/Eatsplorer/gradle/libs.versions.toml)
- Add `glide` version: `4.16.0` (or latest stable).
- Add `glide-compose` version: `1.0.0-beta01`.
- Add `glide-compose` library definition.

#### [MODIFY] [app/build.gradle.kts](file:///Users/kimikevin/Desktop/github/Eatsplorer/app/build.gradle.kts)
- Add `implementation(libs.glide.compose)` to the dependencies.

### [UI Layer]

#### [MODIFY] [OnboardingScreen.kt](file:///Users/kimikevin/Desktop/github/Eatsplorer/app/src/main/java/com/kimikevin/eatsplorer/view/screens/OnboardingScreen.kt)
- Replace the standard `Image` with `GlideImage` from the Glide Compose library.
- Ensure `ContentScale.Crop` is maintained for the full-screen effect.

## Verification Plan

### Automated Tests
- Run `./gradlew assembleDebug` to verify the build.

### Manual Verification
- Launch the app and observe the onboarding screen.
- Verify that images load smoothly and the UI remains responsive during transitions.
