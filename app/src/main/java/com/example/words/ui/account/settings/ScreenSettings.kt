package com.example.words.ui.account.settings

import android.content.Context
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.words.R
import com.example.words.ui.account.categories.ViewModelCategories
import com.example.words.ui.navigation.AlertDialogExample
import com.example.words.ui.navigation.DialogWithEditField

@Composable
fun ScreenSettings(
    viewModelSettings: ScreenSettingsViewModel,
    navController: NavHostController,
    viewModelCategories: ViewModelCategories,
    context: Context
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.lightDark),
            fontSize = 24.sp
        )
        Column(
            modifier = Modifier
                .selectableGroup()
                .fillMaxWidth()
        ) {
            RowWithRadio(
                selected = viewModelSettings.selected.value,
                onClick = {
                    viewModelSettings.updateSelected(true)
                    viewModelSettings.updateColor()
                          },
                text = R.string.light
            )
            RowWithRadio(
                selected = !viewModelSettings.selected.value,
                onClick = {
                    viewModelSettings.updateSelected(false)
                    viewModelSettings.updateColor()
                          },
                text = R.string.Dark
            )
        }
        Text(
            text = stringResource(id = R.string.account),
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 8.dp, top = 15.dp)
        )
        RowWithButtons(
//            onClick1 = { viewModelSettings.openAlert(1) },
            onClick2 = {
                       viewModelSettings.openAlert(2)
            },
            onClick3 = {
                viewModelSettings.openAlert(3)
            }
        )
    }

//    if (viewModelSettings.alertDialogField.value) {
//        DialogWithEditField(
//            onDismissRequest = { viewModelSettings.openAlert(1) },
//            onConfirmation = {
//                viewModelSettings.updateUser(viewModelCategories.enter)
//            },
//            text = stringResource(id = R.string.nameChange),
//            viewModel = viewModelCategories
//        )
//    }
    if (viewModelSettings.alertDialogDelete.value) {
        AlertDialogExample(
            onDismissRequest = { viewModelSettings.openAlert(2) },
            onConfirmation = {
                viewModelSettings.deleteAccount(navController, context)
                viewModelSettings.openAlert(2)
                             },
            dialogTitle = "${stringResource(id = R.string.accountDelete)}?"
        )
    }
    if (viewModelSettings.alertDialogExit.value) {
        AlertDialogExample(
            onDismissRequest = { viewModelSettings.openAlert(3) },
            onConfirmation = {
                viewModelSettings.openAlert(3)
                viewModelSettings.exit(navController)
                             },
            dialogTitle = "${stringResource(id = R.string.accountExit)}?"
        )
    }
}

@Composable
fun RowWithButtons(
//    onClick1: () -> Unit,
    onClick2: () -> Unit,
    onClick3: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
//        Buttons(
//            onClick = { onClick1() },
//            text = R.string.accountChange
//        )
        Buttons(
            onClick = { onClick2() },
            text = R.string.accountDelete
        )
        Buttons(
            onClick = { onClick3() },
            text = R.string.accountExit
        )
    }
}

@Composable
fun Buttons(
    onClick: () -> Unit,
    text: Int
){
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .width(230.dp)
            .padding(8.dp)
    ) {
        Text(text = stringResource(id = text),
            fontSize = 18.sp)
    }
}

@Composable
fun RowWithRadio(
    selected: Boolean,
    onClick: () -> Unit,
    @StringRes text: Int
) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        RadioButton(
            selected = selected,
            onClick = { onClick() }
        )
        Text(
            text = stringResource(id = text),
            fontSize = 20.sp
        )
    }
}



//@Preview
//@Composable
//fun Pop() {
//    ScreenSettings(ScreenSettingsViewModel(), rememberNavController(), ViewModelCategories())
////    RowWithButtons()
//}