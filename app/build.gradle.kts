plugins {
    alias(libs.plugins.android.application)

    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
    kotlin("kapt") // Add this line
}
android {
    namespace = "com.ankitkumar.project.ripplenotes"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.ankitkumar.project.ripplenotes"
        minSdk = 27
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    // Add this block if it doesn't exist
    kotlin {
        sourceSets.main {
            kotlin.srcDirs("build/generated/ksp/debug/kotlin")
        }
    }

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    implementation(libs.firebase.firestore)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.material.v190)
    implementation("androidx.room:room-runtime:2.6.1")

    implementation("androidx.room:room-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")


    dependencies {
        implementation( "androidx.appcompat:appcompat:1.6.1")
    }

    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation ("com.airbnb.android:lottie:6.6.2")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation( "androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation( "com.google.android.material:material:1.9.0")
    implementation( "com.google.firebase:firebase-auth:22.3.1")
    implementation ("com.google.firebase:firebase-firestore:24.10.1")
    implementation ("com.google.android.gms:play-services-auth:20.7.0")
    implementation(platform("com.google.firebase:firebase-bom:33.11.0"))
    implementation("com.google.firebase:firebase-analytics")
}