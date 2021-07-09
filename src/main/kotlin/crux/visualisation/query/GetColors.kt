package crux.visualisation.query

import crux.api.ICruxDatasource
import crux.api.query.conversion.q
import crux.visualisation.domain.CruxAdapter.Companion.TYPE_COLOUR
import crux.visualisation.domain.CruxAdapter.Companion.TYPE_LINK
import crux.visualisation.domain.VisualisationColor
import crux.visualisation.domain.VisualisationColors
import crux.visualisation.domain.visualisation.VisualisationQuery
import crux.visualisation.domain.visualisation.VisualisationQuery.Direct
import crux.visualisation.domain.visualisation.VisualisationQuery.Identity


fun ICruxDatasource.getColors(): List<VisualisationColor> =
    q {
        find {
            + hex
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
        Identity -> getIdentityColour(input)
        Direct -> getDirect(input)
    }

private fun getIdentityColour(input: VisualisationColor) = listOf(input)

private fun ICruxDatasource.getDirect(input: VisualisationColor) =
    q {
        find {
            + hex
        }

        where {
            start has type eq TYPE_COLOUR
            end has type eq TYPE_COLOUR
            link has type eq TYPE_LINK
            link has fromKey eq start
            link has toKey eq end
            start has color eq input.hex
            end has color eq hex
        }
    }
        .map { it.single() as String }
        .map(VisualisationColors::get)