/*
 * Copyright 2024 Mifos Initiative
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * See https://github.com/openMF/mobile-wallet/blob/master/LICENSE.md
 */
package com.mifospay.core.model.domain.user

import com.mifospay.core.model.entity.Role
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val username: String,
    val userId: Long = 0,
    val base64EncodedAuthenticationKey: String,
    val authenticated: Boolean = false,
    val officeId: Int,
    val officeName: String,
    val roles: List<Role>,
    val permissions: List<String>,
    val clients: List<Long> = emptyList(),
    val shouldRenewPassword: Boolean,
    val isTwoFactorAuthenticationRequired: Boolean,
)
