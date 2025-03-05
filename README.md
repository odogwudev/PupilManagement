# 🏫 Android Technical Test (Pupil Management)

![Android](https://img.shields.io/badge/Platform-Android-brightgreen)
![Kotlin](https://img.shields.io/badge/Language-Kotlin-blue)
![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-purple)
![Paging 3](https://img.shields.io/badge/Paging-3-orange)
![Hilt](https://img.shields.io/badge/DI-Hilt-red)
![Retrofit](https://img.shields.io/badge/Network-Retrofit-green)

A **modern Android application** for managing **pupil records** using:
- **Jetpack Compose** for UI
- **Paging 3** for infinite scrolling
- **Hilt** for Dependency Injection
- **Retrofit** for API calls
- **Room** for local database caching
- **Coroutines & Flow** for asynchronous programming

---

## 📸 **Screenshots**
| Home Screen | Pupil Details |
|------------|--------------|
| ![Home](https://via.placeholder.com/200) | ![Details](https://via.placeholder.com/200) |

---

## 🎯 **Features**
✔️ Fetch and display **paged list** of pupils  
✔️ **Pull-to-refresh** to reload data  
✔️ **Offline caching** with Room  
✔️ **Pagination** with Paging 3  
✔️ **Error handling** for **network failures**  
✔️ **Search Pupils** by name  
✔️ **View Pupil Details**  
✔️ **Dark mode support**

---

## 🛠 **Tech Stack**
| **Layer** | **Library** |
|-----------|------------|
| **UI** | Jetpack Compose |
| **State Management** | ViewModel, Flow |
| **Networking** | Retrofit, OkHttp |
| **Persistence** | Room Database |
| **Pagination** | Paging 3 |
| **DI (Dependency Injection)** | Hilt |
| **Testing** | JUnit, MockK |

---

## 🚀 **Getting Started**

### **1️⃣ Clone the Repository**
```sh
git clone https://github.com/yourusername/android-technical-test.git
cd android-technical-test


⚙️ API Setup
This app uses a mock API that simulates real-world scenarios:

Network failures (timeouts, 500 errors)
Slow responses
Dynamic data changes
Base URL: https://androidtechnicaltestapi-test.bridgeinternationalacademies.com/


📦 android-technical-test
     ┣ 📂 app
     ┃ ┣ 📂 data              # Repository & DAO
     ┃ ┣ 📂 domain            # Use cases & business logic
     ┃ ┣ 📂 ui                # Jetpack Compose UI
     ┃ ┣ 📂 di                # Hilt Dependency Injection
     ┃ ┣ 📂 utils             # Extensions & error handling
     ┃ ┗ 📝 README.md

./gradlew test

Covered test cases:
    ✅ PupilRepository fetches correct data
    ✅ Handles network failures & retries
    ✅ Offline caching with Room
    ✅ Pagination works correctly
    ✅ 404 errors remove stale data
