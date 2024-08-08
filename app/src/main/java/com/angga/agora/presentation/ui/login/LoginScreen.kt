package com.angga.agora.presentation.ui.login

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.angga.agora.R
import com.angga.agora.presentation.ui.components.AgoraActionButton
import com.angga.agora.presentation.ui.components.AgoraPasswordTextField
import com.angga.agora.presentation.ui.components.AgoraTextField
import com.angga.agora.presentation.ui.components.EmailIcon
import com.angga.agora.presentation.ui.theme.AgoraTheme
import com.angga.agora.presentation.ui.utils.ObserveAsEvents


@Composable
fun LoginScreenRoot(
    onLoginSuccess: () -> Unit,
    onSignUpClick: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {

    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    ObserveAsEvents(viewModel.events) { event ->
        when(event) {
            is LoginEvent.Error -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
            LoginEvent.LoginSuccess -> {
                onLoginSuccess()
            }
        }
    }

    LoginScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                is LoginAction.OnRegisterClick -> onSignUpClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}


@Composable
fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
            .padding(vertical = 32.dp)
            .padding(top = 16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.hi_there),
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = stringResource(id = R.string.agora_welcome_text),
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(48.dp))

        AgoraTextField(
            state = state.email,
            startIcon = EmailIcon,
            endIcon = null,
            hint = stringResource(id = R.string.example_email),
            title = stringResource(id = R.string.email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        AgoraPasswordTextField(
            state = state.password,
            isPasswordVisible = state.isPasswordVisible,
            onTogglePasswordVisibility = {
                onAction(LoginAction.OnTogglePasswordVisibilityClick)
            },
            hint = stringResource(id = R.string.password),
            title = stringResource(id = R.string.password),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        AgoraActionButton(
            text = stringResource(id = R.string.login),
            isLoading = state.isLoggingIn,
            enabled = state.canLogin && !state.isLoggingIn,
            onClick = {
                onAction(LoginAction.OnLoginClick)
            },
        )

        Spacer(modifier = Modifier.height(32.dp))

        val annotatedString = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) {
                append(stringResource(id = R.string.not_have_account) + " ")
                withLink(
                    LinkAnnotation.Url(
                        url = "clickable",
                        linkInteractionListener = {
                            onAction(LoginAction.OnRegisterClick)
                        },
                        styles = TextLinkStyles(
                            style = SpanStyle(
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.primary,
                            )
                        )
                    )
                ) {
                    append(stringResource(id = R.string.sign_up))
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.BottomCenter
        ) {
            BasicText(
                text = annotatedString
            )
        }
    }
}


@Preview
@Composable
private fun LoginScreenPreview() {
    AgoraTheme {
        LoginScreen(
            state = LoginState(),
            onAction = {}
        )
    }
}