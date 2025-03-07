
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("com.google.devtools.ksp") version "2.0.20-1.0.25" apply false
    id("com.google.dagger.hilt.android") version "2.49" apply false
    kotlin("plugin.serialization") version "2.0.20"
    alias(libs.plugins.compose.compiler) apply false
    id("androidx.room") version "2.6.1" apply false
}