package com.example.tasky.agenda.presentation.itemdetail.components

import android.Manifest
import android.net.Uri
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tasky.BuildConfig
import com.example.tasky.agenda.domain.model.AgendaPhoto
import com.example.tasky.ui.theme.Light
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun DetailPhotosContainer(
    photos: List<AgendaPhoto>,
    canAddPhoto: Boolean,
    onPhotoSelected: (String) -> Unit,
    onPhotoClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            if (it != null) {
                onPhotoSelected(it.toString())
            }
        }

    val readGalleryPermissionState =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            rememberPermissionState(Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            rememberPermissionState(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

    if (photos.isNotEmpty()) {
        PhotosListContainer(
            canAddPhoto = canAddPhoto,
            photos = photos,
            onAddClick = {
                launchImageGallery(readGalleryPermissionState, galleryLauncher)
            },
            onPhotoClick = onPhotoClick,
            modifier = modifier
        )
    } else {
        AddPhotosContainer(modifier = modifier) {
            launchImageGallery(readGalleryPermissionState, galleryLauncher)
        }
    }

    Spacer(modifier = Modifier.size(25.dp))

    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        color = Light
    )
}

@OptIn(ExperimentalPermissionsApi::class)
private fun launchImageGallery(
    permissionState: PermissionState,
    galleryLauncher: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>
) {
    if (permissionState.status == PermissionStatus.Granted || BuildConfig.DEBUG) {
        val mediaRequest =
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        galleryLauncher.launch(mediaRequest)
    } else {
        permissionState.launchPermissionRequest()
    }
}

@Preview
@Composable
fun DetailPhotosContainerPreview() {
    DetailPhotosContainer(
        photos = listOf(),
        canAddPhoto = true,
        onPhotoSelected = {},
        onPhotoClick = {}
    )
}