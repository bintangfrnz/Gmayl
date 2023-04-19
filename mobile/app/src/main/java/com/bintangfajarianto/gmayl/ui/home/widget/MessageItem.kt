package com.bintangfajarianto.gmayl.ui.home.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bintangfajarianto.gmayl.data.home.MessageItem
import com.bintangfajarianto.gmayl.data.home.MessageItemResponse
import com.bintangfajarianto.gmayl.extension.StringDateFormat
import com.bintangfajarianto.gmayl.extension.format
import com.bintangfajarianto.gmayl.theme.GmaylTheme

@Composable
fun MessageItem(
    messageItem: MessageItem,
    modifier: Modifier = Modifier,
    onClickItem: () -> Unit,
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClickItem)
            .height(IntrinsicSize.Min)
            .background(color = GmaylTheme.color.mist10)
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        AsyncImage(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(color = GmaylTheme.color.primary50, shape = CircleShape),
            model = ImageRequest.Builder(LocalContext.current)
                .data(messageItem.imageUrl)
                .crossfade(true)
                .build(),
            placeholder = ColorPainter(color = GmaylTheme.color.mist50),
            fallback = ColorPainter(color = GmaylTheme.color.mist50),
            error = ColorPainter(color = GmaylTheme.color.mist50),
            contentDescription = "Avatar",
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = messageItem.sender,
                style = GmaylTheme.typography.contentMediumBold,
                color = GmaylTheme.color.mist100,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = messageItem.subject,
                style = GmaylTheme.typography.contentSmallSemiBold,
                color = GmaylTheme.color.mist100,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = messageItem.body,
                style = GmaylTheme.typography.contentSmallRegular,
                color = GmaylTheme.color.mist70,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Text(
            text = messageItem.getSentTimeAsLocalDate().format(
                format = StringDateFormat.MESSAGE_ITEM_DATE,
            ),
            style = GmaylTheme.typography.contentSmallSemiBold,
            color = GmaylTheme.color.mist100,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview
@Composable
private fun PreviewMessageItem() {
    MessageItem(
        messageItem = MessageItemResponse(
            sender = "Bintang Fajarianto",
            subject = "[Tubes 3] Ini contoh subject aja",
            body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent interdum nibh at porttitor pharetra. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Maecenas mattis vitae nisl at convallis. Quisque rhoncus, felis id bibendum commodo, risus nulla faucibus magna, id eleifend eros ante non sapien. Quisque.",
            sentTime = "2023-04-05T13:15:30+07:00",
            imageUrl = "",
        ),
    ) {}
}
