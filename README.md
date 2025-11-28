# 🍽️ Eatsplorer - Android Restaurant Discovery App

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Retrofit](https://img.shields.io/badge/Retrofit-2C8EBB?style=for-the-badge&logo=square&logoColor=white)
![Google Maps](https://img.shields.io/badge/Google_Maps-4285F4?style=for-the-badge&logo=google-maps&logoColor=white)

> **Eat. Explore. Repeat.**
> An Android application that helps users discover "hidden gem" restaurants based on vibe and mood, featuring a cost-optimized implementation of the Google Places API.

---

## 📱 Project Overview
**Eatsplorer** is not just another directory; it's a discovery tool for indecisive foodies. Unlike standard apps that filter merely by cuisine, Eatsplorer focuses on the **Vibe** (e.g., "Date Night," "Digital Nomad").

It features a **"Spin the Wheel"** decision-maker for groups and uses a smart data-fetching strategy to minimize API costs while delivering rich content.

## ✨ Key Features
* **🎯 Vibe-First Search:** Filter restaurants by mood (Romantic, Quiet, Lively) rather than just "Italian" or "Burgers."
* **🎰 Spin the Wheel:** Can't decide? The app randomly selects a highly-rated nearby spot for you.
* **📍 Smart Map Exploration:** Interactive Google Maps integration to view spots around you.
* **⚡ Instant Details:** Tap a card to reveal contact info (Phone, Website) instantly.
* **📉 Data Saver Mode:** Uses specific Google API "Field Masking" to reduce data usage and costs.

---

## 🏗️ Architecture & Design

The app is built using **Native Android (Java)** following the **MVVM (Model-View-ViewModel)** architecture to ensure separation of concerns and testability.

### 1. The MVVM Pattern
* **View (UI):** XML Layouts & Activities using **ViewBinding** to display data safely.
* **ViewModel:** Holds UI state and survives screen rotations. It exposes data via **LiveData**.
* **Repository:** The "Single Source of Truth." It orchestrates data fetching and decides whether to use the Network (API) or Local Cache.

### 2. Data Flow (Request Lifecycle)
1.  **User Action:** User taps "Search" or "Spin the Wheel".
2.  **Repository Logic:** The app checks internal cache. If empty, it triggers a Retrofit network call.
3.  **API Request:** A `POST` request is sent to `places.googleapis.com` using specific **Field Masks** (see below).
4.  **UI Update:** The response is parsed into POJOs, passed to the ViewModel, and the RecyclerView updates automatically via **DiffUtil** for smooth animations.

---

## 💰 Technical Highlight: Cost Optimization Strategy
One of the core engineering challenges was managing the pricing tiers of the **Google Places API (New)**. This app implements a **Split-Fetch Strategy** to keep costs low.

### A. The "Browsing" Fetch (Low Cost)
Used for the main list (`RecyclerView`). We only request data from the **Essentials (Pro)** tier.
* **API Endpoint:** `v1/places:searchNearby`
* **Field Mask:**
    ```text
    places.id,
    places.displayName,
    places.formattedAddress,
    places.photos,
    places.primaryTypeDisplayName
    ```

### B. The "Detail" Fetch (Premium)
Used **only** when a user *taps* a card. This fetches contact data which is billed at a higher rate.
* **API Endpoint:** `v1/places/{placeId}`
* **Field Mask:**
    ```text
    nationalPhoneNumber,
    websiteUri,
    regularOpeningHours
    ```

---

## 🛠️ Tech Stack

| Component | Library/Tool | Usage |
| :--- | :--- | :--- |
| **Language** | **Java 17** | Core application logic. |
| **Architecture** | **MVVM** | `ViewModel`, `LiveData`, and `Repository` pattern. |
| **Network** | **Retrofit + Gson** | API calls to Google Places (New API). |
| **List Rendering** | **RecyclerView + DiffUtil** | Efficient list updates and animations. |
| **Image Loading** | **Glide** | Caching and rendering restaurant photos. |
| **Maps** | **Google Maps SDK** | Rendering the map interface. |

---

## 📸 Screenshots
*(Place your screenshots in a `screenshots` folder in your repository)*

| Home Screen | Spin the Wheel | Detail View |
|:---:|:---:|:---:|
| <img src="screenshots/home.png" width="250" alt="Home Screen"> | <img src="screenshots/spin.png" width="250" alt="Spin Wheel"> | <img src="screenshots/detail.png" width="250" alt="Detail View"> |

---

## 🚀 Setup & Installation

Follow these steps to get the project running on your local machine.

### 1. Prerequisites
* [Android Studio Koala](https://developer.android.com/studio) (or newer).
* Java Development Kit (JDK) 17.
* A Google Cloud Platform account (for API keys).

### 2. Clone the Repository
```bash
git clone [https://github.com/your-username/Eatsplorer.git](https://github.com/your-username/Eatsplorer.git)
cd Eatsplorer
