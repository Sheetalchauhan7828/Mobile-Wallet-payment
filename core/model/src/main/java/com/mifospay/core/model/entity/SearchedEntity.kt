/*
 * Copyright 2024 Mifos Initiative
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * See https://github.com/openMF/mobile-wallet/blob/master/LICENSE.md
 */
package com.mifospay.core.model.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchedEntity(
    var entityId: Int = 0,
    var entityAccountNo: String = " ",
    var entityName: String = " ",
    var entityType: String = " ",
    var parentId: Int = 0,
    var parentName: String = " ",
) : Parcelable
