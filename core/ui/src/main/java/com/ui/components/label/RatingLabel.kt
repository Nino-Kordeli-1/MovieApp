package com.ui.components.label

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.designsystem.Spacing
import com.designsystem.theme.Neutral02DarkestGrey
import com.designsystem.theme.Typography
import com.designsystem.theme.YellowPrimary

@SuppressLint("DefaultLocale")
@Composable
fun RatingLabel(
    rating: Double
) {

    Card(
        shape = RoundedCornerShape(Spacing.spacing_20),
        colors = CardDefaults.cardColors(
            containerColor = Neutral02DarkestGrey
        )
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = Spacing.spacing_12,
                vertical = Spacing.spacing_4
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painterResource(
                    com.movieapp.designsystem.R.drawable.ic_star
                ),
                contentDescription = null,
                tint = YellowPrimary,
                modifier = Modifier.size(Spacing.spacing_12)
            )

            Spacer(
                modifier = Modifier.width(
                    Spacing.spacing_4
                )
            )

            Text(
                text = String.format("%.1f", rating),
                style = Typography.labelLarge,
                color = YellowPrimary,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}