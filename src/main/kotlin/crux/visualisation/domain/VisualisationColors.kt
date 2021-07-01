package crux.visualisation.domain

enum class VisualisationColor(val hex: String) {
    Color1("#a6cee3"),
    Color2("#1f78b4"),
    Color3("#b2df8a"),
    Color4("#33a02c"),
    Color5("#fb9a99"),
    Color6("#e31a1c"),
    Color7("#fdbf6f"),
    Color8("#ff7f00"),
}

fun String.hexToColor(): Long =
    if (this[0] == '#') { // Use a long to avoid rollovers on #ffXXXXXX
        var color = substring(1).toLong(16)
        if (length == 7) { // Set the alpha value
            color = color or -0x1000000
        } else require(length == 9) { "Unknown color" }
        color
    } else {
        throw IllegalArgumentException("Unknown color")
    }

val VisualisationColor.renderColor get() = hex.hexToColor()