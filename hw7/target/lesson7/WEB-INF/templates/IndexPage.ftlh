<#import "commons.ftlh" as c>

<@c.page>
    Welcome on Codeforces!
    <div class="articles">
        <template>
            <article>
                <div class="title"></div>
                <div class="body"></div>
                <div class="information"></div>
                <div class="footer">
                    <div class="left">
                        <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                        <span class="positive-score">+0</span>
                        <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
                    </div>
                    <div class="right">
                        <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                        <div class="date"></div>
                        <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                        <a href="#">0</a>
                    </div>
                </div>
            </article>
        </template>
        <div class="noData">
            No data.
        </div>
    </div>
<script>
    $(function () {
        const $noData = $(".articles .noData");
        $noData.text("Loading...");
        counter = 0;
        ajax($(this), null, null, null, {action: "action"}, function(response) {
                response["articles"].forEach(function (article) {
                    if (!article["hidden"]) {
                        $noData.hide();
                        counter++;
                        const $article = $($(".articles template").prop("content")).clone();
                        $article.find(".title").text(article["title"]);
                        $article.find(".body").text(article["text"]);
                        $article.find(".information").text("By user with id=" + article["userId"]);
                        $article.find(".footer .right .date").text(article["creationTime"]);
                        $(".articles").append($article);
                    }
                });
            }
        );
        if (counter === 0) {
            $noData.text("No articles yet");
        }
    })
    $(function () {
        $("aside section").click(function () {
            $(this).css("background-color", "red");
        });
    })
</script>
</@c.page>
