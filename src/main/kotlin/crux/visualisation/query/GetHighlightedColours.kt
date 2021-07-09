package crux.visualisation.query

import crux.api.ICruxDatasource
import crux.visualisation.domain.VisualisationColor
import crux.visualisation.domain.visualisation.VisualisationQuery
import crux.visualisation.domain.visualisation.VisualisationQuery.IDENTITY

fun ICruxDatasource.getHighlightedColours(query: VisualisationQuery, input: VisualisationColor?): List<VisualisationColor> =
    if (input == null) emptyList() else when (query) {
        IDENTITY -> getIdentityColour(input)
    }

private fun ICruxDatasource.getIdentityColour(input: VisualisationColor) = listOf(input)