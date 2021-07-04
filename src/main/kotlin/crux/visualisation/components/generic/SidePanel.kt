@file:Suppress("FunctionName")

package crux.visualisation.components.generic

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import crux.visualisation.components.generic.SidePanelId.History
import crux.visualisation.components.generic.SidePanelId.Input
import crux.visualisation.components.history.HistoryPanel
import crux.visualisation.components.input.TimedInputPanel
import crux.visualisation.domain.history.HistoryItem
import crux.visualisation.domain.input.InputDataWithTimes

@Composable
fun SidePanels(
    inputAcceptor: (InputDataWithTimes) -> Unit,
    history: List<HistoryItem>
) {
    val (expanded, setExpanded) = remember { mutableStateOf(false) }
    val (expandedPanel, setExpandedPanel) = remember { mutableStateOf<SidePanelId?>(null) }

    @Composable
    fun divider() = Divider(
        modifier = Modifier.width(132.dp),
        color = MaterialTheme.colors.surface,
        thickness = 1.dp
    )

    Row {
        Surface(color = MaterialTheme.colors.primary) {
            Column {
                Row(
                    Modifier
                        .width(132.dp)
                        .padding(16.dp)
                        .clickable { setExpanded(!expanded) },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Actions")

                    Icon(
                        if (expanded) {
                            Icons.Default.KeyboardArrowUp
                        }
                        else {
                            Icons.Default.KeyboardArrowDown
                        },
                        null
                    )
                }

                if (expanded) {
                    divider()
                    SidePanelId.values().forEach {
                        SidePanelButton(it, it == expandedPanel, setExpandedPanel)
                        divider()
                    }
                }
            }
        }

        if (expandedPanel != null) {
            val width = 480.dp
            Surface(border = BorderStroke(1.dp, MaterialTheme.colors.primary)) {
                Column {
                    Surface(color = MaterialTheme.colors.primary) {
                        Row(
                            modifier = Modifier.width(width),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(expandedPanel.name)
                            Icon(
                                Icons.Default.Close,
                                null,
                                Modifier.clickable { setExpandedPanel(null) }
                            )
                        }
                    }

                    when (expandedPanel) {
                        History -> HistoryPanel(width, history)
                        Input -> TimedInputPanel(width, inputAcceptor)
                    }
                }
            }
        }
    }
}

@Composable
fun SidePanelButton(
    id: SidePanelId,
    selected: Boolean,
    setExpanded: (SidePanelId?) -> Unit
) {
    Surface(color = if (selected) MaterialTheme.colors.primaryVariant else MaterialTheme.colors.primary)
    {
        Row(Modifier.clickable { setExpanded(id.takeIf{!selected}) }) {
            MySpacer()
            Row(
                modifier = Modifier.width(100.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(id.name)
                Icon(Icons.Default.KeyboardArrowRight, null)
            }
            MySpacer()
        }
    }
}

enum class SidePanelId {
    History, Input
}
