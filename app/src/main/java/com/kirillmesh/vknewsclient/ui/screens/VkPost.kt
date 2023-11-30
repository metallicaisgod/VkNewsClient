package com.kirillmesh.vknewsclient.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kirillmesh.vknewsclient.R
import com.kirillmesh.vknewsclient.domain.FeedPost
import com.kirillmesh.vknewsclient.domain.StatisticElement
import com.kirillmesh.vknewsclient.domain.StatisticType
import com.kirillmesh.vknewsclient.ui.theme.RalewayFontFamily


@Composable
fun VkPostCard(
    modifier: Modifier = Modifier,
    feedPost: FeedPost,
    onStatisticViewsClickListener: () -> Unit,
    onStatisticSharesClickListener: () -> Unit,
    onStatisticCommentsClickListener: () -> Unit,
    onStatisticLikesClickListener: () -> Unit,
) {
    Card(
        modifier = modifier,
//        shape = RoundedCornerShape(4.dp),
//        border = BorderStroke(0.5.dp, Color.Gray),
        contentColor = MaterialTheme.colors.background
    ) {
        Column(modifier = Modifier.padding(2.dp)) {
            PostHeader(feedPost)
            Text(text = feedPost.contentText, color = MaterialTheme.colors.onBackground)
            AsyncImage(
                model = feedPost.contentImageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp, bottom = 2.dp)
                    .wrapContentHeight(),
                contentScale = ContentScale.FillWidth
            )
            Statistic(
                statistics = feedPost.statistics,
                onStatisticViewsClickListener = onStatisticViewsClickListener,
                onStatisticSharesClickListener = onStatisticSharesClickListener,
                onStatisticCommentsClickListener = onStatisticCommentsClickListener,
                onStatisticLikesClickListener = onStatisticLikesClickListener
            )
        }
    }
}

@Composable
private fun Statistic(
    statistics: List<StatisticElement>,
    onStatisticViewsClickListener: () -> Unit,
    onStatisticSharesClickListener: () -> Unit,
    onStatisticCommentsClickListener: () -> Unit,
    onStatisticLikesClickListener: () -> Unit,
) {
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
            val viewsElement = statistics.getElementByType(StatisticType.VIEWS)
            PostStatistic(
                viewsElement.count,
                R.drawable.ic_eye
            ) {
                onStatisticViewsClickListener()
            }
        }
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val sharesElement = statistics.getElementByType(StatisticType.SHARES)
            PostStatistic(
                sharesElement.count,
                R.drawable.ic_share
            ) {
                onStatisticSharesClickListener()
            }
            val commentsElement = statistics.getElementByType(StatisticType.COMMENTS)
            PostStatistic(
                commentsElement.count,
                R.drawable.ic_comment
            ) {
                onStatisticCommentsClickListener()
            }
            val likesElement = statistics.getElementByType(StatisticType.LIKES)
            PostStatistic(
                likesElement.count,
                R.drawable.ic_like
            ) {
                onStatisticLikesClickListener()
            }
        }
    }
}

private fun List<StatisticElement>.getElementByType(type: StatisticType): StatisticElement {
    return this.find { it.type == type }
        ?: throw IllegalStateException("getElementByType exited -1")
}

@Composable
private fun PostStatistic(
    count: Long,
    resId: Int,
    onItemClickListener: () -> Unit,
) {
    Row(
        modifier = Modifier.clickable { onItemClickListener() },
        verticalAlignment = Alignment.CenterVertically
    ) {
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

@Composable
private fun PostHeader(
    feedPost: FeedPost,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = feedPost.communityAvatarUrl,
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
                feedPost.communityName,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onBackground
            )
            Text(
                feedPost.publicationData,
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
