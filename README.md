# TwitterClone App

Welcome to the TwitterClone App – your gateway to building a community and sharing moments. This Android application is a homage to Twitter, featuring similar functionalities with a touch of uniqueness. It's built using Firebase for robust backend services and follows the MVVM architectural pattern for maintainable and scalable code.

## Screenshots
<img src="https://github.com/Vishalbhardwj/WeatherApp_Retrofit/assets/139758910/9360360a-6b56-42a1-9d19-70400d07081b" width="200" height="370">
<img src="https://github.com/Vishalbhardwj/WeatherApp_Retrofit/assets/139758910/74db9a3e-ff0e-475b-b1d1-43b1b95f973b" width="200" height="370">





## Features

- **User Authentication**: Secure email and password login system powered by Firebase Auth.
- **Real-time Database**: User information is managed in real-time with Firebase Realtime Database.
- **Media Storage**: Users can upload media files seamlessly with Firebase Storage.
- **User Profiles**: Personalize your experience by uploading profile images.
- **Tweeting**: Share your thoughts and connect with others through tweets.
- **Interactive UI**: Navigate through the app with fragments for account management and tweeting.
- **Floating Action Button**: A handy button for quick uploading of tweets .

### Prerequisites

- Android Studio
- Firebase account

### Getting Started

- **1**. Clone the repository:
- **git clone https://github.com/yourusername/TwitterClone.git**
- **2**.Open the project in Android Studio.
- **3**.Build the project to resolve all dependencies.

### Dependencies

Here are the dependencies used in the build.gradle.kts (Module:app) file:
```kotlin
dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(platform("com.google.firebase:firebase-bom:32.8.1"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("com.github.bumptech.glide:glide:4.16.0")
}


