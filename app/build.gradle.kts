plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.project.patigo"
    compileSdk = 33

    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.project.patigo"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation("com.google.android.flexbox:flexbox:3.0.0")
    implementation("com.karumi:dexter:6.2.2")
    implementation("io.coil-kt:coil:2.4.0")
    implementation("com.github.santalu:maskara:1.0.0")
    implementation("com.github.Spikeysanju:MotionToast:1.4")
    implementation("com.github.ibrahimsn98:SmoothBottomBar:1.7.9")
    implementation("com.airbnb.android:lottie:6.2.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("com.google.dagger:hilt-android:2.48")
    implementation("com.google.firebase:firebase-auth:22.3.0")
    implementation("com.google.firebase:firebase-firestore:24.10.0")
    implementation("com.google.firebase:firebase-storage:20.3.0")
    kapt("com.google.dagger:hilt-android-compiler:2.48")
    implementation("com.squareup.retrofit2:retrofit:2.6.0")
    implementation("com.google.code.gson:gson:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.5.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.5.1")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("me.relex:circleindicator:2.1.6")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("com.github.dhaval2404:imagepicker:2.1")


}