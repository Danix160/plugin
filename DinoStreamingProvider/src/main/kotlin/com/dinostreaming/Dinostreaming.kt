package com.dinostreaming

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.plugins.CloudstreamPlugin
import com.lagradost.cloudstream3.utils.*

@CloudstreamPlugin
class DinoStreaming : MainAPI() {
    override var mainUrl = "https://www.dinostreaming.it"
    override var name = "DinoStreaming"
    override val hasMainPage = true
    override var lang = "it" // ✅ OK

   override val mainPage = listOf(
    MainPageData("Film", "$mainUrl/film-streaming/"),
    MainPageData("Serie TV", "$mainUrl/serie-tv-streaming/")
)

    override suspend fun getMainPage(page: Int, request: MainPageRequest): HomePageResponse {
        val doc = app.get(request.data).document
        val items = doc.select("div.result-item").mapNotNull {
            val title = it.selectFirst("h3.result-title")?.text() ?: return@mapNotNull null
            val link = it.selectFirst("a")?.attr("href") ?: return@mapNotNull null
            val poster = it.selectFirst("img")?.attr("abs:src")
            val type = if (request.name.contains("Serie", ignoreCase = true)) TvType.TvSeries else TvType.Movie

            newMovieSearchResponse(title, link, type) {
                this.posterUrl = poster
            }
        }

        return newHomePageResponse(request.name, items)
    }

    override suspend fun search(query: String): List<SearchResponse> {
        val url = "$mainUrl/?s=${query.trim().replace(" ", "+")}"
        val doc = app.get(url).document
        return doc.select("div.result-item").mapNotNull {
            val title = it.selectFirst("h3.result-title")?.text() ?: return@mapNotNull null
            val link = it.selectFirst("a")?.attr("href") ?: return@mapNotNull null
            val poster = it.selectFirst("img")?.attr("abs:src")
            val type = if (link.contains("/serie-tv/")) TvType.TvSeries else TvType.Movie

            newMovieSearchResponse(title, link, type) {
                this.posterUrl = poster
            }
        }
    }

    override suspend fun load(url: String): LoadResponse? {
        val doc = app.get(url).document
        val title = doc.selectFirst("h1")?.text() ?: return null
        val poster = doc.select("img").firstOrNull()?.attr("abs:src")
        val description = doc.select("div.entry-content p").firstOrNull()?.text()

        val isSerie = url.contains("/serie-tv/")

        return if (isSerie) {
            val episodes = doc.select("ul.episodios li").mapNotNull {
                val epLink = it.selectFirst("a")?.attr("href") ?: return@mapNotNull null
                val epName = it.selectFirst("a")?.text() ?: ""
                Episode(epLink, epName)
            }

            newTvSeriesLoadResponse(title, url, TvType.TvSeries, episodes) {
                this.posterUrl = poster
                this.plot = description
            }
        } else {
            newMovieLoadResponse(title, url, TvType.Movie, url) {
                this.posterUrl = poster
                this.plot = description
            }
        }
    }

 override suspend fun loadLinks(
    data: String,
    isCasting: Boolean,
    subtitleCallback: (SubtitleFile) -> Unit,
    callback: (ExtractorLink) -> Unit
): Boolean {
    val doc = app.get(data).document
    val iframe = doc.select("iframe").firstOrNull()?.attr("src") ?: return false

    loadExtractor(iframe, data, subtitleCallback, callback)
    return true
}

}
