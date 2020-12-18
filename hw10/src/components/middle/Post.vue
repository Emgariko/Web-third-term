<template>
    <div id="post">
        <article>
            <div class="title">
                <a href="#" > {{ post.title }} </a>
            </div>
            <div class="information">By {{ usersName[post.userId] }} </div>
            <div class="body"> {{ post.text }} </div>
            <ul class="attachment">
                <li>Announcement of <a href="#">Codeforces Round #XYZ (Div. 1)</a></li>
                <li>Announcement of <a href="#">Codeforces Round #XYZ (Div. 2)</a></li>
            </ul>
            <div class="footer">
                <div class="left">
                    <img src="../../assets/img/voteup.png" title="Vote Up" alt="Vote Up"/>
                    <span class="positive-score">+173</span>
                    <img src="../../assets/img/votedown.png" title="Vote Down" alt="Vote Down"/>
                </div>
                <div class="right">
                    <img src="../../assets/img/date_16x16.png" title="Puslish Time" alt="Publish Time"/>
                    Today, 2 hours ago
                    <img src="../../assets/img/comments_16x16.png" title="Comments" alt="Comments"/>
                    <a href="#"> {{ postIdToCommentCount[post.id] || 0}} </a>
                </div>
            </div>
        </article>
        <div class="comments">
            <p v-for="comment in getComments(post.id)" :key="comment.id">
                Comment by {{ commentUserIdToUserName[comment.userId] }}: {{ comment.text }}
            </p>
        </div>
    </div>
</template>

<script>
export default {
    name: "Post",
    props : ["post", "usersName", "postIdToCommentCount", "commentUserIdToUserName"],
    methods: {
        getComments : function(postId) {
            return Object.values(this.$root.$data.comments).filter((cur) => (cur.postId === postId));
        }
    }
}
</script>


<style scoped>

</style>