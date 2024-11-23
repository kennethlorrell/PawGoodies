package com.deepdark.pawgoodies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.deepdark.pawgoodies.ui.theme.PawGoodiesTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val auth = FirebaseAuth.getInstance()
            val currentUser = remember { auth.currentUser }

            val navController = rememberNavController()
            val scrollState = rememberScrollState()

            PawGoodiesTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold { outerPadding ->
                        Box(
                            modifier = Modifier
                                .padding(outerPadding)
                        ) {
                            App(
                                navController = navController,
                                scrollState,
                                currentUser = currentUser
                            )
                        }
                    }
                }
            }
        }
    }
}
