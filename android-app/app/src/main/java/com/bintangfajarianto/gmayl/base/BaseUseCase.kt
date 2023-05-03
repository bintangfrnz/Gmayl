package com.bintangfajarianto.gmayl.base

interface BaseUseCase<ReqParams : Any, AR : ActionResult> {
    suspend operator fun invoke(param: ReqParams): AR
}
