package crux.visualisation.query

import crux.api.ICruxDatasource
import crux.api.query.conversion.q
import crux.api.underware.kw
import crux.api.underware.sym
import crux.visualisation.domain.CruxAdapter.Companion.TYPE_COLOUR
import crux.visualisation.domain.VisualisationColor
import crux.visualisation.domain.VisualisationColors

private val hex = "hex".sym
private val entity = "id".sym
private val type = "type".kw
private val color = "colour".kw

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