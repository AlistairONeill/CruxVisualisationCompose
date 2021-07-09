package crux.visualisation.query

import crux.api.ICruxDatasource
import crux.api.query.conversion.q
import crux.visualisation.domain.CruxAdapter.Companion.TYPE_COLOUR
import crux.visualisation.domain.CruxAdapter.Companion.TYPE_LINK
import crux.visualisation.domain.VisualisationColor
import crux.visualisation.domain.VisualisationColors
import crux.visualisation.domain.visualisation.VisualisationQuery
import crux.visualisation.domain.visualisation.VisualisationQuery.*


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
        Identity -> getIdentity(input)
        Direct -> getDirect(input)
        Recursive -> getRecursive(input)
    }

private fun getIdentity(input: VisualisationColor) = listOf(input)

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

private fun ICruxDatasource.getRecursive(input: VisualisationColor) =
    q {
        find {
            + hex
        }

        where {
            start has color eq input.hex
            end has color eq hex

            rule(linksTo) (start, end)
        }

        rules {
            def(linksTo) (start, end) {
                start has type eq TYPE_COLOUR
                end has type eq TYPE_COLOUR
                link has type eq TYPE_LINK
                link has fromKey eq start
                link has toKey eq end
            }

            def(linksTo) (start, end) {
                rule(linksTo) (start, intermediate)
                rule(linksTo) (intermediate, end)
            }
        }
    }
        .map { it.single() as String }
        .map(VisualisationColors::get)