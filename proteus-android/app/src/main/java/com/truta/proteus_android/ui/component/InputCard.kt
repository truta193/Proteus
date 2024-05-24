package com.truta.proteus_android.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.truta.proteus_android.ui.screen.new_task.NewTaskViewModel

//TODO: Change caret color to match theme
@Composable
fun InputCard(
    modifier: Modifier = Modifier,
    viewModel: NewTaskViewModel = hiltViewModel()
) {

    val task = viewModel.task.collectAsState()

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
            verticalAlignment = Alignment.CenterVertically
        ) {
//            TextField(
//                value = title.value,
//                modifier = Modifier.padding(0.dp),
//                colors = TextFieldDefaults.colors(
//                    focusedIndicatorColor = Color.Transparent,
//                    unfocusedIndicatorColor = Color.Transparent,
//                    disabledIndicatorColor = Color.Transparent
//                ),
//                textStyle = MaterialTheme.typography.titleMedium,
//                placeholder = {
//                    Text(
//                        text = "Title",
//                        modifier = Modifier.padding(0.dp),
//                        style = MaterialTheme.typography.titleMedium
//                    )
//                },
//                onValueChange = {viewModel.updateTitle(it)},
//            )
            BasicTextField(
                value = task.value.title,
                modifier = Modifier.padding(0.dp),
                onValueChange = { viewModel.updateTitle(it) },
                textStyle = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onSurface),
                decorationBox = { innerTextField ->
                    Row(modifier = Modifier.fillMaxWidth()) {
                        if (task.value.title.isEmpty()) {
                            Text(
                                text = "Title",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                    innerTextField()
                }
            )
        }
    }
}

@Preview
@Composable
fun InputCardPreview() {
    InputCard()
}