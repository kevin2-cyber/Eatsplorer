# Custom Font Integration Plan

The goal is to integrate the custom fonts (`samsung.otf` and `samsung_bold.ttf`) into the Jetpack Compose `Typography` configuration to ensure consistent branding across the app.

## Proposed Changes

### [UI Layer]

#### [MODIFY] [Type.kt](file:///Users/kimikevin/Desktop/github/Eatsplorer/app/src/main/java/com/kimikevin/eatsplorer/view/theme/Type.kt)
- Define a `FontFamily` called `SamsungFontFamily` that includes:
    - `samsung.otf` as the default weight.
    - `samsung_bold.ttf` as the bold weight.
- Update the `Typography` object to use `SamsungFontFamily` for all major text styles (`bodyLarge`, `titleLarge`, `headlineMedium`, etc.).
- Clean up commented-out boilerplate code.

## Verification Plan

### Automated Tests
- Run `./gradlew assembleDebug` to verify that the font resources are correctly linked and the code compiles.

### Manual Verification
- Deploy the app and visually confirm that the new font is applied to the UI (e.g., in the Restaurant List or Detail Screen).
