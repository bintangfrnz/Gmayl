package com.bintangfajarianto.gmayl.core.validator

import com.bintangfajarianto.gmayl.base.ActionResult
import com.bintangfajarianto.gmayl.base.BaseValidator
import com.bintangfajarianto.gmayl.extension.isSymmetricKeyLengthValid

object SymmetricKeyValidator : BaseValidator<String, SymmetricKeyValidatorActionResult> {
    override fun validate(param: String): SymmetricKeyValidatorActionResult =
        when {
            param.isEmpty() -> SymmetricKeyValidatorActionResult.Blank
            (param.isSymmetricKeyLengthValid()) -> SymmetricKeyValidatorActionResult.Valid
            else -> SymmetricKeyValidatorActionResult.InvalidLength
        }
}

sealed class SymmetricKeyValidatorActionResult : ActionResult {
    object Valid : SymmetricKeyValidatorActionResult()
    object InvalidLength : SymmetricKeyValidatorActionResult()
    object Blank : SymmetricKeyValidatorActionResult()
}