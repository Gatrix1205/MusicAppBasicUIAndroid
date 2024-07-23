package com.example.musicappbasic
import android.annotation.SuppressLint
import androidx.annotation.DrawableRes

sealed class Screen(
    val title: String,
    val route: String,
) {
    sealed class BottomScreen(
        val bTitle: String,
        val bRoute : String,
        @DrawableRes val icon : Int
    ) : Screen(bTitle, bRoute){
        @SuppressLint("PrivateResource")
        object Home: BottomScreen(
            "Home","home", R.drawable.ic_add
        )
        object Library: BottomScreen(
            "Library","library", R.drawable.ic_subscription
        )

        object Browse : BottomScreen(
            "Browse", "browse", R.drawable.ic_music
        )
    }

    sealed class DrawerScreen(
        val  dTitle: String,
        val  dRoute: String,
        @DrawableRes val icon : Int
    ): Screen(dTitle, dRoute){
        object Account : DrawerScreen(
            "Account",
            "account",
            R.drawable.ic_account
        )

        object Subscription : DrawerScreen(
            "Subscription",
            "subscription",
            R.drawable.ic_subscription
        )


        object AddAccount : DrawerScreen(
            "Add Account",
            "add_account",
            R.drawable.ic_add
        )
    }
}

val bottomBarList = listOf(
    Screen.BottomScreen.Home,
    Screen.BottomScreen.Browse,
    Screen.BottomScreen.Library
)

val screensList =  listOf(
    Screen.DrawerScreen.Account,
    Screen.DrawerScreen.Subscription,
    Screen.DrawerScreen.AddAccount,
)

