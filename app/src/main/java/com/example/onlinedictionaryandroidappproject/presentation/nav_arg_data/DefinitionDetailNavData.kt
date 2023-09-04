package com.example.onlinedictionaryandroidappproject.presentation.nav_arg_data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DefinitionDetailNavData(val wordID: Int, val meaningID: Int, val definitionID: Int) : Parcelable