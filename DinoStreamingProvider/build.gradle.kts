plugins {
    id("cloudstream-plugin") version "0.0.1" // questo è un plugin custom, definito in `pluginManagement` o `buildscript`
    kotlin("jvm") version "1.9.10"
}

cloudstreamPlugin {
    language = "it"
    status: 1
    authors = listOf("doGior")
    tvTypes = listOf("Movie", "TvSeries")
    requiresResources = false
    iconUrl = "https://dinostreaming.it/favicon.ico"
}

dependencies {
     implementation("com.github.recloudstream.cloudstream:library:v4.5.2")
}

repositories {
    google()
    mavenCentral()
    maven("https://jitpack.io")
}
