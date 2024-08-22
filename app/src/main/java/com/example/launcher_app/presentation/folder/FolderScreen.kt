package com.example.launcher_app.presentation.folder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.launcher_app.domain.model.Folder
import com.example.launcher_app.helper.Constant
import com.example.launcher_app.presentation.dialog.FolderCreationDialog
import com.example.launcher_app.presentation.folder.components.ItemFolder
import com.example.launcher_app.presentation.navgraph.Route

@Composable
fun FolderScreen(
    viewModel: FolderViewModel = hiltViewModel(),
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var isVisibleDialog by remember {
        mutableStateOf(false)
    }
    val listFolder by viewModel.listFolder.collectAsState()
    LaunchedEffect(true) {
        viewModel.getAllFolder()
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .statusBarsPadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
            Button(onClick = { isVisibleDialog = true }) {
                Text(text = "Add folder")
            }
        }
        FolderCreationDialog(
            isVisible = isVisibleDialog,
            onDismiss = { isVisibleDialog = false },
            onCreate = { folderName ->
                viewModel.insertFolder(folderName = folderName)
                isVisibleDialog = false
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(
                count = listFolder.size
            ) {
                listFolder[it].let {
                    folder ->
                    ItemFolder(folder = folder, onClick = {
                        navigateToDetail(
                            navController = navController,
                            folder = folder
                        )
                    })
                }
            }
        }
    }
}

private fun navigateToDetail(navController: NavController, folder: Folder) {
    navController.currentBackStackEntry?.savedStateHandle?.set(Constant.FOLDER, folder)
    navController.navigate(
        route = Route.FolderDetailScreen.route
    )
}