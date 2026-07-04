package com.impl.screen.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.designsystem.FontSize
import com.designsystem.Spacing
import com.designsystem.theme.Neutral02DarkestGrey
import com.designsystem.theme.Neutral06LightGrey
import com.movieapp.impl.home.R

@Composable
fun SearchBar(
    query: String,
    modifier: Modifier = Modifier,
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
                color = Neutral06LightGrey
            )
        }
        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            singleLine = true,
            textStyle = TextStyle(
                fontSize = FontSize.fontSize_14,
                color = Neutral06LightGrey
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}