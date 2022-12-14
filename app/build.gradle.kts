plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.countryinfo"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.countryinfo"
        minSdk = 23
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
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
    kotlinOptions {
        jvmTarget = javaVersion.toString()
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation("io.insert-koin:koin-android:${Libs.koin}")
    implementation("androidx.core:core-ktx:${Libs.androidCore}")
    implementation("androidx.appcompat:appcompat:${Libs.appCompat}")
    implementation("com.google.android.material:material:${Libs.androidMaterial}")
    implementation("androidx.navigation:navigation-fragment-ktx:${Libs.navigation}")
    implementation("androidx.navigation:navigation-ui-ktx:${Libs.navigation}")

    testImplementation("junit:junit:${Libs.junit}")
    testImplementation("app.cash.turbine:turbine:${Libs.turbine}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Libs.coroutines}")
    testImplementation("org.mockito.kotlin:mockito-kotlin:${Libs.mockitoKotlin}")
    androidTestImplementation("androidx.test.ext:junit:${Libs.androidXJUnit}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Libs.espresso}")
}