package crux.visualisation.query

import crux.api.ICruxDatasource
import crux.api.query.conversion.q
import crux.visualisation.domain.CruxAdapter.Companion.TYPE_COLOUR
import crux.visualisation.domain.CruxAdapter.Companion.TYPE_LINK
import crux.visualisation.domain.VisualisationColor
import crux.visualisation.domain.VisualisationColors
import crux.visualisation.domain.visualisation.Link
import crux.visualisation.domain.visualisation.VisualisationQuery
import crux.visualisation.domain.visualisation.VisualisationQuery.Direct
import crux.visualisation.domain.visualisation.VisualisationQuery.Identity

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
        Identity -> getIdentityLinks()
        Direct -> getDirectLinks(input)
    }

private fun getIdentityLinks() = emptyList<Link>()

private fun ICruxDatasource.getDirectLinks(input: VisualisationColor) =
    q {
        find {
            + entity
            + to
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