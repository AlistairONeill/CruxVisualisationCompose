package crux.visualisation.domain.visualisation

import androidx.compose.runtime.MutableState
import crux.visualisation.MutableDerivative
import crux.visualisation.domain.TemporalData

enum class VisualisationMode {
    SimpleColorGraph, NetworkGraph
}

fun MutableState<TemporalData>.toVisualisationMode(): MutableDerivative<VisualisationMode> {
    return MutableDerivative(value.visualisationMode) { value = value.withVisualisationMode(it) }
}