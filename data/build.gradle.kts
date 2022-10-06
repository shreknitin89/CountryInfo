plugins {
    id("java-library")
    kotlin("jvm")
}

java {
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
}

kotlin {
    sourceSets {
        test {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test-annotations-common")
                implementation("org.jetbrains.kotlin:kotlin-test-common")
            }
        }
    }
}

dependencies {
    implementation(project(":domain"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Libs.coroutines}")
    api("com.squareup.retrofit2:retrofit:${Libs.retrofit}")
    api("com.squareup.retrofit2:converter-gson:${Libs.retrofit}")
    testApi("junit:junit:${Libs.junit}")
    testApi("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Libs.coroutines}")
    testApi("org.mockito.kotlin:mockito-kotlin:${Libs.mockitoKotlin}")
    testApi("app.cash.turbine:turbine:${Libs.turbine}")

}