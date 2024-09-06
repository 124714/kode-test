plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.jetbrains.kotlin.android)
  alias(libs.plugins.ksp)
  alias(libs.plugins.hilt)
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
//  hilt {
//    enableExperimentalClasspathAggregation = true
//  }

}

dependencies {

  implementation(project(":common"))
  implementation(project(":feature:main"))
  implementation(project(":feature:detail"))
  // ----------------------------------------------


  // DI
  implementation(libs.hilt.android)
  ksp(libs.hilt.compiler)

  // ----------------------------------------------

  implementation(platform(libs.androidx.compose.bom))

  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.ui)
  implementation(libs.androidx.ui.graphics)
  implementation(libs.androidx.ui.tooling.preview)

  // Lifecycle
  implementation(libs.androidx.lifecycle)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.lifecycle.viewmodel)
  implementation(libs.androidx.material3)

  // PullToRefresh (refreshlayout)
  implementation(libs.pulltorefresh)

  // Navigation
  implementation(libs.androidx.navigation.compose)
  implementation(libs.androidx.hilt.navigation.compose)

//  // Coil
//  implementation(libs.coil.compose)

  testImplementation(libs.mockwebserver)
  androidTestImplementation(libs.mockwebserver)

  //
  implementation(libs.androidx.compose.foundation)
  implementation(libs.androidx.material.icons)


  androidTestImplementation(libs.hilt.android.testing)

  // Testing:[androidx.test, Robolectric]
  implementation(libs.androidx.monitor)

  testImplementation("org.robolectric:robolectric:4.13")
  testImplementation("com.google.truth:truth:1.4.2")
  androidTestImplementation("com.google.truth:truth:1.4.2")
  androidTestImplementation ("androidx.arch.core:core-testing:2.2.0")

  //
  debugImplementation("com.google.truth:truth:1.1.2")
  debugImplementation("androidx.arch.core:core-testing:2.2.0")
  debugImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.2")

  // Testing:[Default]
  androidTestImplementation(platform(libs.androidx.compose.bom))

  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.test.espresso.core)
  androidTestImplementation(libs.androidx.ui.test.junit4)
  debugImplementation(libs.androidx.ui.tooling)
  debugImplementation(libs.androidx.ui.test.manifest)
}