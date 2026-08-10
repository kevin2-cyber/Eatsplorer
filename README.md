# Eatsplorer - Android Restaurant Discovery App

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![XML](https://img.shields.io/badge/UI-XML_Views-blue?style=for-the-badge&logo=android)
![Google Maps](https://img.shields.io/badge/Google_Maps-4285F4?style=for-the-badge&logo=google-maps&logoColor=white)

> **Eat. Explore. Repeat.**
> An Android application that helps users discover "hidden gem" restaurants based on vibe and mood, featuring a cost-optimized implementation of the Google Places API.

---

## Project Overview

**Eatsplorer** is a discovery tool for indecisive foodies. Unlike standard apps that filter merely by cuisine, Eatsplorer focuses on the **Vibe** (e.g., "Date Night," "Digital Nomad").

It features a **"Spin the Wheel"** decision-maker for groups and uses a smart data-fetching strategy to minimize API costs while delivering rich content.

## Key Features

- **Vibe-First Search:** Filter restaurants by mood (Romantic, Quiet, Lively) rather than just "Italian" or "Burgers."
- **Spin the Wheel:** Can't decide? The app randomly selects a highly-rated nearby spot for you.
- **Smart Map Exploration:** Interactive Google Maps integration to view spots around you.
- **Instant Details:** Tap a card to reveal contact info (phone, website, opening hours) on demand.
- **Data Saver Mode:** Uses Google API Field Masking to reduce data usage and keep API costs low.
- **Onboarding Flow:** First-launch walkthrough featuring **ViewPager2** with a **Dots Indicator**.

---

## Architecture & Design

The app is built using **Java 17** and **XML Views**, following the **MVVM** architecture pattern with **View Binding** for safe and efficient UI interactions.

### MVVM Pattern

- **View (UI):** Activities and Fragments using **View Binding** to interact with XML layouts.
- **ViewModel:** Holds and manages UI state using `LiveData`. Survives configuration changes and drives all screen logic.
- **Repository:** The single source of truth. Orchestrates **Retrofit** network calls and handles data persistence.

### Package Structure

```
com.kimikevin.eatsplorer
├── MainActivity.java             # Entry point, splash screen, onboarding gate
├── model/
│   ├── entity/                   # Data models & Retrofit service interface
│   ├── mapper/                   # RestaurantMapper (API → domain model)
│   └── repository/               # RestaurantRepository (singleton)
├── viewmodel/
│   ├── HomeViewModel.java        # Search & wheel-spin logic
│   ├── DetailViewModel.java      # Place detail fetching
│   └── SplashViewModel.java      # Splash screen state
└── view/
    ├── adapter/                  # RecyclerView & ViewPager2 adapters
    ├── fragment/                 # App screens (Home, Map, Detail, etc.)
    └── ui/                       # Custom UI components
```

---

## Tech Stack

| Component | Library / Tool | Details |
| :--- | :--- | :--- |
| **Language** | Java 17 | Robust, object-oriented development |
| **UI Framework** | XML Views | Classic Android View system with View Binding |
| **Navigation** | Navigation Component 2.9.8 | Fragment-based navigation with Safe Args |
| **Network** | Retrofit 3.0.0 + OkHttp 5.4.0 | Google Places API (New) integration |
| **Image Loading** | Glide 5.0.9 | Efficient image fetching and caching |
| **Onboarding** | ViewPager2 + DotsIndicator | Smooth walkthrough experience |
| **Maps** | Google Maps SDK 20.0.0 | Native map rendering and interaction |
| **Location** | Play Services Location 21.4.0 | GPS and network-based positioning |
| **Splash Screen** | AndroidX SplashScreen 1.2.0 | Standardized launch experience |
| **API Security** | Secrets Gradle Plugin 2.0.1 | Secure API key management |

---

## Setup & Installation

### Prerequisites

- [Android Studio Meerkat](https://developer.android.com/studio) or newer.
- **JDK 17**.
- A Google Cloud Platform account with the **Places API (New)** and **Maps SDK for Android** enabled.

### API Key Configuration

1. Open (or create) `local.properties` in the project root.
2. Add your Google API key:
   ```properties
   GOOGLE_API_KEY="YOUR_ACTUAL_API_KEY"
   ```
3. The Secrets Gradle Plugin reads this key and injects it into `BuildConfig` and `AndroidManifest.xml` automatically.

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
