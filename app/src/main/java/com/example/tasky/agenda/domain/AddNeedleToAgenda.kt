package com.example.tasky.agenda.domain

import com.example.tasky.agenda.domain.model.AgendaItem

object AddNeedleToAgenda {

    operator fun invoke(
        timeNow: Long,
        agendaList: MutableList<AgendaItem>
    ): MutableList<AgendaItem> {
        var needlePosition = 0

        agendaList.forEachIndexed { index, agendaItem ->
            if (timeNow < agendaItem.time) {
                needlePosition = index
            }
        }

        agendaList.add(needlePosition, AgendaItem.Needle)

        return agendaList
    }
}