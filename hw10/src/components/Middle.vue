<template>
    <div class="middle">
        <Sidebar :posts="viewSidebarPosts"/>
        <main>
            <Index v-if="page === 'Index'" :posts="viewAllPosts" :usersName="usersName"
                   :postIdToCommentCount="postIdToCommentCount"/>
            <Enter v-if="page === 'Enter'"/>
            <WritePost v-if="page === 'WritePost'"/>
            <EditPost v-if="page === 'EditPost'"/>
            <Register v-if="page === 'Register'"/>
            <Users v-if="page === 'Users'" :users="this.users"/>
            <Post v-if="page === 'Post'" :post="this.curPost" :usersName="usersName"
                  :postIdToCommentCount="postIdToCommentCount"
                  :commentUserIdToUserName="commentUserIdToUserName"/>
        </main>
    </div>
</template>

<script>
import Sidebar from "@/components/sidebar/Sidebar";
import Index from "@/components/middle/Index";
import Enter from "@/components/middle/Enter";
import WritePost from "@/components/middle/WritePost";
import EditPost from "@/components/middle/EditPost";
import Register from "@/components/middle/Register";
import Users from "@/components/middle/Users"
import Post from "@/components/middle/Post"

export default {
    name: "Middle",
    data: function () {
        return {
            page: "Index",
            curPost : ""
        }
    },
    components: {
        Post,
        Register,
        WritePost,
        Enter,
        Index,
        Sidebar,
        EditPost,
        Users
    },
    props: ["posts", "users"],
    computed: {
        viewSidebarPosts: function () {
            return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
        },
        viewAllPosts: function() {
            return Object.values(this.posts).sort((a, b) => b.id - a.id)
        },
        usersName : function() {
            const result = {}
            Object.values(this.users).forEach((cur) => (result[cur.id] = cur.name));
            return result;
        },
        postIdToCommentCount : function() {
            const result = {}
            Object.values(this.$root.$data.comments).forEach((cur) => (result[cur.postId] = 0));
            Object.values(this.$root.$data.comments).forEach((cur) => (result[cur.postId]++));
            return result;
        },
        commentUserIdToUserName : function() {
            const result = {}
            Object.values(this.$root.$data.comments).forEach((comment) => (result[comment.userId] = -1));
            Object.values(this.$root.$data.users).forEach(
                (curUser) =>
                {
                    if (result[curUser.id] === -1) { (result[curUser.id] = curUser.name) }
                }
            );
            return result;
        }
    },
    beforeCreate() {
        this.$root.$on("onChangePage", (page) => this.page = page);
        this.$root.$on("onPost", (post) => {this.curPost = post; this.page = 'Post'});
    }
}
</script>

<style scoped>

</style>