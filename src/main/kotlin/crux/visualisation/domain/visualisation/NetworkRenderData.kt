package crux.visualisation.domain.visualisation

import crux.visualisation.domain.TemporalData
import crux.visualisation.domain.VisualisationColor
import crux.visualisation.domain.visualisation.VisualisationData.NetworkGraphData

data class NetworkRenderData(
    val colors: List<VisualisationColor>,
    val links: List<Link>,
    val selectedColor: VisualisationColor?,
    val highlightedColours: List<VisualisationColor>,
    val highlightedLinks: List<Link>
)

data class Link(val from: VisualisationColor, val to: VisualisationColor)

fun TemporalData.toNetworkRenderDataSource(): () -> NetworkRenderData = {
    val visualisationData = visualisationData as? NetworkGraphData ?: throw Exception("Ruh roh")
    val query = visualisationData.query

    NetworkRenderData(
        cruxAdapter.getColors(),
        cruxAdapter.getLinks(),
        selectedVisualisationColor,
        cruxAdapter.getHighlightedColours(query, selectedVisualisationColor),
        cruxAdapter.getHighlightedLinks(query, selectedVisualisationColor)
    )
}