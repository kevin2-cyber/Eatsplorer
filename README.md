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

## 🛠️ Tech Stack & Architecture

The app is built using **Native Android (Java)** following the **MVVM (Model-View-ViewModel)** architecture to ensure separation of concerns and testability.

| Component | Library/Tool | Usage |
| :--- | :--- | :--- |
| **Language** | **Java 17** | Core application logic. |
| **Architecture** | **MVVM** | `ViewModel`, `LiveData`, and `Repository` pattern. |
| **Network** | **Retrofit + Gson** | API calls to Google Places (New API). |
| **List Rendering** | **RecyclerView + DiffUtil** | Efficient list updates and animations. |
| **Image Loading** | **Glide** | Caching and rendering restaurant photos. |
| **Maps** | **Google Maps SDK** | Rendering the map interface. |

---

## 💰 Technical Highlight: Cost Optimization
One of the core engineering challenges was managing the pricing tiers of the **Google Places API (New)**.

This app implements a **Split-Fetch Strategy** to keep costs low:
1.  **The List View (Budget Tier):** Calls the API requesting *only* `displayName`, `photo`, and `category`. This falls under the cheaper "Essentials" SKU.
2.  **The Detail View (Premium Tier):** Only when a user *taps* a card does the app fetch expensive fields like `nationalPhoneNumber` and `websiteUri` (Enterprise SKU).

**Code Snippet (Field Masking):**
```java
// We strictly request ONLY "Essentials" tier fields for the list
String cheapFieldMask = "places.displayName,places.formattedAddress,places.photos,places.primaryTypeDisplayName";
