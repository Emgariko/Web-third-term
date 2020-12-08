<#-- @ftlvariable name="comments" type="ru.itmo.wp.domain.Post[]" -->
<#-- @ftlvariable name="curPost" type="ru.itmo.wp.domain.Post" -->
<#import "commons.ftlh" as c>

<@c.page>
    <#if curPost??>
        <@c.post curPost/>
        <div class="form">
            <div class="header">Write Comment</div>
            <div class="body">
                <form method="post" action="writeComment">
                    <div class="field">
                        <input type="hidden" name="postId" value="${curPost.getId()}">
<#--                        <input type="hidden" name="postId" value="hyu">-->
                        <div class="name">
                            <label for="text">Text</label>
                        </div>
                        <div class="value">
                            <textarea id="text" name="text"><#--${post.text!}--></textarea>
                        </div>
                        <@c.error "commentForm.text"/>
                    </div>
                    <div class="button-field">
                        <input type="submit" value="Write">
                    </div>
                </form>
            </div>
        </div>
        <div class="comments">
            <#list comments as comment>
                <p>Comment by ${comment.getUser().getLogin()}: ${comment.getText()}</p>
            </#list>
        </div>
    </#if>
</@c.page>