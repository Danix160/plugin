package com.dinostreaming

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.lagradost.cloudstream3.plugins.CloudstreamPlugin
import com.lagradost.cloudstream3.plugins.Plugin

@CloudstreamPlugin
class DinostreamingPlugin: Plugin() {
    private var activity: AppCompatActivity? = null

    override fun load(context: Context) {
        activity = context as? AppCompatActivity

        // All providers should be added in this manner
        registerMainAPI(DinoStreamingProvider())

        openSettings = {
            val frag = BlankFragment(this)
            activity?.let {
                frag.show(it.supportFragmentManager, "Frag")
            }
        }
    }
}
