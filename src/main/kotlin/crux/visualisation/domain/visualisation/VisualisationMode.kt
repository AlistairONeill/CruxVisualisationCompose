package crux.visualisation.domain.visualisation

import androidx.compose.runtime.MutableState
import crux.visualisation.MutableDerivative
import crux.visualisation.domain.TemporalData
import crux.visualisation.domain.visualisation.VisualisationData.NetworkGraphData
import crux.visualisation.domain.visualisation.VisualisationData.SimpleColorGraphData
import crux.visualisation.domain.visualisation.VisualisationMode.NetworkGraph
import crux.visualisation.domain.visualisation.VisualisationMode.SimpleColorGraph
import crux.visualisation.domain.visualisation.VisualisationQuery.Direct

enum class VisualisationMode {
    SimpleColorGraph, NetworkGraph
}

sealed class VisualisationData(val visualisationMode: VisualisationMode) {
    object SimpleColorGraphData: VisualisationData(SimpleColorGraph)
    data class NetworkGraphData(val query: VisualisationQuery): VisualisationData(NetworkGraph)
}

enum class VisualisationQuery {
    Direct, Recursive, Cycle
}

fun VisualisationData.toMode(mode: VisualisationMode) =
    when (mode) {
        SimpleColorGraph -> SimpleColorGraphData
        NetworkGraph -> when (this) {
            SimpleColorGraphData -> NetworkGraphData(Direct)
            is NetworkGraphData -> this
        }
    }

fun MutableState<TemporalData>.toVisualisationData(): MutableDerivative<VisualisationData> =
    MutableDerivative(value.visualisationData) { value = value.withVisualisationData(it) }

fun MutableDerivative<VisualisationData>.toVisualisationMode(): MutableDerivative<VisualisationMode> =
    MutableDerivative(value.visualisationMode) { set(value.toMode(it))}

fun MutableDerivative<VisualisationData>.toQuery(): MutableDerivative<VisualisationQuery> {
    val value = value as NetworkGraphData
    return MutableDerivative(value.query) { set(value.copy(query = it))}
}