plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
//    id("dagger.hilt.android.plugin")
}
kapt {
    correctErrorTypes = true
    useBuildCache = true
}
android {
    compileSdk = 30
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "com.esmaeel.saver"
        minSdk = 21
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = rootProject.extra["compose_version"] as String
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.2.1")
    implementation("androidx.compose.ui:ui:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.material:material:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.ui:ui-tooling:${rootProject.extra["compose_version"]}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.0-alpha06")
    implementation("androidx.activity:activity-compose:1.3.0-alpha02")
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${rootProject.extra["compose_version"]}")

    implementation("androidx.room:room-runtime:2.2.6")
    kapt("androidx.room:room-compiler:2.2.6")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:2.2.6")


    // Koin main features for Android (Scope,ViewModel ...)
    implementation("io.insert-koin:koin-android:3.0.1")
//    implementation("io.insert-koin:koin-viewmodel:3.0.1")
//     Koin Android - experimental builder extensions
    implementation("io.insert-koin:koin-android-ext:3.0.1")
    // Koin for Jetpack WorkManager
    implementation("io.insert-koin:koin-androidx-workmanager:3.0.1")
    // Koin for Jetpack Compose (unstable version)
    implementation("io.insert-koin:koin-androidx-compose:3.0.1")


    implementation("androidx.navigation:navigation-compose:1.0.0-alpha09")

}