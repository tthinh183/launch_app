package com.example.launcher_app.presentation.folder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.launcher_app.domain.model.Folder
import com.example.launcher_app.domain.model.TaskInfo
import com.example.launcher_app.presentation.dialog.AppSelectedDialog
import com.example.launcher_app.presentation.home.components.ItemApp
import okhttp3.internal.concurrent.Task

@Composable
fun FolderDetail(
    folder: Folder,
    navController: NavController,
    viewModel: FolderDetailViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var isVisibleDialog by remember {
        mutableStateOf(false)
    }
    val listApp by viewModel.listApp.collectAsState()
    val listAppInFolder by viewModel.appsOfFolder.collectAsState()
    LaunchedEffect(true) {
        viewModel.getAllApp()
        viewModel.getListAppOfFolder(folder.name)
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppSelectedDialog(
            isVisible = isVisibleDialog,
            onDismiss = { isVisibleDialog = false },
            onAppSelected = { listAppSelected ->
                viewModel.updateFolder(folder.copy(apps = listAppSelected))
                isVisibleDialog = false
            },
            context = context,
            existingAppInfo = listAppInFolder,
            listApp = listApp
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = folder.name)
            }
            Button(onClick = { isVisibleDialog = true }) {
                Text(text = "Declare list application")
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            items(
                listAppInFolder.size
            ) {
                listAppInFolder[it].let {
                    taskInfo ->
                    ItemApp(taskInfo = taskInfo, context = context)
                }
            }
        }
    }
}