package com.github.app.ui.repo.compose.component.button

import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.github.app.ui.repo.screen.state.FilterButtonViewState
import kotlinx.collections.immutable.ImmutableList

@Composable
fun RepositoryFilterButtons(
    filterButtons: ImmutableList<FilterButtonViewState>,
    onFilterButtonClick: (FilterButtonViewState) -> Unit,
    modifier: Modifier = Modifier,
) {
    SingleChoiceSegmentedButtonRow(modifier) {
        filterButtons.forEachIndexed { index, filterButton ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = filterButtons.size,
                ),
                onClick = { onFilterButtonClick(filterButton) },
                selected = filterButton.isSelected,
                label = { Text(text = stringResource(filterButton.textRes), maxLines = 1) },
            )
        }
    }
}
