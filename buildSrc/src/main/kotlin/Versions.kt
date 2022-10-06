import org.gradle.api.JavaVersion

/**
 * Created by Nitin Dasari on 10/5/22.
 */
const val kotlinVersion = "1.7.10" // https://kotlinlang.org/docs/releases.html#release-details
val javaVersion = JavaVersion.VERSION_11

object BuildPlugins {
    const val androidGradle = "7.3.0" // https://developer.android.com/studio/releases/gradle-plugin
}

object Libs {

    const val appCompat = "1.4.2" // https://mvnrepository.com/artifact/androidx.appcompat/appcompat
    const val androidCore = "1.9.0"
    const val androidMaterial =
        "1.6.1" // https://mvnrepository.com/artifact/com.google.android.material/material
   const val fragment = "1.5.0" // https://developer.android.com/jetpack/androidx/releases/fragment
    const val navigation =
        "2.5.2" // https://developer.android.com/jetpack/androidx/releases/navigation

    const val coroutines = "1.6.4" // https://github.com/Kotlin/kotlinx.coroutines/releases
    const val retrofit = "2.9.0" // https://github.com/square/retrofit/releases
    const val koin = "3.2.2" // https://insert-koin.io/docs/setup/v3.2

    // region Unit and Instrumented Testing
    const val junit = "4.13.2" // https://mvnrepository.com/artifact/junit/junit
    const val testRules = "1.4.0" // https://mvnrepository.com/artifact/androidx.test/rules
    const val androidXJUnit = "1.1.3" // https://mvnrepository.com/artifact/androidx.test.ext/junit-ktx
    const val mockitoKotlin = "4.0.0" // https://mvnrepository.com/artifact/org.mockito.kotlin/mockito-kotlin
    const val espresso = "3.4.0" // https://developer.android.com/training/testing/espresso/setup.html
    const val turbine = "0.10.0"
    // endregion
}
