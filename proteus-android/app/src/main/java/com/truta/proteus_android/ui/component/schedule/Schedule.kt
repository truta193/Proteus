package com.truta.proteus_android.ui.component.schedule

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.truta.proteus_android.model.TaskModel
import com.truta.proteus_android.ui.component.Task
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import kotlin.math.roundToInt

//TODO: Check if tasks fits between begin and end time
@Composable
fun Schedule(
    modifier: Modifier = Modifier,
    tasks: List<TaskModel>,
    dayWidth: Dp,
    hourHeight: Dp,
    startTime: LocalTime,
    endTime: LocalTime,
    numDays: Int,
) {
    val dividerColor =
        if (MaterialTheme.colorScheme.background.luminance() > 0.5) Color.LightGray else Color.DarkGray

    //val validTasks = tasks.filter { t -> t.startTime.toLocalTime() >= startTime && t.endTime.toLocalTime() <= endTime }

    Layout(
        content = {
            tasks.sortedBy(TaskModel::startTime).forEach { task ->
                Box(modifier = Modifier.eventData(task)) {
                    Task(task)
                }
            }
        },
        modifier = modifier
            .drawBehind {
                repeat(endTime.hour - startTime.hour) {
                    drawLine(
                        dividerColor,
                        start = Offset(0f, (it + 1) * hourHeight.toPx()),
                        end = Offset(size.width, (it + 1) * hourHeight.toPx()),
                        strokeWidth = 1.dp.toPx()
                    )
                }
                repeat(numDays - 1) {
                    drawLine(
                        dividerColor,
                        start = Offset((it + 1) * dayWidth.toPx(), 0f),
                        end = Offset((it + 1) * dayWidth.toPx(), size.height),
                        strokeWidth = 1.dp.toPx()
                    )
                }
            }
    ) { measurables, constraints ->
        val height = hourHeight.roundToPx() * (endTime.hour - startTime.hour)
        val width = dayWidth.roundToPx() * numDays
        val placeablesWithTasks = measurables.map { measurable ->
            val task = measurable.parentData as TaskModel
            val taskDurationMinutes = ChronoUnit.MINUTES.between(task.startTime, task.endTime)
            val taskHeight = ((taskDurationMinutes / 60f) * hourHeight.toPx()).roundToInt()
            val placeable = measurable.measure(
                constraints.copy(
                    minWidth = dayWidth.roundToPx(),
                    maxWidth = dayWidth.roundToPx(),
                    minHeight = taskHeight,
                    maxHeight = taskHeight
                )
            )
            Pair(placeable, task)
        }
        layout(width, height) {
            placeablesWithTasks.forEach { (placeable, task) ->
                val eventOffsetMinutes =
                    ChronoUnit.MINUTES.between(startTime, task.startTime.toLocalTime())
                val eventY = ((eventOffsetMinutes / 60f) * hourHeight.toPx()).roundToInt()
                val eventX = task.day * dayWidth.roundToPx()
                placeable.place(eventX, eventY)
            }
        }
    }
}

private class EventDataModifier(
    val task: TaskModel,
) : ParentDataModifier {
    override fun Density.modifyParentData(parentData: Any?) = task
}

private fun Modifier.eventData(task: TaskModel) = this.then(EventDataModifier(task))