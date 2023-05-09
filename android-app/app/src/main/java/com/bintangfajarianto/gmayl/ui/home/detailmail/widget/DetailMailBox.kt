package com.bintangfajarianto.gmayl.ui.home.detailmail.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bintangfajarianto.gmayl.R
import com.bintangfajarianto.gmayl.data.model.auth.User
import com.bintangfajarianto.gmayl.data.model.home.Mail
import com.bintangfajarianto.gmayl.data.model.home.SentMail
import com.bintangfajarianto.gmayl.extension.StringDateFormat
import com.bintangfajarianto.gmayl.extension.format
import com.bintangfajarianto.gmayl.theme.GmaylTheme
import com.bintangfajarianto.gmayl.ui.widget.GmaylKeypair

@Composable
fun DetailMailBox(
    mail: Mail,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = GmaylTheme.color.mist10)
            .border(
                width = 1.dp,
                brush = SolidColor(GmaylTheme.color.mist30),
                shape = RoundedCornerShape(8.dp),
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        GmaylKeypair(
            keyContent = {
                Text(
                    text = stringResource(id = R.string.mail_detail_from),
                    style = GmaylTheme.typography.contentSmallSemiBold,
                    color = GmaylTheme.color.mist100,
                )
            },
            pairContent = {
                Text(
                    text = mail.sender.email,
                    style = GmaylTheme.typography.contentSmallRegular,
                    color = GmaylTheme.color.mist70,
                )
            },
        )
        Divider(color = GmaylTheme.color.mist30)
        GmaylKeypair(
            keyContent = {
                Text(
                    text = stringResource(id = R.string.mail_detail_to),
                    style = GmaylTheme.typography.contentSmallSemiBold,
                    color = GmaylTheme.color.mist100,
                )
            },
            pairContent = {
                Text(
                    text = mail.receiver.email,
                    style = GmaylTheme.typography.contentSmallRegular,
                    color = GmaylTheme.color.mist70,
                )
            },
        )
        Divider(color = GmaylTheme.color.mist30)
        GmaylKeypair(
            keyContent = {
                Text(
                    text = stringResource(id = R.string.mail_detail_date),
                    style = GmaylTheme.typography.contentSmallSemiBold,
                    color = GmaylTheme.color.mist100,
                )
            },
            pairContent = {
                Text(
                    text = mail.getSentTimeAsLocalDate().format(
                        format = StringDateFormat.FULL_DATE,
                    ),
                    style = GmaylTheme.typography.contentSmallRegular,
                    color = GmaylTheme.color.mist70,
                )
            },
        )
    }
}

@Preview
@Composable
private fun PreviewDetailMailBox() {
    DetailMailBox(
        mail = SentMail(
            sender = User(email = "admin@gmail.com"),
            receiver = User(email = "you@gmail.com"),
            subject = "[Tubes 3] Ini contoh subject aja",
            body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent interdum nibh at porttitor pharetra. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Maecenas mattis vitae nisl at convallis. Quisque rhoncus, felis id bibendum commodo, risus nulla faucibus magna, id eleifend eros ante non sapien. Quisque.",
            sentTime = "2023-04-05T13:15:30+07:00",
        ),
    )
}
