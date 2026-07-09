package com.data.mapper

interface BaseMapper<From, To> {
    fun map(from: From): To
}