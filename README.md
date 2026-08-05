# Eatsplorer - Android Restaurant Discovery App

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-2.4.10-ED8B00?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)
![Firebase](https://img.shields.io/badge/Firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=black)
![Google Maps](https://img.shields.io/badge/Google_Maps-4285F4?style=for-the-badge&logo=google-maps&logoColor=white)

> **Eat. Explore. Repeat.**
> An Android application that helps users discover "hidden gem" restaurants based on vibe and mood, featuring a cost-optimized implementation of the Google Places API.

---

## Project Overview

**Eatsplorer** is not just another directory — it's a discovery tool for indecisive foodies. Unlike standard apps that filter merely by cuisine, Eatsplorer focuses on the **Vibe** (e.g., "Date Night," "Digital Nomad").

It features a **"Spin the Wheel"** decision-maker for groups and uses a smart data-fetching strategy to minimize API costs while delivering rich content.

## Key Features

- **Vibe-First Search:** Filter restaurants by mood (Romantic, Quiet, Lively) rather than just "Italian" or "Burgers."
- **Spin the Wheel:** Can't decide? The app randomly selects a highly-rated nearby spot for you.
- **Smart Map Exploration:** Interactive Google Maps integration with Maps Compose to view spots around you.
- **Instant Details:** Tap a card to reveal contact info (phone, website, opening hours) on demand.
- **Data Saver Mode:** Uses Google API Field Masking to reduce data usage and keep API costs low.
- **Onboarding Flow:** First-launch walkthrough with persistent state via SharedPreferences.

---

## Architecture & Design

The app is built with **Jetpack Compose** and **Kotlin** following the **MVVM** architecture pattern.

### MVVM Pattern

- **View (UI):** Compose screens using `StateFlow` collected via `collectAsStateWithLifecycle`.
- **ViewModel:** Holds and manages UI state using `MutableStateFlow`. Survives configuration changes and drives all screen logic.
- **Repository:** The single source of truth. Orchestrates Retrofit network calls and exposes results through Kotlin's `Result<T>` type.

### Package Structure

```
com.kimikevin.eatsplorer
├── MainActivity.kt               # Entry point, splash screen, onboarding gate
├── model/
│   ├── entity/                   # Data classes & Retrofit service interface
│   ├── mapper/                   # RestaurantMapper (API → domain model)
│   └── repository/               # RestaurantRepository (singleton)
├── viewmodel/
│   ├── HomeViewModel.kt          # Search & wheel-spin logic
│   ├── DetailViewModel.kt        # Place detail fetching
│   └── SplashViewModel.kt        # Splash screen state
└── view/
    ├── screens/                  # Compose screens
    │   ├── MainScreen.kt         # Navigation host & bottom tab routing
    │   ├── RestaurantListScreen.kt
    │   ├── MapScreen.kt
    │   ├── DetailScreen.kt
    │   └── OnboardingScreen.kt
    └── theme/                    # Material3 theme (Color, Type, Theme)
```

### Data Flow

1. **User Action:** User taps "Search" or "Spin the Wheel" on a Compose screen.
2. **ViewModel:** Calls the repository via a `viewModelScope` coroutine and updates `StateFlow` state.
3. **Repository:** Sends a Retrofit `POST` to `places.googleapis.com` with a Field Mask.
4. **UI Update:** Compose automatically recomposes when the `StateFlow` emits the new state.

---

## Cost Optimization Strategy

Managing Google Places API pricing tiers is a core engineering concern. The app uses a **Split-Fetch Strategy** to keep costs low.

### Browsing Fetch (Low Cost)

Used for the main restaurant list. Only requests fields from the Essentials tier.

- **Endpoint:** `v1/places:searchNearby`
- **Field Mask:** `places.id, places.displayName, places.formattedAddress, places.photos, places.primaryTypeDisplayName`

### Detail Fetch (On-Demand)

Triggered **only** when a user taps a card. Fetches contact data billed at a higher rate.

- **Endpoint:** `v1/places/{placeId}`
- **Field Mask:** `nationalPhoneNumber, websiteUri, regularOpeningHours`

---

## Tech Stack

| Component | Library / Tool | Details |
| :--- | :--- | :--- |
| **Language** | Kotlin 2.4.10 | Coroutines, `Result<T>`, suspend functions |
| **UI** | Jetpack Compose BOM 2026.06.01 | Material3, `StateFlow`, no XML layouts |
| **Architecture** | MVVM | ViewModel + StateFlow + Repository |
| **Navigation** | Navigation Compose 2.9.8 | Type-safe sealed-class routes |
| **Network** | Retrofit 3.0.0 + OkHttp 5.4.0 | Google Places API (New) |
| **Image Loading** | Coil Compose 2.7.0 | Async image rendering in Compose |
| **Maps** | Maps Compose 8.4.0 | Google Maps rendered in Compose |
| **Location** | Google Play Services Location 21.4.0 | Fine + coarse location |
| **Auth** | Firebase Auth 24.2.0 | User authentication |
| **Analytics** | Firebase Analytics 23.2.0 | Usage tracking |
| **Splash Screen** | AndroidX SplashScreen 1.2.0 | Animated launch screen |
| **API Key Security** | Secrets Gradle Plugin 2.0.1 | Keys in `local.properties` |

---

## Screenshots

*(Place your screenshots in a `screenshots` folder in your repository)*

| Home Screen | Spin the Wheel | Detail View |
|:---:|:---:|:---:|
| <img src="screenshots/home.png" width="250" alt="Home Screen"> | <img src="screenshots/spin.png" width="250" alt="Spin Wheel"> | <img src="screenshots/detail.png" width="250" alt="Detail View"> |

---

## Setup & Installation

### Prerequisites

- [Android Studio Meerkat](https://developer.android.com/studio) or newer.
- JDK 17.
- A Google Cloud Platform account with the **Places API (New)** and **Maps SDK for Android** enabled.
- A Firebase project (for Auth and Analytics).

### Clone the Repository

```bash
git clone https://github.com/your-username/Eatsplorer.git
cd Eatsplorer
```

### API Key Configuration

1. Open (or create) `local.properties` in the project root.
2. Add your Google API key:
   ```properties
   GOOGLE_API_KEY="YOUR_ACTUAL_API_KEY"
   ```
3. The Secrets Gradle Plugin reads this key and injects it into `BuildConfig` and `AndroidManifest.xml` automatically.

### Firebase Setup

1. Download `google-services.json` from your Firebase project console.
2. Place it in the `app/` directory.

### Build and Run

1. Open the project in Android Studio.
2. Wait for Gradle to sync.
3. Connect an Android device (API 30+) or start an emulator.
4. Click **Run** (▶).

---

## Contributing

1. Fork the project.
2. Create your feature branch: `git checkout -b feature/AmazingFeature`
3. Commit your changes: `git commit -m 'Add some AmazingFeature'`
4. Push to the branch: `git push origin feature/AmazingFeature`
5. Open a Pull Request.

---

## License

Distributed under the MIT License. See `LICENSE` for more information.

---

*Built with love by Kelvin Eduful*
