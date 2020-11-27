<#-- @ftlvariable name="error" type="java.lang.String" -->
<#-- @ftlvariable name="user" type="ru.itmo.wp.model.domain.User" -->
<#-- @ftlvariable name="talks" type="ru.itmo.wp.model.domain.Talk[]" -->
<#-- @ftlvariable name="u.login" type="java.lang.String" -->
<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#-- @ftlvariable name="loginOrEmail" type="java.lang.String" -->
<#-- @ftlvariable name="password" type="java.lang.String" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="form-box">
        <div class="header">Message</div>
        <div class="body">
            <form method="post" action="" id="messaging">
                <input type="hidden" name="action" value="sendMessage"/>
                <div class="field">
                    <div class="name">
                        <label for="recipient">Recipient</label>
                    </div>
                    <div class="value">
                        <select id="recipients" form="messaging" name="recipient">
                            <#list users as u>
                                <#if u.id != user.id>
                                    <option value="${u.id}">${u.login}</option>
                                </#if>
                            </#list>
                        </select>
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="message">Message</label>
                    </div>
                    <div class="value">
<#--                        <input id="message" name="message" type="text" value="${password!}"/>-->
                        <textarea id="message" name="talkMessage"></textarea>
                    </div>
                </div>
                <#if error??>
                    <div class="error">
                        ${error!}
                    </div>
                </#if>
                <div class="button-field">
                    <input type="submit" value="Send">
                </div>
            </form>
        </div>
    </div>
    <div class="datatable">
        <div class="caption">Messages</div>
        <table>
            <thead>
            <tr>
                <th>From</th>
                <th>To</th>
                <th>Text</th>
                <th>Time</th>
            </tr>
            </thead>
            <tbody>
            <#list talks as talk>
                <tr>
                    <#if talk.getSourceUserId() == user.id>
                        <td><b>Me</b></td>
                    <#else>
                        <td>${findUserLoginById(talk.getSourceUserId(), users)}</td>
                    </#if>
                    <#if talk.getTargetUserId() == user.id>
                        <td><b>Me</b></td>
                    <#else>
                        <td>${findUserLoginById(talk.getTargetUserId(), users)}</td>
                    </#if>
                    <td><small>${talk.getText()[0..*250]} <#if 250 < talk.getText()?length>...</#if></small></td>
                    <td>${talk.getCreationTime()}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>

<#function findUserLoginById userId users>
    <#list users as curUser>
        <#if userId == curUser.getId()>
            <#return curUser.getLogin()>
        </#if>
    </#list>
</#function>
