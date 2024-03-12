package com.truta.proteus_android.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DoubleOptionCard(
    modifier: Modifier = Modifier,
    title: String,
    selectionTitleLeft: String,
    selectionTitleRight: String,
    onClickLeft: () -> Unit,
    onClickRight: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .wrapContentHeight(Alignment.CenterVertically),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.weight(1f))

            ElevatedButton(
                shape = RoundedCornerShape(12.dp),
                onClick = onClickLeft,
                content = {
                    Text(
                        text = selectionTitleLeft,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            )

            Icon(
                modifier = Modifier.padding(start = 6.dp, end = 6.dp),
                imageVector = Icons.Rounded.ArrowForward,
                contentDescription = "Arrow Forward"
            )

            ElevatedButton(
                shape = RoundedCornerShape(12.dp),
                onClick = onClickRight,
                content = {
                    Text(
                        text = selectionTitleRight,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            )
        }
    }
}

@Preview
@Composable
fun DoubleOptionCardPreview() {
    DoubleOptionCard(
        title = "Time",
        selectionTitleLeft = "10:00 AM",
        selectionTitleRight = "11:00 AM",
        onClickLeft = {},
        onClickRight = {}
    )
}