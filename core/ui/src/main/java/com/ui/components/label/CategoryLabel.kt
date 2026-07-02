package com.ui.components.label

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.designsystem.Spacing
import com.designsystem.theme.Typography
import com.designsystem.theme.YellowPrimary

@Composable
fun CategoryLabel(
    title: String,
    modifier: Modifier
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(
            style = Typography.labelSmall,
            text = title,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = Spacing.spacing_10, end = Spacing.spacing_10)
                .background(color = YellowPrimary, shape = RoundedCornerShape(Spacing.spacing_22))
                .padding(horizontal = Spacing.spacing_12, vertical = Spacing.spacing_4)
        )
    }
}

@Composable
@Preview
fun CategoryLabelPreview() {
    CategoryLabel(
        "Category",
        modifier = Modifier
    )
}