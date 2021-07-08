package crux.visualisation.query

import crux.api.ICruxDatasource
import crux.api.query.conversion.q
import crux.api.underware.kw
import crux.api.underware.sym
import crux.visualisation.domain.CruxAdapter.Companion.TYPE_LINK
import crux.visualisation.domain.VisualisationColors
import crux.visualisation.domain.visualisation.Link

private val entity = "id".sym
private val type = "type".kw
val from = "from".sym
val to = "to".sym
private val fromKey = "from".kw
private val toKey = "to".kw


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