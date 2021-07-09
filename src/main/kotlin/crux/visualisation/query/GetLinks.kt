package crux.visualisation.query

import crux.api.ICruxDatasource
import crux.api.query.conversion.q
import crux.api.underware.kw
import crux.api.underware.sym
import crux.visualisation.domain.CruxAdapter.Companion.TYPE_LINK
import crux.visualisation.domain.VisualisationColor
import crux.visualisation.domain.VisualisationColors
import crux.visualisation.domain.visualisation.Link
import crux.visualisation.domain.visualisation.VisualisationQuery

fun ICruxDatasource.getLinks(): List<Link> =
    q {
        find {
            +from
            +to
        }

        where {
            entity has type eq TYPE_LINK
            entity has fromKey eq from
            entity has toKey eq to
        }
    }.map {
        Link(
            VisualisationColors[it[0] as String],
            VisualisationColors[it[1] as String]
        )
    }

fun ICruxDatasource.getHighlightedLinks(query: VisualisationQuery, input: VisualisationColor?): List<Link> =
    if (input == null) emptyList() else when (query) {
        VisualisationQuery.IDENTITY -> getIdentityLinks(input)
    }

private fun ICruxDatasource.getIdentityLinks(input: VisualisationColor) = emptyList<Link>()