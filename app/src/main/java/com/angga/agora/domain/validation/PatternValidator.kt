package com.angga.agora.domain.validation

interface PatternValidator {
    fun matches(value : String) : Boolean
}