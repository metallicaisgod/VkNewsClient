package com.kirillmesh.vknewsclient.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kirillmesh.vknewsclient.domain.Comment
import com.kirillmesh.vknewsclient.domain.FeedPost
import com.kirillmesh.vknewsclient.ui.states.CommentsScreenState
import com.kirillmesh.vknewsclient.ui.viewmodels.CommentsViewModel
import com.kirillmesh.vknewsclient.ui.viewmodels.CommentsViewModelFactory

@Composable
fun CommentsScreen(
    onBackPressedListener: () -> Unit,
    feedPost: FeedPost,
) {
    val viewModel: CommentsViewModel = viewModel(
        factory = CommentsViewModelFactory(feedPost = feedPost)
    )
    val currentState = viewModel.screenState.value
    if (currentState is CommentsScreenState.Comments) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Comments for post ${currentState.feedPost.communityName}",
                            color = MaterialTheme.colors.onPrimary
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { onBackPressedListener() }) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    }
                )
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier.padding(paddingValues = paddingValues),
                contentPadding = PaddingValues(
                    top = 16.dp,
                    start = 8.dp,
                    end = 8.dp,
                    bottom = 72.dp
                )
            ) {
                items(
                    items = currentState.comments,
                    key = { it.id }
                ) {
                    CommentItem(comment = it)
                }
            }
        }
    }
}

@Composable
private fun CommentItem(
    comment: Comment,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 8.dp)
    ) {
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = comment.authorAvatarId),
            contentDescription = null
        )
        Spacer(modifier = Modifier.size(8.dp))
        Column {
            Text(
                text = "${comment.authorName} CommentId: ${comment.id}",
                color = MaterialTheme.colors.onPrimary,
                fontSize = 12.sp
            )
            Text(
                text = comment.commentText,
                color = MaterialTheme.colors.onPrimary,
                fontSize = 14.sp
            )
            Text(
                text = comment.commentTime,
                color = MaterialTheme.colors.onSecondary,
                fontSize = 12.sp
            )
        }
    }
}

//@Preview
//@Composable
//fun PreviewCommentDark() {
//    VkNewsClientTheme(true) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(MaterialTheme.colors.primary)
//        ) {
//            CommentItem(comment = Comment(id = 0))
//        }
//    }
//}
//
//@Preview
//@Composable
//fun PreviewCommentLight() {
//    VkNewsClientTheme(false) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(MaterialTheme.colors.primary)
//        ) {
//            CommentItem(comment = Comment(id = 0))
//        }
//    }
//}
