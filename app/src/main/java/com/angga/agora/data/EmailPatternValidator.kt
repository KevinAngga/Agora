package com.angga.agora.data

import android.util.Patterns
import com.angga.agora.domain.PatternValidator
import javax.inject.Inject

class EmailPatternValidator @Inject constructor() : PatternValidator {
    override fun matches(value: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(value).matches()
    }
}