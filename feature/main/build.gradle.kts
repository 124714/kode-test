plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.jetbrains.kotlin.android)
  alias(libs.plugins.ksp)
}

android {
  namespace = "com.antoan.kodetest.main"
  compileSdk = 34

  defaultConfig {
    minSdk = 24

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    consumerProguardFiles("consumer-rules.pro")
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
    kotlinCompilerExtensionVersion = "1.5.2"
  }
}

dependencies {
  implementation(platform(libs.androidx.compose.bom))
  implementation(project(":common"))

  // DI
  implementation(libs.hilt.android)
  ksp(libs.hilt.compiler)

// Lifecycle
  implementation(libs.androidx.lifecycle)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.lifecycle.viewmodel)
  implementation(libs.androidx.material3)

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.appcompat)
//  implementation(libs.material)

  // Coil
  implementation(libs.coil.compose)

  implementation(libs.androidx.ui.graphics)
  implementation(libs.androidx.ui.tooling.preview)
  implementation(libs.androidx.ui)
  implementation(libs.androidx.lifecycle.runtime.ktx)

  implementation(libs.pulltorefresh)

  //
  implementation(libs.androidx.compose.foundation)
  implementation(libs.androidx.material.icons)

  // Navigation
  implementation(libs.androidx.navigation.compose)
  implementation(libs.androidx.hilt.navigation.compose)

  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.test.espresso.core)
}