package com.daniyars.translatorapp.models

object DataSource {
    val items by lazy {
        mutableListOf<VideoObjects>().apply {
            repeat(5) {
                add(VideoObjects("the secret life of walter mitty", "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
                    "",
                    "Молодого мажора по имени Азамат, привыкшего к комфортной городской жизни, по стечению непредвиденных обстоятельств, отправляют работать Акимом в отдаленный посёлок - Таза Булак. ... С первого дня пребывания в ауле, новоиспечённый Аким мечтает о том, чтобы вернуться в город."))

                add(VideoObjects("breaking bad 1.3", "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
                    "",
                    "Молодого мажора по имени Азамат, привыкшего к комфортной городской жизни, по стечению непредвиденных обстоятельств, отправляют работать Акимом в отдаленный посёлок - Таза Булак. ... С первого дня пребывания в ауле, новоиспечённый Аким мечтает о том, чтобы вернуться в город."))

                add(VideoObjects("pulp fiction - go into the story", "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
                    "",
                    "Молодого мажора по имени Азамат, привыкшего к комфортной городской жизни, по стечению непредвиденных обстоятельств, отправляют работать Акимом в отдаленный посёлок - Таза Булак. ... С первого дня пребывания в ауле, новоиспечённый Аким мечтает о том, чтобы вернуться в город."))

                add(VideoObjects("if joaquin phoenix is willing to do it ?!", "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
                    "",
                    "Молодого мажора по имени Азамат, привыкшего к комфортной городской жизни, по стечению непредвиденных обстоятельств, отправляют работать Акимом в отдаленный посёлок - Таза Булак. ... С первого дня пребывания в ауле, новоиспечённый Аким мечтает о том, чтобы вернуться в город."))
            }
        }
    }
}

data class VideoObjects(
    var title: String,
    var videoURL: String,
    var thumbnail: String? = null,
    var description: String? = null
)