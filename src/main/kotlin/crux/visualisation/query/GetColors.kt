package crux.visualisation.query

import crux.api.ICruxDatasource
import crux.api.query.conversion.q
import crux.visualisation.domain.CruxAdapter.Companion.TYPE_COLOUR
import crux.visualisation.domain.VisualisationColor
import crux.visualisation.domain.VisualisationColors
import crux.visualisation.domain.visualisation.VisualisationQuery


fun ICruxDatasource.getColors(): List<VisualisationColor> =
    q {
        find {
            +hex
        }

        where {
            entity has type eq TYPE_COLOUR
            entity has color eq hex
        }
    }
        .map { it.single() as String }
        .map(VisualisationColors::get)

fun ICruxDatasource.getHighlightedColours(query: VisualisationQuery, input: VisualisationColor?): List<VisualisationColor> =
    if (input == null) emptyList() else when (query) {
        VisualisationQuery.IDENTITY -> getIdentityColour(input)
    }

private fun ICruxDatasource.getIdentityColour(input: VisualisationColor) = listOf(input)