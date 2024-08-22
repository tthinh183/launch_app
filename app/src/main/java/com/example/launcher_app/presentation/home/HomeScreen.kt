package com.example.launcher_app.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.launcher_app.presentation.home.components.ItemApp
import com.example.launcher_app.presentation.navgraph.Route

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val listApp by viewModel.listApp.collectAsState()
    val context = LocalContext.current
    ConstraintLayout(
        modifier = modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(top = 50.dp)
            .padding(bottom = 50.dp),
    ) {
        val (header, body, appManager) = createRefs()
        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "HomeScreen", Modifier.constrainAs(header) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(body.top)
        })
        Spacer(modifier = Modifier.height(30.dp))
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .constrainAs(body) {
                    top.linkTo(header.bottom)
                    bottom.linkTo(appManager.top)
                }
        ) {
            items(
                count = listApp.size
            ) {
                listApp[it].let { taskInfo ->
                    ItemApp(taskInfo = taskInfo, modifier = Modifier.padding(bottom = 5.dp), context = context)
                }
            }
        }
        Column(
            modifier = Modifier.constrainAs(appManager) {
                bottom.linkTo(parent.bottom)
                top.linkTo(body.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { navController.navigate(Route.RecentScreen.route) }, modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {
                Text(text = "Recently Application")
            }
            Button(onClick = { navController.navigate(Route.FolderScreen.route) }, modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {
                Text(text = "Manage Application")
            }
        }
    }
}