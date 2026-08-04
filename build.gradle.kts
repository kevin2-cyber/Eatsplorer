// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/google/secrets-gradle-plugin")
            credentials {
                username = project.findProperty("GITHUB_USER") as String? ?: System.getenv("GITHUB_USER")
                password = project.findProperty("GITHUB_TOKEN") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
    dependencies {
        classpath(libs.secrets.gradle.plugin)
        classpath(libs.google.services)
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin) apply false
    alias(libs.plugins.jetbrains.kotlin.plugin.compose) apply false
    alias(libs.plugins.google.gms.google.services) apply false
    alias(libs.plugins.kotlin.android) apply false
}
