package com.angga.agora.presentation.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angga.agora.domain.account.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {
    fun onAction(action : AccountScreenAction) {
        when(action) {
            AccountScreenAction.OnLogoutClick -> {
                viewModelScope.launch {
                    accountRepository.logout()
                }
            }
        }
    }
}