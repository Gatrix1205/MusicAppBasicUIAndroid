package com.example.musicappbasic

import androidx.annotation.DrawableRes

data class Lib(@DrawableRes val icon: Int, val name:String)

val libraries = listOf(
    Lib(R.drawable.ic_music, "Playlist"),
    Lib(R.drawable.ic_subscription,"Artists"),
    Lib(R.drawable.ic_launcher_foreground,"Album"), Lib(
        R.drawable.ic_account,"Songs"
    ),Lib(androidx.core.R.drawable.ic_call_answer_low,"Genre")
)