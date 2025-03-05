package week.on.a.plate.dialogs.forCreateRecipeScreen.chooseHowImagePick.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.buttons.CommonButton
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.dialogs.forCreateRecipeScreen.chooseHowImagePick.event.BaseContextProvider
import week.on.a.plate.dialogs.forCreateRecipeScreen.chooseHowImagePick.event.ChooseHowImagePickEvent

@Composable
fun ChooseHowImagePickContent(onEvent: (ChooseHowImagePickEvent) -> Unit) {
    val context = LocalContext.current
    val contextProvider = remember { BaseContextProvider(context) }
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(24.dp)
    ) {
        DoneButton(stringResource(R.string.provide_link_to_image),  click = {
            onEvent(
                ChooseHowImagePickEvent.ByUrl
            )
        })
        Spacer(Modifier.height(12.dp))
        CommonButton (stringResource(R.string.make_photo)) {
            onEvent(ChooseHowImagePickEvent.MakePhoto(contextProvider))
        }
        Spacer(Modifier.height(12.dp))
        CommonButton (stringResource(R.string.select_from_gallery)) {
            onEvent(ChooseHowImagePickEvent.FromGallery)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewExitApply() {
    WeekOnAPlateTheme {
        ChooseHowImagePickContent {}
    }
}