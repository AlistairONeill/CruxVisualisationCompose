package crux.visualisation

import androidx.compose.desktop.Window
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import crux.api.CruxK
import crux.visualisation.components.MainComponent
import crux.visualisation.domain.TemporalData

fun main() = Window(
    title = "Crux Visualisation"
){
    val crux = CruxK.startNode()
    val temporalData = remember { mutableStateOf(TemporalData.new(crux)) }

    MaterialTheme {
        MainComponent(temporalData)
    }
}