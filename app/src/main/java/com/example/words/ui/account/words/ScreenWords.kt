package com.example.words.ui.account.words

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.words.R
import com.example.words.ui.views.ListWithWords

@Composable
fun ScreenWords(
    modifier: Modifier = Modifier,
    viewModel: ViewModelWords = viewModel(),
    categoryId: Int,
    categoryName: String
) {

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = null,
                    modifier = modifier
                        .size(35.dp)
                )
            }
            Spacer(modifier = modifier.width(90.dp))
            Text(
                text = categoryName,
                fontSize = 26.sp,
                modifier = modifier
                    .padding(vertical = 20.dp)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp)
                .padding(10.dp)
        ) {
            ListWithWords(
                wordsList = viewModel.wordsListResponse,
                viewModel = viewModel
            )
            viewModel.getWordsOfCategory(categoryId)
        }
        Button(
            onClick = {
                      viewModel.addWord(categoryId)
            },
        ) {
            Row {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null
                )
                Text(
                    text = stringResource(id = R.string.add_categories),
                    fontSize = 16.sp
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    ScreenWords()
//}