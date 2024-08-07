package com.angga.agora.domain

interface PatternValidator {
    fun matches(value : String) : Boolean
}