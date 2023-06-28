package com.example.tasky.itemdetail.presentation.model

import com.example.tasky.R

sealed class VisitorType(
    val name: Int,
    val isSelected: Boolean
) {
    class All(
        name: Int = R.string.all,
        isSelected: Boolean
    ) : VisitorType(
        name,
        isSelected
    )

    class Going(
        name: Int = R.string.going,
        isSelected: Boolean
    ) : VisitorType(
        name,
        isSelected
    )

    class NotGoing(
        name: Int = R.string.not_going,
        isSelected: Boolean
    ) : VisitorType(
        name,
        isSelected
    )
}
