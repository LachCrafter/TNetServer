plugins {
    id("net.fabricmc.fabric-loom") version "1.18-SNAPSHOT"
    id("maven-publish")
}

val version = project
val group = project.property("maven_group")

val archivesName = project.property("archives_base_name")

repositories {
    // Add repositories to retrieve artifacts from in here.
    // You should only use this when depending on other mods because
    // Loom adds the essential maven repositories to download Minecraft and libraries from automatically.
    // See https://docs.gradle.org/current/userguide/declaring_repositories.html
    // for more information about repositories.
    mavenCentral()
}

dependencies {
    // To change the versions see the gradle.properties file
    minecraft(libs.minecraft)
    implementation(libs.fabric.loader)
    implementation(libs.fabric.api)
}

tasks.withType<ProcessResources> {
    inputs.property("version", rootProject.property("project_version"))
    inputs.property("minecraft_version", libs.versions.minecraft.get())
    inputs.property ("loader_version", libs.versions.fabric.loader.get())
    filteringCharset = "UTF-8"

    filesMatching("fabric.mod.json") {
        expand(mapOf("version" to rootProject.property("project_version"),
                "minecraft_version" to libs.versions.minecraft.get(),
                "loader_version" to libs.versions.fabric.loader.get()))
    }
}

val targetJavaVersion = 25
tasks.withType<JavaCompile>().configureEach {
    // ensure that the encoding is set to UTF-8, no matter what the system default is
    // this fixes some edge cases with special characters not displaying correctly
    // see http://yodaconditions.net/blog/fix-for-java-file-encoding-problems-with-gradle.html
    // If Javadoc is generated, this must be specified in that task too.
    options.encoding = "UTF-8"
    if (targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible) {
        options.release.set(targetJavaVersion)
    }
}

java {
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)
    if (JavaVersion.current() < javaVersion) {
        toolchain.languageVersion = JavaLanguageVersion.of(targetJavaVersion)
    }
    // Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
    // if it is present.
    // If you remove this line, sources will not be generated.
    withSourcesJar()
}

tasks.withType<Jar> {
    from("LICENSE") {
        rename { "${it}_${project.property("archives_base_name")}"}
    }
}

// configure the maven publication
publishing {
    publications {
        create<MavenPublication>("release") {
            artifactId to project.property("archives_base_name")
            from(components["java"])
        }
    }

    // See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
    repositories {
        // Notice: This block does NOT have the same function as the block in the top level.
        // Add repositories to publish to here.
        // The repositories here will be used for publishing your artifact, not for
        // retrieving dependencies.
    }
}
