package com.deepdark.pawgoodies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.room.Room
import com.deepdark.pawgoodies.data.AppDatabase
import com.deepdark.pawgoodies.data.seeds.seedDatabase
import com.deepdark.pawgoodies.ui.theme.PawGoodiesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "pawgoodies_database"
        ).fallbackToDestructiveMigration().build()

        seedDatabase(db)

        setContent {
            PawGoodiesTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize()
                    ) { outerPaddings ->
                        Column(
                            modifier = Modifier.padding(outerPaddings)
                        ) {
                            App()
                        }
                    }
                }
            }
        }
    }
}
