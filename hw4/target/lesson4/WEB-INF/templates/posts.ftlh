<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#import "./common.ftlh" as c>

<#if user_id?? && user_id?is_number>
    <@c.page>
        <@c.show_posts_by_user_id user_id c.const_false c.const_middle_name/>
    </@c.page>
<#else>
    <@c.page>There are no posts by this user :(</@c.page>
</#if>
