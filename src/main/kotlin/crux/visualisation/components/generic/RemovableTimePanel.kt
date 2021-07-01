@file:Suppress("FunctionName")

package crux.visualisation.components.generic

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import java.time.LocalTime

@Composable
fun RemovableTimePanel(
    width: Dp,
    label: String,
    time: LocalTime,
    valid: Boolean,
    setTime: (LocalTime?) -> Unit,
    removeTime: () -> Unit
) {
    val (value, setValue) = remember { mutableStateOf(time.toString())}

    Row(
        modifier = Modifier.width(width),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        OutlinedTextField(
            value = value,
            onValueChange = { text ->
                setValue(text)
                setTime(kotlin.runCatching { LocalTime.parse(text) }.getOrNull())
            },
            label = { Text(label) },
            isError = !valid,
            leadingIcon = {
                Icon(Icons.Default.DateRange, null)
            }
        )
        MySpacer()
        Icon(Icons.Default.Delete, null, Modifier.clickable { removeTime() })
    }
}