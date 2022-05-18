buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.2.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.20")
        classpath("com.github.ben-manes:gradle-versions-plugin:0.42.0")
    }
}

tasks.create("clean", Delete::class) {
    delete(rootProject.buildDir)
}
