plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.jetbrains.kotlin.android)
  alias(libs.plugins.ksp)
//  alias(libs.plugins.hilt)
}

android {
  namespace = "com.antoan.kodetest.common"
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
}

dependencies {
  // Room
  implementation(libs.androidx.room.runtime)
  implementation(libs.androidx.room.ktx)
  ksp(libs.androidx.room.compiler)

  // Network:[Retrofit, Moshi, OkHttp, MockWebServer]
  implementation(libs.converter.moshi)
  implementation(libs.moshi)
  implementation(libs.okhttp)
  implementation(libs.logging.interceptor)
  implementation(libs.retrofit)
  ksp(libs.moshi.kotlin.codegen)

  // DI
  implementation(libs.hilt.android)
  ksp(libs.hilt.compiler)

}