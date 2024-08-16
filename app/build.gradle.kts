plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.jetbrains.kotlin.android)
  id("com.google.devtools.ksp") version "1.9.0-1.0.12"
}

android {
  namespace = "com.antoan.kodetest"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.antoan.kodetest"
    minSdk = 24
    targetSdk = 34
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
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
  }
  buildFeatures {
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.5.1"
  }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

dependencies {

//  implementation(project(":logging"))

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.ui)
  implementation(libs.androidx.ui.graphics)
  implementation(libs.androidx.ui.tooling.preview)
  implementation(libs.androidx.material3)

  // Lifecycle
  implementation(libs.androidx.lifecycle.viewmodel)
  implementation(libs.androidx.lifecycle)

  // Retrofit
  implementation ("com.squareup.retrofit2:retrofit:2.11.0")
  implementation ("com.squareup.okhttp3:okhttp:4.12.0")

  // Moshi
  implementation("com.squareup.moshi:moshi:1.13.0")
  implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
  ksp("com.squareup.moshi:moshi-kotlin-codegen:1.13.0")

  //
  implementation(libs.androidx.compose.foundation)
  implementation(libs.androidx.material.icons)

  // Koin
  implementation(libs.koin.androidx.compose)


  // Coil
  implementation(libs.coil.compose)


  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.ui.test.junit4)
  debugImplementation(libs.androidx.ui.tooling)
  debugImplementation(libs.androidx.ui.test.manifest)
}