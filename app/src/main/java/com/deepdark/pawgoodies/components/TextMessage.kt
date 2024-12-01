package com.deepdark.pawgoodies.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deepdark.pawgoodies.data.viewmodels.MessageState
import com.deepdark.pawgoodies.data.viewmodels.MessageType

@Composable
fun TextMessage(
    messageState: MessageState?,
    modifier: Modifier = Modifier
) {
    if (messageState != null && messageState.message.isNotEmpty()) {
        Text(
            text = messageState.message,
            color = if (messageState.type == MessageType.SUCCESS) MaterialTheme.colorScheme.secondary
                    else MaterialTheme.colorScheme.error,
            modifier = modifier.padding(top = 16.dp)
        )
    }
}