plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.jetbrains.kotlin.android)
  id("com.google.devtools.ksp")
  id("com.google.dagger.hilt.android")
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

    testInstrumentationRunner = "com.antoan.kodetest.HiltTestRunner"
    vectorDrawables {
      useSupportLibrary = true
    }
  }

  sourceSets {
    getByName("androidTest") {
      assets.srcDirs("src/debug/assets")
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

  testOptions {
    unitTests {
      isIncludeAndroidResources = true
    }
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.5.2"
  }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }

}

dependencies {
  
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.ui)
  implementation(libs.androidx.ui.graphics)
  implementation(libs.androidx.ui.tooling.preview)
  implementation(libs.androidx.material3)

  // PullToRefresh
  implementation("io.github.frankieshao.refreshlayout:refreshlayout:1.0.0")

  // Lifecycle
  implementation(libs.androidx.lifecycle.viewmodel)
  implementation(libs.androidx.lifecycle)

  // Navigation
  implementation("androidx.navigation:navigation-compose:2.7.7")
  implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

  // Network:[Retrofit, Moshi, OkHttp, MockWebServer]
  implementation("com.squareup.retrofit2:retrofit:2.11.0")
  implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
  implementation("com.squareup.okhttp3:okhttp:4.12.0")
  implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
  implementation("com.squareup.moshi:moshi:1.13.0")

  ksp("com.squareup.moshi:moshi-kotlin-codegen:1.13.0")

  testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
  androidTestImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")

  //
  implementation(libs.androidx.compose.foundation)
  implementation(libs.androidx.material.icons)

  // DI:[Hilt]
  implementation("com.google.dagger:hilt-android:2.51.1")
  androidTestImplementation("com.google.dagger:hilt-android-testing:2.51.1")
  ksp("com.google.dagger:hilt-compiler:2.51.1")

  // Coil
  implementation(libs.coil.compose)

  // Room
  val room = "2.6.1"
  implementation("androidx.room:room-runtime:$room")
  implementation("androidx.room:room-ktx:$room")
  ksp("androidx.room:room-compiler:$room")

  // Testing:[androidx.test, Robolectric]
  implementation(libs.androidx.monitor)
  testImplementation("org.robolectric:robolectric:4.13")
  testImplementation("com.google.truth:truth:1.4.2")
  androidTestImplementation("com.google.truth:truth:1.4.2")
  androidTestImplementation ("androidx.arch.core:core-testing:2.2.0")

  // Testing:[Default]
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.ui.test.junit4)
  debugImplementation(libs.androidx.ui.tooling)
  debugImplementation(libs.androidx.ui.test.manifest)
}