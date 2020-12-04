<#-- @ftlvariable name="curUser" type="ru.itmo.wp.domain.User" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="userbox">
        <#if curUser??>
            <ul>
                <li>Id: ${curUser.id}</li>
                <li>Login: ${curUser.login}</li>
                <li>Creation time: ${curUser.creationTime}</li>
            </ul>
        <#else>
            No such user.
        </#if>
    </div>
</@c.page>
