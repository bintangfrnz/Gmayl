package com.bintangfajarianto.gmayl.data.repository.home

class HomeRepositoryImpl(
    private val storageRepository: HomeStorageRepository,
) : HomeRepository, HomeStorageRepository by storageRepository
