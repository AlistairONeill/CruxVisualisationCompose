package crux.visualisation.domain.visualisation

import crux.visualisation.domain.TemporalData
import crux.visualisation.domain.VisualisationColor

data class NetworkRenderData(
    val colors: List<VisualisationColor>,
    val links: List<Link>,
    val selectedColor: VisualisationColor?
)

data class Link(val from: VisualisationColor, val to: VisualisationColor)

fun TemporalData.toNetworkRenderDataSource(): () -> NetworkRenderData = {
    NetworkRenderData(
        cruxAdapter.getColors(),
        cruxAdapter.getLinks(),
        selectedVisualisationColor
    )
}