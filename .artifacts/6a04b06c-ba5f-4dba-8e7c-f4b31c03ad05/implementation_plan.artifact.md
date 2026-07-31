# Build Fix Plan after Dependency Updates

The project fails to build due to incorrect Gradle configuration in `settings.gradle`, `libs.versions.toml`, and `app/build.gradle`. Specifically, the Version Catalog (`libs.versions.toml`) is misconfigured, with many libraries placed in the `[plugins]` section, and the `alias` keyword is misused in build scripts.

## Proposed Changes

### [Gradle Configuration]

#### [MODIFY] [settings.gradle](file:///Users/kimikevin/Desktop/github/Eatsplorer/settings.gradle)
- Wrap plugin alias in parentheses: `alias(libs.plugins.gradle.toolchains.foojay.resolver.convention)`.

#### [MODIFY] [libs.versions.toml](file:///Users/kimikevin/Desktop/github/Eatsplorer/gradle/libs.versions.toml)
- Correct the `[versions]` section to use actual version strings instead of artifact names.
- Move mislabeled libraries (Compose UI, Material3, Runtime, Firebase) from `[plugins]` to `[libraries]`.
- Consolidate versions according to the user's update list.

#### [MODIFY] [app/build.gradle](file:///Users/kimikevin/Desktop/github/Eatsplorer/app/build.gradle)
- Wrap plugin aliases in parentheses in the `plugins {}` block.
- Correct the `dependencies {}` block:
    - Remove the `alias` keyword from dependency declarations.
    - Update references to use the corrected Version Catalog entries.
    - Ensure BOMs are used correctly with `platform()`.
    - Use consistency between TOML and build file (e.g., Room version).

## Verification Plan

### Automated Tests
- Run `./gradlew assembleDebug` to verify the build.
- Run `gradle_sync` to ensure IDE synchronization.

### Manual Verification
- None required if the build passes.
