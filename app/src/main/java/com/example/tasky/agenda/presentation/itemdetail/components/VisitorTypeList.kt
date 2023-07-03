package com.example.tasky.agenda.presentation.itemdetail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tasky.agenda.presentation.itemdetail.model.VisitorType

@Composable
fun VisitorTypeList(
    selectedFilter: VisitorType,
    modifier: Modifier = Modifier,
    onClick: (VisitorType) -> Unit
) {
    Row(
        modifier = modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        VisitorType.values().forEach { type ->
            VisitorTypePill(
                modifier = Modifier.weight(1f),
                type = type,
                isSelected = type == selectedFilter,
            ) { onClick(type) }
        }
    }

    Spacer(modifier = Modifier.size(20.dp))
}

@Preview
@Composable
fun VisitorTypeListPreview() {
    VisitorTypeList(selectedFilter = VisitorType.ALL) {}
}