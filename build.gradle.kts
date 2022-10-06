buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:${BuildPlugins.androidGradle}")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application").version(BuildPlugins.androidGradle).apply(false)
    id("com.android.library").version(BuildPlugins.androidGradle).apply(false)
    id("org.jetbrains.kotlin.android").version(kotlinVersion).apply(false)
    id("org.jetbrains.kotlin.jvm").version(kotlinVersion).apply(false)
}