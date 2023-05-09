package com.bintangfajarianto.gmayl.ui.home.detailmail.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bintangfajarianto.gmayl.R
import com.bintangfajarianto.gmayl.data.model.auth.User
import com.bintangfajarianto.gmayl.data.model.home.Mail
import com.bintangfajarianto.gmayl.data.model.home.SentMail
import com.bintangfajarianto.gmayl.extension.StringDateFormat
import com.bintangfajarianto.gmayl.extension.format
import com.bintangfajarianto.gmayl.theme.GmaylTheme

@Composable
fun SenderItem(
    mail: Mail,
    modifier: Modifier = Modifier,
    onClickReply: () -> Unit,
) {
    var expand by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(mail.sender.avatarUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.ic_default_account),
                fallback = painterResource(id = R.drawable.ic_default_account),
                error = painterResource(id = R.drawable.ic_default_account),
                contentDescription = "Avatar",
                contentScale = ContentScale.Crop,
            )
            Column(
                modifier = Modifier
                    .clickable { expand = !expand }
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = mail.sender.name,
                    style = GmaylTheme.typography.contentMediumBold,
                    color = GmaylTheme.color.mist100,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    val receiver =
                        if (mail.sender.email == mail.receiver.email) "me" else mail.receiver.email
                    Text(
                        text = stringResource(id = R.string.mail_detail_to_email, receiver),
                        style = GmaylTheme.typography.contentSmallRegular,
                        color = GmaylTheme.color.mist70,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Icon(
                        modifier = Modifier.size(14.dp),
                        painter = rememberVectorPainter(image = Icons.Default.KeyboardArrowDown),
                        contentDescription = "chevron down",
                        tint = GmaylTheme.color.mist70,
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = mail.getSentTimeAsLocalDate().format(
                        format = StringDateFormat.MESSAGE_ITEM_DATE,
                    ),
                    style = GmaylTheme.typography.contentSmallSemiBold,
                    color = GmaylTheme.color.mist100,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Icon(
                    modifier = Modifier.clickable(onClick = onClickReply),
                    painter = painterResource(id = R.drawable.ic_reply),
                    contentDescription = "icon reply",
                )
            }
        }

        if (expand) {
            DetailMailBox(mail = mail)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSenderItem() {
    SenderItem(
        mail = SentMail(
            sender = User(name = "Bintang Fajarianto"),
            receiver = User(email = "you@gmail.com"),
            subject = "[Tubes 3] Ini contoh subject aja",
            body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent interdum nibh at porttitor pharetra. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Maecenas mattis vitae nisl at convallis. Quisque rhoncus, felis id bibendum commodo, risus nulla faucibus magna, id eleifend eros ante non sapien. Quisque.",
            sentTime = "2023-04-05T13:15:30+07:00",
            encrypted = false,
        ),
        onClickReply = {},
    )
}
