package com.example.tasky.agenda.presentation.itemdetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.tasky.R
import com.example.tasky.agenda.domain.model.AgendaPhoto
import com.example.tasky.ui.theme.Black
import com.example.tasky.ui.theme.Gray
import com.example.tasky.ui.theme.LightGray2

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PhotosListContainer(
    canAddPhoto: Boolean,
    photos: List<AgendaPhoto>,
    onAddClick: () -> Unit,
    onPhotoClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = LightGray2)
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(R.string.photos),
                color = Black,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                photos.forEach { photo ->
                    AsyncImage(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .size(60.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(2.dp, Gray, RoundedCornerShape(8.dp))
                            .clickable { onPhotoClick(photo.url) },
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(photo.url)
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(R.drawable.placeholder),
                        contentDescription = "image",
                        contentScale = ContentScale.Crop,
                    )
                }
                if (canAddPhoto) {
                    Box(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .size(60.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(2.dp, Gray, RoundedCornerShape(8.dp))
                            .clickable { onAddClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(35.dp),
                            imageVector = Icons.Rounded.Add,
                            tint = Gray,
                            contentDescription = stringResource(id = R.string.add)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Preview
@Composable
fun PhotosListContainerPreview() {
    PhotosListContainer(
        canAddPhoto = true,
        photos = listOf(),
        onAddClick = {},
        onPhotoClick = {}
    )
}