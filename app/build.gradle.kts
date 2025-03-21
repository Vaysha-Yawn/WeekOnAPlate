import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("kotlinx-serialization")
    alias(libs.plugins.compose.compiler)
    id ("androidx.room")
}

room {
    schemaDirectory("release", "$projectDir/schemas/release")
    schemaDirectory("debug", "$projectDir/schemas/debug")
}

android {

    namespace = "week.on.a.plate"
    compileSdk = 35

    defaultConfig {
        applicationId = "week.on.a.plate"
        minSdk = 28
        targetSdk = 34
        versionCode = 6
        versionName = "1.3"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField("String", "cookPlannerAdsIdWeek", "\"${cookPlannerAdsIdWeek}\"")
        buildConfigField("String", "cookPlannerAdsIdDay", "\"${cookPlannerAdsIdDay}\"")
        buildConfigField("String", "shoppingAdsId", "\"${shoppingAdsId}\"")
        buildConfigField("String", "menuWeekAdsId", "\"${menuWeekAdsId}\"")
        buildConfigField("String", "menuDayAdsId", "\"${menuDayAdsId}\"")
    }

    buildTypes {
        debug {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {
    implementation ("com.yandex.android:mobileads:7.9.0")

    implementation("io.coil-kt:coil-compose:2.7.0")

    //nav
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.2")
    implementation("androidx.navigation:navigation-compose:2.8.0")

    //hilt
    implementation("com.google.dagger:hilt-android:2.51.1")
    implementation(libs.androidx.webkit)
    ksp("com.google.dagger:hilt-android-compiler:2.51.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    //room
    implementation(libs.gson)
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    androidTestImplementation(libs.androidx.room.testing)
    implementation(libs.androidx.room.paging)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")
    testImplementation("io.mockk:mockk:1.13.7")
}


val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()){
    localProperties.load(FileInputStream(localPropertiesFile))
}
val cookPlannerAdsIdWeek = localProperties.getProperty("cookPlannerAdsIdWeek")?:""
val cookPlannerAdsIdDay = localProperties.getProperty("cookPlannerAdsIdDay")?:""
val shoppingAdsId = localProperties.getProperty("shoppingAdsId")?:""
val menuWeekAdsId = localProperties.getProperty("menuWeekAdsId")?:""
val menuDayAdsId = localProperties.getProperty("menuDayAdsId")?:""
