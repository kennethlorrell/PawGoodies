package com.deepdark.pawgoodies.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deepdark.pawgoodies.R

@Composable
fun TopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 18.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.logo),
                contentDescription = "PawGoodies Logo",
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 8.dp),
                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.Black)
            )
            Text(
                text = "PawGoodies",
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}
