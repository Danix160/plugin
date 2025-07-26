package com.dinostreaming

import com.lagradost.cloudstream3.plugins.CloudstreamPlugin

@CloudstreamPlugin
class DinoStreamingPlugin {
    fun load() {
        registerMainAPI(DinoStreaming())
    }
}
