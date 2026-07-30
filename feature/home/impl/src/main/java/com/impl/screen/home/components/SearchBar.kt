package com.impl.screen.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.designsystem.FontSize
import com.designsystem.Spacing
import com.designsystem.theme.Neutral02DarkestGrey
import com.designsystem.theme.Neutral05LightGrey
import com.designsystem.theme.Neutral08Whisper
import com.movieapp.impl.home.R

@Composable
fun SearchBar(
    query: String,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit,
    onQueryChange: (String) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(Spacing.spacing_36)
            .background(
                color = Neutral02DarkestGrey,
                shape = RoundedCornerShape(Spacing.spacing_24),
            )
            .padding(horizontal = Spacing.spacing_12),
        contentAlignment = Alignment.CenterStart
    ) {
        if (query.isEmpty()) {
            Text(
                text = stringResource(R.string.feature_home_impl_search),
                fontSize = FontSize.fontSize_14,
                color = Neutral05LightGrey,
                modifier = Modifier.padding(start = Spacing.spacing_36)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                tint = Neutral05LightGrey,
                painter = painterResource(com.movieapp.designsystem.R.drawable.ic_looking_glass),
                modifier = Modifier.padding(start = Spacing.spacing_12),
                contentDescription = null
            )
            BasicTextField(
                value = query,
                cursorBrush = SolidColor(Neutral05LightGrey),
                onValueChange = onQueryChange,
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = FontSize.fontSize_14,
                    color = Neutral05LightGrey
                ),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(start = Spacing.spacing_12)
            )

            if (query.isNotEmpty()) {
                Icon(
                    modifier = Modifier
                        .clickable {
                            onDeleteClick()
                        }
                        .padding(start = Spacing.spacing_12),
                    painter = painterResource(com.movieapp.designsystem.R.drawable.ic_cancel),
                    contentDescription = null,
                    tint = Neutral08Whisper
                )
            }
        }
    }
}