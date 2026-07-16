package com.common.mapper

interface BaseMapper<From, To> {
    fun map(from: From): To
}