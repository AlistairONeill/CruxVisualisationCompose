package crux.visualisation.query

import crux.api.ICruxDatasource
import crux.visualisation.domain.VisualisationColor
import crux.visualisation.domain.visualisation.Link
import crux.visualisation.domain.visualisation.VisualisationQuery
import crux.visualisation.domain.visualisation.VisualisationQuery.IDENTITY

fun ICruxDatasource.getHighlightedLinks(query: VisualisationQuery, input: VisualisationColor?): List<Link> =
    if (input == null) emptyList() else when (query) {
        IDENTITY -> getIdentityLinks(input)
    }

private fun ICruxDatasource.getIdentityLinks(input: VisualisationColor) = emptyList<Link>()