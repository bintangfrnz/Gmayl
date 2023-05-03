package com.bintangfajarianto.gmayl.core.validator

import com.bintangfajarianto.gmayl.base.ActionResult
import com.bintangfajarianto.gmayl.base.BaseValidator
import com.bintangfajarianto.gmayl.extension.isEmailFormatValid

object EmailValidator : BaseValidator<String, EmailValidatorActionResult> {
    override fun validate(param: String): EmailValidatorActionResult =
        when {
            param.isEmpty() -> EmailValidatorActionResult.Blank
            param.isEmailFormatValid() -> EmailValidatorActionResult.Valid
            else -> EmailValidatorActionResult.InvalidFormat
        }
}

sealed class EmailValidatorActionResult : ActionResult {
    object Valid : EmailValidatorActionResult()
    object InvalidFormat : EmailValidatorActionResult()
    object Blank : EmailValidatorActionResult()
}
