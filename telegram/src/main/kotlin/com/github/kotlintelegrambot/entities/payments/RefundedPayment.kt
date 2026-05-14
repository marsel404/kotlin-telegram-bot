package com.github.kotlintelegrambot.entities.payments

import com.google.gson.annotations.SerializedName as Name

data class RefundedPayment(
    val currency: String,
    @Name("total_amount") val totalAmount: Int,
    @Name("invoice_payload") val invoicePayload: String,
    @Name("telegram_payment_charge_id") val telegramPaymentChargeId: String,
    @Name("provider_payment_charge_id") val providerPaymentChargeId: String,
)
