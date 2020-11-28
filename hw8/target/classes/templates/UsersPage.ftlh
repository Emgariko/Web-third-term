<#-- @ftlvariable name="users" type="ru.itmo.wp.domain.User[]" -->

<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Created</th>
                <th>Enable/Disable</th>
            </tr>
            </thead>
            <tbody>
            <#if users?size==0>
                <tr class="noData">
                    <td colspan="4">
                        No data
                    </td>
                </tr>
            <#else>
                <#list users as user>
                <tr>
                    <td class="id">${user.id}</td>
                    <td class="login">${user.login}</td>
                    <td class="creationTime">${user.creationTime}</td>
                    <td class="action">
                        <form method="post" action="setStatus">
                            <#assign curAction = user.getStatus()?then("disable", "enable")/>
                            <input type="hidden" name="action" value=${curAction}>
                            <input type="hidden" name="userId" value="${user.getId()}">
                            <div class="button-field">
                                <input id="userStatusButton"
                                       type="submit" value="${curAction?capitalize}"/>
                            </div>
                        </form>
                    </td>
                </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
</@c.page>
