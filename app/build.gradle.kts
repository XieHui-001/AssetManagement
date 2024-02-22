plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.tools.assetmanagement"
    compileSdk = 34

    val apiDomain = "\"https://www.baidu.com\""

    defaultConfig {
        applicationId = "com.tools.assetmanagement"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        vectorDrawables.useSupportLibrary = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String","API_DOMAIN","$apiDomain")

        ndk {
            abiFilters.add("armeabi-v7a")
            abiFilters.add("arm64-v8a")
            abiFilters.add("x86")
            abiFilters.add("armeabi")
            abiFilters.add("x86_64")
        }

    }


    signingConfigs {
        register("release") {
            storeFile = file("E:\\AssetManagement\\asset.jks")
            storePassword = "123456"
            keyAlias = "asset"
            keyPassword = "123456"
            enableV1Signing = true
            enableV2Signing = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs["release"]
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
        viewBinding = true
        buildConfig = true
    }
}



dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("androidx.activity:activity-ktx:1.7.2")
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")

    implementation("com.github.bumptech.glide:glide:4.12.0")
    implementation("com.github.bumptech.glide:okhttp3-integration:4.12.0")

    //网络请求框架
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.9.0")
    implementation("com.squareup.moshi:moshi:1.9.2") { exclude(group = "com.squareup.okio") }

    //刷新框架
    implementation("com.scwang.smart:refresh-layout-kernel:2.0.3")      //核心必须依赖
    implementation("com.scwang.smart:refresh-header-classics:2.0.3")    //经典刷新头
    implementation("com.scwang.smart:refresh-footer-classics:2.0.3")    //经典加载

    //EventBus
    implementation("org.greenrobot:eventbus:3.2.0")

    //牛逼的工具类
    implementation("com.blankj:utilcodex:1.31.1")
    // 本地缓存架构
    implementation("com.tencent:mmkv-static:1.2.10")

    //权限管理
    implementation("com.github.getActivity:XXPermissions:11.6")

    //屏幕适配
    implementation("com.github.JessYanCoding:AndroidAutoSize:v1.2.1")

    // 沉浸式依赖包
    implementation("com.geyifeng.immersionbar:immersionbar:3.2.2")
    // kotlin扩展（可选）
    implementation("com.geyifeng.immersionbar:immersionbar-ktx:3.2.2")

    //网络请求cookie缓存
    implementation("com.github.franmontiel:PersistentCookieJar:v1.0.1")

    implementation ("com.github.getActivity:GsonFactory:9.5")
    // Kotlin 反射库：用于反射 Kotlin data class 类对象
    implementation ("org.jetbrains.kotlin:kotlin-reflect:1.5.10")


}