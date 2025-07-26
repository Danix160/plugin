package com.dinostreaming

import com.lagradost.cloudstream3.plugins.CloudstreamPlugin
import com.lagradost.cloudstream3.plugins.registerMainAPI // <-- Import mancante!

@CloudstreamPlugin
class DinoStreamingPlugin {
    fun load() {
        registerMainAPI(DinoStreaming())
    }
}
