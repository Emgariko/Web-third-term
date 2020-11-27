<#import "./common.ftlh" as c>

<@c.page>
    <#--<#list posts?size-1..0 as i>
        <@c.showpost posts[i] c.const_true/>
    </#list>-->
    <@c.show_posts c.const_true c.const_middle_name/>
    <ul>
        <#list users as u>
            <li>
                <#if user?? && user.id == u.id>*</#if>
                <@c.userlink u c.const_false/>
            </li>
        </#list>
    </ul>
</@c.page>
