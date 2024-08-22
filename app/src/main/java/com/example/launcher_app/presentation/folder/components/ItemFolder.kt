package com.example.launcher_app.presentation.folder.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.launcher_app.domain.model.Folder

@Composable
fun ItemFolder(
    folder: Folder,
    onClick: (Folder) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp).clickable { onClick.invoke(folder) }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = folder.name, fontSize = 20.sp, color = Color.Black)
        }
    }
}