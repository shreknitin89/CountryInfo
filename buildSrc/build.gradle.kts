plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

tasks.clean {
    delete(rootProject.buildDir)
}
