package com.example.launcher_app.presentation.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun FolderCreationDialog(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onCreate: (String) -> Unit
) {
    if (isVisible) {
        var folderName by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { onDismiss },
            confirmButton = {
                Button(onClick = {
                    onCreate(
                        folderName
                    )
                    onDismiss
                }) {
                    Text(text = "Create")
                }
            },
            title = { Text(text = "Create new folder") },
            text = {
                TextField(
                    value = folderName,
                    onValueChange = { folderName = it },
                    label = { Text(text = "Folder Name") })
            },
            dismissButton = {
                Button(
                    onClick = onDismiss
                ) {
                    Text(text = "Cancel")
                }
            }
        )
    }
}