package com.dinostreaming

import com.lagradost.cloudstream3.plugins.Plugin
import com.lagradost.cloudstream3.plugins.PluginContext

class DinostreamingPlugin : Plugin() {
    override fun load(context: PluginContext) {
        registerMainAPI(DinoStreaming())
    }
}
