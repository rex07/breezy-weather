package wangdaye.com.geometricweather.settings.preference.composables

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import wangdaye.com.geometricweather.R
import wangdaye.com.geometricweather.common.ui.widgets.Material3CardListItem
import wangdaye.com.geometricweather.common.ui.widgets.defaultCardListItemElevation
import wangdaye.com.geometricweather.theme.compose.DayNightTheme
import wangdaye.com.geometricweather.theme.compose.rememberThemeRipple

@Composable
fun SwitchPreferenceView(
    @StringRes titleId: Int,
    @StringRes summaryOnId: Int,
    @StringRes summaryOffId: Int,
    checked: Boolean,
    enabled: Boolean = true,
    onValueChanged: (Boolean) -> Unit,
) = SwitchPreferenceView(
    title = stringResource(titleId),
    summary = { context, it ->
        context.getString(if (it) summaryOnId else summaryOffId)
    },
    checked = checked,
    enabled = enabled,
    onValueChanged = onValueChanged,
)

@Composable
fun SwitchPreferenceView(
    title: String,
    summary: (Context, Boolean) -> String?,
    checked: Boolean,
    enabled: Boolean = true,
    onValueChanged: (Boolean) -> Unit,
) {
    val state = remember { mutableStateOf(checked) }

    Material3CardListItem(
        elevation = if (enabled) defaultCardListItemElevation else 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(if (enabled) 1f else 0.5f)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberThemeRipple(),
                    onClick = {
                        state.value = !state.value
                        onValueChanged(state.value)
                    },
                    enabled = enabled,
                )
                .padding(dimensionResource(R.dimen.normal_margin)),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    color = DayNightTheme.colors.titleColor,
                    style = MaterialTheme.typography.titleMedium,
                )
                val currentSummary = summary(LocalContext.current, state.value)
                if (currentSummary?.isNotEmpty() == true) {
                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.little_margin)))
                    Text(
                        text = currentSummary,
                        color = DayNightTheme.colors.bodyColor,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }

            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.little_margin)))
            Switch(
                checked = state.value,
                onCheckedChange = {
                    state.value = it
                    onValueChanged(it)
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                    uncheckedThumbColor = MaterialTheme.colorScheme.onSurface.copy(
                        alpha = 0.6f
                    ),
                    checkedTrackColor = MaterialTheme.colorScheme.surface,
                    uncheckedTrackColor = MaterialTheme.colorScheme.surface,
                    disabledCheckedThumbColor = MaterialTheme.colorScheme.onSurface.copy(
                        alpha = ContentAlpha.disabled
                    ),
                    disabledUncheckedThumbColor = MaterialTheme.colorScheme.onSurface.copy(
                        alpha = ContentAlpha.disabled
                    )
                )
            )
        }
    }
}