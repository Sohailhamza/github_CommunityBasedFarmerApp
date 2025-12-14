plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.practicenavigation"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.practicenavigation"
        minSdk = 24
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation ("androidx.cardview:cardview:1.0.0")
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation ("com.google.android.material:material:1.13.0")

    implementation ("com.google.android.material:material:1.13.0")
    implementation ("com.google.firebase:firebase-auth")
    implementation ("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-auth:24.0.1")
    implementation(platform("com.google.firebase:firebase-bom:34.7.0"))
    implementation("com.cloudinary:cloudinary-android:3.1.2")

    implementation ("com.github.bumptech.glide:glide:5.0.5")
    annotationProcessor ("com.github.bumptech.glide:compiler:5.0.5")
    implementation("com.squareup.okhttp3:okhttp:5.3.2")


}