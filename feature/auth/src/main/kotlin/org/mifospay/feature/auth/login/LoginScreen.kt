/*
 * Copyright 2024 Mifos Initiative
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * See https://github.com/openMF/mobile-wallet/blob/master/LICENSE.md
 */
package org.mifospay.feature.auth.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.mifospay.core.designsystem.component.MfOverlayLoadingWheel
import org.mifospay.core.designsystem.component.MifosButton
import org.mifospay.core.designsystem.component.MifosOutlinedTextField
import org.mifospay.core.designsystem.icon.MifosIcons
import org.mifospay.core.designsystem.theme.MifosTheme
import org.mifospay.core.designsystem.theme.grey
import org.mifospay.core.designsystem.theme.styleNormal18sp
import org.mifospay.feature.auth.R
import org.mifospay.feature.auth.socialSignup.SocialSignupMethodContentScreen

@Composable
internal fun LoginScreen(
    navigateToPasscodeScreen: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    navigateToSignupScreen: () -> Unit,
) {
    val context = LocalContext.current
    val showProgress by viewModel.showProgress.collectAsStateWithLifecycle()
    val isLoginSuccess by viewModel.isLoginSuccess.collectAsStateWithLifecycle()

    LoginScreenContent(
        modifier = modifier,
        showProgress = showProgress,
        login = { username, password ->
            viewModel.loginUser(
                username = username,
                password = password,
                onLoginFailed = { message ->
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                },
            )
        },
        navigateToSignupScreen = navigateToSignupScreen,
    )

    if (isLoginSuccess) {
        navigateToPasscodeScreen()
    }
}

@Composable
@Suppress("LongMethod")
private fun LoginScreenContent(
    showProgress: Boolean,
    login: (username: String, password: String) -> Unit,
    modifier: Modifier = Modifier,
    navigateToSignupScreen: () -> Unit,
) {
    var showSignUpScreen by rememberSaveable { mutableStateOf(false) }

    var userName by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue(""),
        )
    }
    var password by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue(""),
        )
    }
    var passwordVisibility: Boolean by remember { mutableStateOf(false) }

    if (showSignUpScreen) {
        SocialSignupMethodContentScreen(
            navigateToSignupScreen = navigateToSignupScreen,
        ) {
            showSignUpScreen = false
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 100.dp, start = 48.dp, end = 48.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = stringResource(id = R.string.feature_auth_login),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
            )
            Text(
                modifier = Modifier
                    .padding(top = 32.dp),
                text = stringResource(id = R.string.feature_auth_welcome_back),
                style = styleNormal18sp.copy(color = grey),
            )
            Spacer(modifier = Modifier.padding(top = 32.dp))
            MifosOutlinedTextField(
                label = R.string.feature_auth_username,
                value = userName,
                onValueChange = {
                    userName = it
                },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.padding(top = 16.dp))
            MifosOutlinedTextField(
                label = R.string.feature_auth_password,
                value = password,
                onValueChange = {
                    password = it
                },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (passwordVisibility) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                trailingIcon = {
                    val image = if (passwordVisibility) {
                        MifosIcons.Visibility
                    } else {
                        MifosIcons.VisibilityOff
                    }
                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        Icon(imageVector = image, null)
                    }
                },
            )
            MifosButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                enabled = userName.text.isNotEmpty() && password.text.isNotEmpty(),
                onClick = {
                    login.invoke(userName.text, password.text)
                },
                contentPadding = PaddingValues(12.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.feature_auth_login).uppercase(),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
            // Hide reset password for now
            /*Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                text = "Forgot Password",
                textAlign = TextAlign.Center,
                style = styleMedium16sp.copy(
                    textDecoration = TextDecoration.Underline,
                )
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                text = "OR",
                textAlign = TextAlign.Center,
                style = styleMedium16sp.copy(color = grey)
            )*/
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "Don’t have an account yet? ",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    modifier = Modifier.clickable {
                        showSignUpScreen = true
                    },
                    text = stringResource(id = R.string.feature_auth_sign_up),
                    style = MaterialTheme.typography.titleMedium.copy(
                        textDecoration = TextDecoration.Underline,
                    ),
                )
            }
        }

        if (showProgress) {
            MfOverlayLoadingWheel(
                contentDesc = stringResource(id = R.string.feature_auth_logging_in),
            )
        }
    }
}

@Preview(showSystemUi = true, device = "id:pixel_5")
@Composable
private fun LoanScreenPreview() {
    MifosTheme {
        LoginScreenContent(
            showProgress = false,
            login = { _, _ -> },
            navigateToSignupScreen = {},
        )
    }
}