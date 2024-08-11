package com.angga.agora.presentation.ui.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.angga.agora.R
import com.angga.agora.presentation.ui.components.AgoraOutlinedActionButton
import com.angga.agora.presentation.ui.theme.AgoraTheme

@Composable
fun AccountScreenRoot(
    viewModel: AccountViewModel = hiltViewModel(),
    onLogoutClick: () -> Unit,
) {
    AccountScreen(
        onAction = { action ->
            when(action) {
                AccountScreenAction.OnLogoutClick -> onLogoutClick()
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun AccountScreen(
    onAction : (AccountScreenAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AgoraOutlinedActionButton(
           text = stringResource(id = R.string.logout),
           isLoading = false,
           onClick = {
               onAction(AccountScreenAction.OnLogoutClick)
           }
       )
    }
}

@Preview
@Composable
private fun AccountScreenPreview() {
   AgoraTheme {
       AccountScreen(
           onAction = {}
       )
   }
}