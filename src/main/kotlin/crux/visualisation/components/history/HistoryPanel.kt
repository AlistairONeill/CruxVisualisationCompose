@file:Suppress("FunctionName")

package crux.visualisation.components.history

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import crux.visualisation.domain.history.HistoryItem

@Composable
fun HistoryPanel(
    width: Dp,
    history: List<HistoryItem>
) {
    LazyColumn {
        items(history) { historyItem ->
            HistoryItemPanel(
                width,
                historyItem
            )
        }
    }
}