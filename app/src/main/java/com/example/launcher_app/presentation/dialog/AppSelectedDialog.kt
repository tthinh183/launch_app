package com.example.launcher_app.presentation.dialog

import android.content.Context
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.launcher_app.domain.model.TaskInfo

@Composable
fun AppSelectedDialog(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onAppSelected: (List<TaskInfo>) -> Unit,
    listApp: List<TaskInfo>,
    context: Context,
    existingAppInfo: List<TaskInfo>
) {
    if (isVisible) {
        val selectedApps = remember {
            mutableStateOf(existingAppInfo.toHashSet())
        }
        Log.d("Existing App Info", "${existingAppInfo.map { it.appInfo?.packageName }}")
        Log.d("Selected Apps", "${selectedApps.value.map { it.appInfo?.packageName }}")
        AlertDialog(
            onDismissRequest = { onDismiss },
            confirmButton = {
                TextButton(
                    onClick = {
                        onAppSelected(selectedApps.value.toList())
                        onDismiss
                    }
                ) {
                    Text(text = "Add")
                }
            },
            dismissButton = { onDismiss },
            text = {
                LazyColumn {
                    items (
                        listApp
                    ) {
                        taskInfo ->
                        val isSelected = selectedApps.value.contains(taskInfo)
                        ListItem(
                            modifier = Modifier.clickable {
                                val newSelection = selectedApps.value.toHashSet()
                                if (isSelected) {
                                    newSelection.remove(taskInfo)
                                } else {
                                    newSelection.add(taskInfo)
                                }
                                selectedApps.value = newSelection
                            },
                            headlineContent = { Text(taskInfo.getLabel(context.packageManager)) },
                            trailingContent = {
                                Text(if (isSelected) "Selected" else "Not Selected")
                            }
                        )
                    }
                }
            }
            )
    }
}