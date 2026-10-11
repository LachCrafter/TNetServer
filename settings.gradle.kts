rootProject.name = "TNetServer"
include("common", "paper", "fabric")

pluginManagement {
    repositories {
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }
        gradlePluginPortal()
    }
}
