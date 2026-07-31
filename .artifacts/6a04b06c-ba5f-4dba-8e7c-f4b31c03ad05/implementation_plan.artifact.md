# Map Screen Enhancement Plan

The goal is to ensure restaurants are correctly displayed on the map and clicking them navigates to the `DetailScreen`. We will also improve the map experience by auto-zooming to the restaurants and hiding unrelated map features.

## Proposed Changes

### [UI Layer]

#### [MODIFY] [MapScreen.kt](file:///Users/kimikevin/Desktop/github/Eatsplorer/app/src/main/java/com/kimikevin/eatsplorer/view/screens/MapScreen.kt)
- **Auto-Zoom**: Use `LatLngBounds` to calculate a bounding box for all loaded restaurants and animate the camera to fit them.
- **Marker Keying**: Use `key(restaurant.id)` when creating markers to ensure stability and performance.
- **Navigation**: Ensure `onInfoWindowClick` navigates to the `DetailScreen`.
- **Map Styling**: (Optional but recommended) Hide generic Points of Interest (POIs) if the user truly wants "only" the restaurants visible.
- **Marker State**: Use `rememberMarkerState` with the restaurant's position.

## Verification Plan

### Automated Tests
- Run `./gradlew assembleDebug` to verify the build.

### Manual Verification
- Launch the app and navigate to the Map tab.
- Verify that the map automatically zooms/pans to show the fetched restaurants.
- Click a marker to show the info window, then click the info window to navigate to the `DetailScreen`.
- Confirm that back navigation from `DetailScreen` returns to the Map tab.
