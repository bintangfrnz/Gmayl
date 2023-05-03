package com.bintangfajarianto.gmayl.base

interface BaseValidator<ValidatorParam, T : ActionResult> {
    fun validate(param: ValidatorParam): T
}
