package com.bintangfajarianto.gmayl.data.repository.home

class HomeRepositoryImpl(
    storageRepository: HomeStorageRepository,
) : HomeRepository, HomeStorageRepository by storageRepository
