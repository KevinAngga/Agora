package com.angga.agora.presentation.ui.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.angga.agora.domain.UserDataValidator
import com.angga.agora.presentation.ui.components.AgoraActionButton
import com.angga.agora.presentation.ui.components.AgoraPasswordTextField
import com.angga.agora.presentation.ui.components.AgoraTextField
import com.angga.agora.presentation.ui.components.CheckIcon
import com.angga.agora.presentation.ui.components.CrossIcon
import com.angga.agora.presentation.ui.components.EmailIcon
import com.angga.agora.presentation.ui.theme.AgoraTheme

@Composable
fun RegisterScreenRoot(
    onSignInClick: () -> Unit,
    onSuccessfulRegistration: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    RegisterScreen(
        state = viewModel.state,
        onAction = {}
    )
}

@Composable
private fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit,
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
            text = stringResource(id = R.string.create_account),
            style = MaterialTheme.typography.headlineMedium
        )

        val annotatedString = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) {
                append(stringResource(id = R.string.already_have_an_account) + " ")
                withLink(
                    LinkAnnotation.Url(
                        url = "clickable",
                        linkInteractionListener = {

                        },
                        styles = TextLinkStyles(
                            style = SpanStyle(
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.primary,
                            )
                        )
                    )
                ) {
                    append(stringResource(id = R.string.login))
                }
            }
        }

        BasicText(
            text = annotatedString
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
                onAction(RegisterAction.OnTogglePasswordVisibilityClick)
            },
            hint = stringResource(id = R.string.password),
            title = stringResource(id = R.string.password),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordRequirement(
            text = stringResource(
                id = R.string.at_least_x_character,
                UserDataValidator.MIN_PASSWORD_LENGTH
            ),
            isValid = state.passwordValidationState.hasMinLength
        )

        Spacer(modifier = Modifier.height(4.dp))

        PasswordRequirement(
            text = stringResource(
                id = R.string.at_least_one_number
            ),
            isValid = state.passwordValidationState.hasNumber
        )

        Spacer(modifier = Modifier.height(4.dp))


        PasswordRequirement(
            text = stringResource(
                id = R.string.contain_lowercase_char
            ),
            isValid = state.passwordValidationState.hasLowerCaseCharacter
        )

        Spacer(modifier = Modifier.height(4.dp))

        PasswordRequirement(
            text = stringResource(
                id = R.string.contain_uppercase_char
            ),
            isValid = state.passwordValidationState.hasUpperCaseCharacter
        )

        Spacer(modifier = Modifier.height(32.dp))

        AgoraActionButton(
            text = stringResource(id = R.string.register),
            isLoading = state.isRegistering,
            enabled = state.canRegister,
            onClick = {
                onAction(RegisterAction.OnRegisterClick)
            },
        )

    }
}

@Composable
fun PasswordRequirement(
    text: String,
    isValid: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (isValid) CheckIcon else CrossIcon,
            contentDescription = null,
            tint = if (isValid) Color.Green else Color.Red
        )

        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp)
    }
}

@Preview
@Composable
private fun RegisterScreenPreview() {
    AgoraTheme {
        RegisterScreen(
            state = RegisterState(),
            onAction = {}
        )
    }
}