package com.ukdev.carcadasalborghetti.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.ukdev.carcadasalborghetti.R

@Composable
fun PaidAppPromotionScreen(
    onGetPaidVersionClicked: () -> Unit
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(color = colorResource(R.color.white)),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = stringResource(R.string.get_paid_version).uppercase(),
                color = colorResource(R.color.red),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = stringResource(R.string.get_paid_version_rationale),
                fontSize = 14.sp
            )
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = onGetPaidVersionClicked,
                colors = ButtonColors(
                    containerColor = colorResource(R.color.red),
                    contentColor = colorResource(R.color.white),
                    disabledContainerColor = colorResource(R.color.red),
                    disabledContentColor = colorResource(R.color.white)
                ),
                shape = RoundedCornerShape(dimensionResource(R.dimen.radius_button))
            ) {
                Text(stringResource(R.string.purchase).uppercase())
            }
        }
    }
}

@Preview
@Composable
fun PreviewPaidAppPromotionScreen() {
    PaidAppPromotionScreen(onGetPaidVersionClicked = {})
}
