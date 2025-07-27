dependencies {
    dependencies {
    implementation("androidx.databinding:viewbinding:8.2.0") // o compatibile con AGP in uso
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("com.github.recloudstream.cloudstream:library:v4.5.2")
}
version = 1 // ✅ questa è fuori dal blocco cloudstream

cloudstream {
    language = "it"
    description = "Movies and Shows from DinoStreaming"
    authors = listOf("doGior")
    status = 1
    tvTypes = listOf("Movie", "TvSeries")
    requiresResources = false
    iconUrl = "https://dinostreaming.it/favicon.ico"
}

android {
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}
