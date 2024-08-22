package com.example.launcher_app.presentation.recent

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.launcher_app.presentation.home.components.ItemApp

@Composable
fun RecentScreen(
    viewModel: RecentViewModel = hiltViewModel(),
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val recentAppList by viewModel.recentAppList.collectAsState()
    val context = LocalContext.current
    val activity = context as Activity

    val hasPermission = remember { mutableStateOf(viewModel.hasUsageStatsPermission(context = context)) }

    LaunchedEffect(Unit) {
        hasPermission.value = viewModel.hasUsageStatsPermission(context = context)
        if (!hasPermission.value) {
            viewModel.requestUsageStatsPermission(activity = activity)
        } else {
            viewModel.getRecentApp()
        }
    }
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        ) {
            val (btnBack, title) = createRefs()
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.constrainAs(btnBack) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
            Text(
                text = "Recently Application",
                modifier = Modifier.constrainAs(title) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                })
        }
        Spacer(modifier = Modifier.height(30.dp))
        LazyColumn(
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            items(
                count = recentAppList.size
            ) {
                recentAppList[it].let { taskInfo ->
                    ItemApp(taskInfo = taskInfo, context = context)
                }
            }
        }
    }
}