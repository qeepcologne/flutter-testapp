pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("flutter_module/build/host/outputs/repo")
        maven("https://storage.googleapis.com/download.flutter.io")
    }
}

rootProject.name = "My Application"
include(":app")
 