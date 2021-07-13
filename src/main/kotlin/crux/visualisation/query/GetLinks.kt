package crux.visualisation.query

import crux.api.ICruxDatasource
import crux.api.query.conversion.q
import crux.visualisation.domain.CruxAdapter.Companion.TYPE_COLOUR
import crux.visualisation.domain.CruxAdapter.Companion.TYPE_LINK
import crux.visualisation.domain.VisualisationColor
import crux.visualisation.domain.VisualisationColors
import crux.visualisation.domain.visualisation.Link
import crux.visualisation.domain.visualisation.VisualisationQuery
import crux.visualisation.domain.visualisation.VisualisationQuery.*

fun ICruxDatasource.getLinks(): List<Link> =
    q {
        find {
            +from
            +to
        }

        where {
            link has type eq TYPE_LINK
            link has fromKey eq from
            link has toKey eq to
        }
    }.map {
        Link(
            VisualisationColors[it[0] as String],
            VisualisationColors[it[1] as String]
        )
    }

fun ICruxDatasource.getHighlightedLinks(query: VisualisationQuery, input: VisualisationColor?): List<Link> =
    if (input == null) emptyList() else when (query) {
        Identity -> getIdentity()
        Direct -> getDirect(input)
        Recursive -> getRecursive(input)
        Cycle -> getIdentity() //TODO
    }

private fun getIdentity() = emptyList<Link>()

private fun ICruxDatasource.getDirect(input: VisualisationColor) =
    q {
        find {
            +entity
            +to
        }

        where {
            link has type eq TYPE_LINK
            entity has type eq TYPE_COLOUR
            entity has color eq input.hex
            link has fromKey eq entity
            link has toKey eq to
        }
    }.map {
        Link(
            VisualisationColors[it[0] as String],
            VisualisationColors[it[1] as String]
        )
    }

private fun ICruxDatasource.getRecursive(input: VisualisationColor) =
    q {
        find {
            +from
            +to
        }

        where {
            rule(linksVia) (start, link)
            start has type eq TYPE_COLOUR
            start has color eq input.hex
            link has type eq TYPE_LINK
            link has fromKey eq from
            link has toKey eq to
        }

        rules {
            def(linksVia) [start] (link) {
                start has type eq TYPE_COLOUR
                link has type eq TYPE_LINK
                link has fromKey eq start
            }

            def(linksVia) [start] (link) {
                end has type eq TYPE_COLOUR
                intermediate has type eq TYPE_LINK
                intermediate has fromKey eq start
                intermediate has toKey eq end
                rule(linksVia) (end, link)
            }
        }
    }.map {
        Link(
            VisualisationColors[it[0] as String],
            VisualisationColors[it[1] as String]
        )
    }