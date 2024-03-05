package com.truta.proteus_android.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun OptionCard(
    modifier: Modifier = Modifier,
    title: String,
    selectionTitle: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text =title,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.weight(1f))

            ElevatedButton(
                shape = RoundedCornerShape(12.dp),
                onClick = onClick,
                content = {
                    Text(
                        text = selectionTitle,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            )
        }
    }
}

@Preview
@Composable
fun OptionCardPreview() {
    OptionCard(
        title = "Day",
        selectionTitle = "Tuesday",
        onClick = {}
    )
}