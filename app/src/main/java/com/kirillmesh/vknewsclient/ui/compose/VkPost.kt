package com.kirillmesh.vknewsclient.ui.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kirillmesh.vknewsclient.R
import com.kirillmesh.vknewsclient.ui.theme.RalewayFontFamily
import com.kirillmesh.vknewsclient.ui.theme.VkNewsClientTheme


@Composable
fun VkPostCard() {
    Card(
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(0.5.dp, Color.Gray),
        colors = cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.padding(2.dp)) {
            PostHeader()
            Text(text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras vel augue ex. In mattis turpis neque, sit amet tempus ligula viverra non. Nam egestas nibh id nibh imperdiet tincidunt.")
            Image(
                painter = painterResource(id = R.drawable.post_content_image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp, bottom = 2.dp),
                contentScale = ContentScale.FillWidth
            )
            Statistic()
        }
    }
}

@Composable
private fun Statistic() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp, bottom = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            modifier = Modifier.weight(1f),

        ) {
            PostStatistic(206, R.drawable.ic_eye)
        }
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PostStatistic(203, R.drawable.ic_share)
            PostStatistic(11, R.drawable.ic_comment)
            PostStatistic(463, R.drawable.ic_like)
        }
    }
}

@Composable
private fun PostHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.post_comunity_thumbnail),
            contentDescription = "",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
        Column(
            modifier = Modifier
                .padding(start = 4.dp)
                .weight(1f)
        ) {
            Text(
                "Group name",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                "14:00",
                color = Color.Gray
            )

        }
//                IconButton(onClick = { }) {
        Icon(
            Icons.Default.MoreVert,
            contentDescription = null,
            tint = Color.Gray
        )
//                }
//                DropdownMenu(expanded = false, onDismissRequest = { }) {
//
//                }
    }
}

@Composable
private fun PostStatistic(count: Int, resId: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = count.toString(),
            fontFamily = RalewayFontFamily,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.width(4.dp))
        Image(
            painter = painterResource(id = resId),
            contentDescription = null
        )
    }

}

@Preview
@Composable
fun PreviewCardLight() {
    VkNewsClientTheme(
        darkTheme = false
    ) {
        PostCardInBox()
    }
}

@Preview
@Composable
fun PreviewCardDark() {
    VkNewsClientTheme(
        darkTheme = true
    ) {
        PostCardInBox()
    }
}

@Composable
private fun PostCardInBox() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(4.dp)
    ) {
        VkPostCard()
    }
}