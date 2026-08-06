package com.ui.components.label

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.designsystem.Spacing
import com.designsystem.theme.Neutral02DarkestGrey
import com.designsystem.theme.Neutral06LightGrey
import com.designsystem.theme.Typography

@Composable
fun InfoLabel(
    label: String
) {

    Card(
        shape = RoundedCornerShape(Spacing.spacing_20),
        colors = CardDefaults.cardColors(
            containerColor = Neutral02DarkestGrey
        )
    ) {
        Text(
            text = label,
            style = Typography.labelLarge,
            color = Neutral06LightGrey,
            modifier = Modifier.padding(
                horizontal = Spacing.spacing_12,
                vertical = Spacing.spacing_4
            )
        )
    }
}