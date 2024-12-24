#  Healthy Salad (Kotlin Multiplatform Mobile)

Healthy Salad is a simple and sample _mobile application_ built to demonstrate the use of
**Kotlin Multiplatform Mobile** for developing Android and iOS applications
using **Jetpack Compose** 🚀.

## About 

It simply loads Posts data from API and stores it in local storage (i.e. SQLite Database). 
Posts will be always loaded from local database. Remote data (from API) and Local data is always 
synchronized.

**Features:**

- [x] Offline capability 📵
- [x] Dark mode 🌓
- [x] Clean and Simple Material UI 🎨

The network API is a dummy  response which is _statically hosted
https://shiv-eng.github.io/salads/api.


### 📱Screenshots

![healthy1](https://github.com/user-attachments/assets/64a88f41-24c0-4015-864c-97bc052a1c0b)
![healthy2](https://github.com/user-attachments/assets/2a4e2881-ec62-44d2-b532-865132d53296)
![healthy3](https://github.com/user-attachments/assets/f3be35bf-2d3f-4571-acb6-3b0759232857)


## Built with 

- [Kotlin](kotlinlang.org): Programming language
- [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html): For building multi-platform applications in the single codebase.
- [Jetpack/JetBrains Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/): For a shared UI between multi-platforms i.e. Android and iOS in this project.
- Kotlinx
  - [Coroutines](https://github.com/Kotlin/kotlinx.coroutines): For multithreading
  - [Serialization](https://github.com/Kotlin/kotlinx.serialization): For JSON serialization/deserailization
- [Ktor Client](https://github.com/ktorio/ktor): Performing HTTP requests, Creating image loading utility for iOS module.
- [SQLDelight](https://github.com/cashapp/sqldelight): For persisting posts data in the local database
- [Coil](https://github.com/coil-kt/coil): Image loading for Android
- [Mutekt](https://github.com/PatilShreyas/mutekt): For UI state management

## About Me

 Hi! My name is Shivangi Mundra, I work as a Software Developer and like to expand my skill set in my spare time.

If you have any questions or want to connect, feel free to reach out to me on :

- [LinkedIn](https://www.linkedin.com/in/shivangi-mundra-9a31b65b/)
