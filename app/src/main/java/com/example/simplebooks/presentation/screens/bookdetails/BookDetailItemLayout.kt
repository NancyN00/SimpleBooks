package com.example.simplebooks.presentation.screens.bookdetails

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.simplebooks.domain.models.BookDetailsItem

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailItemLayout(
    bookDetItem: BookDetailsItem,
    navController: NavController,
    onFavoriteClick: () -> Unit,
    bookDetailViewModel: BookDetailViewModel = hiltViewModel(),
    isFavorite: Boolean
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Book Details")
                    }

                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->

        val context = LocalContext.current

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = bookDetItem.author,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = bookDetItem.name,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(4.dp))

                /** Book Details Container **/
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            shape = RoundedCornerShape(4.dp)
                        )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        /** Book Type **/
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Type")
                            Text(text = bookDetItem.type)
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        /** Book Price **/
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Price")
                            Text(text = "\$${bookDetItem.price}")
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        IconButton(onClick = {

                            Log.d(
                                "BookDetailsScreen",
                                "Toggle favorite clicked for book: ${bookDetItem.name}, isFavorite: $isFavorite"
                            )

                            bookDetailViewModel.toggleFavorite(bookDetItem, context)
                        }) {
                            val favorite = bookDetItem.isFavorite

                            /**Icon color not working to show diff between in fav list or not... fix soon. **/
                            val tintColor = if (favorite) Color.Red else Color.Gray
                            Icon(
                                imageVector = if (favorite)
                                    Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = if (favorite) "Remove from Favorites" else "Add to Favorites",
                                tint = tintColor
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(5.dp))

                /** Current Stock **/
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Log.d("API Response", "Current Stock: ${bookDetItem.currentStock}")
                    Text(text = "Current Stock")
                    Text(text = "${bookDetItem.currentStock}")
                }

                Spacer(modifier = Modifier.height(4.dp))

                /** Stock Status Chip **/
                AssistChip(
                    onClick = {},
                    label = {
                        Text(
                            text = if (bookDetItem.available) "In Stock" else "Out of Stock"
                        )
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = if (bookDetItem.available) Color.Green else Color.Red,
                        labelColor = Color.White
                    )
                )
            }
        }
    }
}
