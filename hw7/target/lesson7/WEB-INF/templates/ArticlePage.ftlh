<#-- @ftlvariable name="error" type="java.lang.String" -->
<#-- @ftlvariable name="title" type="java.lang.String" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="write form-box">
        <div class="header">Article</div>
        <div class="body">
            <form method="post" action="" id="articleWriting">
                <input type="hidden" name="action" value="writeArticle"/>
                <div class="field">
                    <div class="name">
                        <label for="title">Title</label>
                    </div>
                    <div class="value">
<#--                        <input id="title" name="title" value="${title!}"/>-->
                        <input id="title" name="title" value=""/>
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="text">Text</label>
                    </div>
                    <div class="value">
                        <#--                        <input id="message" name="message" type="text" value="${password!}"/>-->
                        <textarea id="text" name="articleText"></textarea>
                    </div>
                </div>
                <div class="error">
                    ${error!}
                </div>
                <div class="button-field">
                    <input type="submit" value="Create">
                </div>
            </form>
        </div>
    </div>

    <script>
        $(function () {
            $(".write form").submit(function () {
                const title = $(this).find("input[name='title']").val();
                const articleText = $(this).find("textarea[name='articleText']").val();
                const $error = $(this).find(".error");
                ajax($(this), null, null, null, { action: "createArticle", title: title, articleText: articleText}, null);
                return false;
            });
        })
    </script>
</@c.page>