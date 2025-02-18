/*
 * Copyright 2024 Mifos Initiative
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * See https://github.com/openMF/mobile-wallet/blob/master/LICENSE.md
 */
package org.mifospay.core.data.fineract.entity.mapper

import com.mifospay.core.model.entity.accounts.savings.Currency
import javax.inject.Inject
import com.mifospay.core.model.domain.Currency as DomainCurrency

class CurrencyMapper @Inject internal constructor() {
    fun transform(savingsCurrency: Currency): DomainCurrency {
        val currency: DomainCurrency =
            DomainCurrency()
        currency.code = savingsCurrency.code
        currency.displayLabel = savingsCurrency.displayLabel
        currency.displaySymbol = savingsCurrency.displaySymbol
        return currency
    }
}
