package com.example.musicappbasic

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.IconButton
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(){
    val scaffoldState : ScaffoldState = rememberScaffoldState()
    val scope: CoroutineScope = rememberCoroutineScope()
    val viewModel: MainActivityViewModel = viewModel()

    val controller : NavController = rememberNavController()
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val dialogOpen = remember{
        mutableStateOf(false)
    }
    val currentScreen = remember{
        viewModel.currentScreen.value
    }
    val title  = remember{
        mutableStateOf(currentScreen.title)

    }

    val bottomBar : @Composable () -> Unit = {
        if(currentScreen is Screen.DrawerScreen ||
            currentScreen == Screen.BottomScreen.Home){
            BottomNavigation(Modifier.wrapContentSize()){
                bottomBarList.forEach {
                    item ->
                    BottomNavigationItem(
                        selected = currentRoute == item.bRoute,
                        onClick = {
                            controller.navigate(
                                item.bRoute
                            )
                        },
                        icon = {
                            Icon(
                                contentDescription = item.bTitle,
                                painter = painterResource(id = item.icon)
                            )
                        },
                        label ={
                            Text(
                                text = item.bTitle
                            )
                        },
                        selectedContentColor = Color.White,
                        unselectedContentColor = Color.Black)
                }
            }
        }
    }
    Scaffold(
        bottomBar = bottomBar,
        scaffoldState = scaffoldState,
        drawerContent = {
            LazyColumn(Modifier.padding(16.dp)){
                items(screensList){
                        item ->
                    Drawer(selected = currentRoute == item.dRoute, item = item) {
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                        if(item.dRoute == "add_account"){
                          dialogOpen.value = true
                        }else{
                            controller.navigate(item.dRoute)
                            title.value = item.dTitle
                        }
                    }
                }
            }
        },
        topBar = {
            TopAppBar(
                title = {
                Text(title.value) },
                navigationIcon = {
                IconButton(onClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }) {
                        Icon(imageVector = Icons.Default.AccountCircle,
                            contentDescription = "menu")
                    }

            },)
        },
    ) {
        Navigation(navController = controller, viewModel = viewModel, pd = it)
        AccountDialog(dialogOpen = dialogOpen)
    }
}


@Composable
fun Drawer(
    selected: Boolean,
    item : Screen.DrawerScreen,
    onDrawerItemClicked : () -> Unit
){
    val backGround = if(selected) Color.DarkGray else Color.White
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 8.dp,
                vertical = 16.dp
            )
            .background(backGround)
            .clickable {
                onDrawerItemClicked()
            }
    ){
        Icon(
            painter = painterResource(id = item.icon),
            contentDescription = item.dTitle,
            Modifier.padding(end = 8.dp, top = 4.dp)
        )
        Text(
            text = item.dTitle,
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainViewPreview(){
    MainView()
}

@Composable
fun Navigation(navController: NavController, viewModel: MainActivityViewModel, pd: PaddingValues){

    NavHost(navController = navController as NavHostController,
        startDestination = Screen.DrawerScreen.Account.route, modifier = Modifier.padding(pd) ){

        composable(Screen.DrawerScreen.Account.route){
            AccountView()
        }
        composable(Screen.DrawerScreen.Subscription.route){
            Subscription()
        }
    }

}