package com.data.di

import com.domain.di.module.domainModule
import com.network.di.networkModule

val coreModules = listOf(
    networkModule,
    apiModule,
    mapperModule,
    dataSourceModule,
    repositoryModule,
    domainModule
)