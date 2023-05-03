package com.bintangfajarianto.gmayl.core.validator

import com.bintangfajarianto.gmayl.base.ActionResult
import com.bintangfajarianto.gmayl.base.BaseValidator
import com.bintangfajarianto.gmayl.extension.isPasswordLengthValid

object PasswordValidator : BaseValidator<String, PasswordValidatorActionResult> {
    override fun validate(param: String): PasswordValidatorActionResult =
        when {
            param.isEmpty() -> PasswordValidatorActionResult.Blank
            (param.isPasswordLengthValid()) -> PasswordValidatorActionResult.Valid
            else -> PasswordValidatorActionResult.InvalidLength
        }
}

sealed class PasswordValidatorActionResult : ActionResult {
    object Valid : PasswordValidatorActionResult()
    object InvalidLength : PasswordValidatorActionResult()
    object Blank : PasswordValidatorActionResult()
}
