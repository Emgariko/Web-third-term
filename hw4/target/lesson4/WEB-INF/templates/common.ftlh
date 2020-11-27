<#-- @ftlvariable name="userColors" type="ru.itmo.web.lesson4.model.UserColor[]" -->
<#-- @ftlvariable name="uri" type="String" -->
<#assign const_true = "true">
<#assign const_false = "false">
<#assign const_middle_name = "middle">
<#assign const_sidebar_name = "sibebar">

<#macro page>
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title>Codeforces</title>
        <link rel="stylesheet" href="/css/normalize.css">
        <link rel="stylesheet" href="/css/style.css">
        <link rel="icon" href="/favicon.ico">
    </head>
    <body>
    <@header/>
    <div class="middle">
        <@sidebar/>
        <main>
            <#nested/>
        </main>
    </div>
    <@footer/>
    </body>
    </html>
</#macro>

<#macro header>
    <header>
        <a href="#" class="logo"><img alt="Codeforces" title="Codeforces"
                                      src="/img/codeforces-vs-coronavirus-65.png"></a>
        <div class="languages">
            <a href="#"><img alt="In English" title="In English"
                             src="/img/gb.png"></a>
            <a href="#"><img alt="In Russian" title="In Russian"
                             src="/img/ru.png"></a>
        </div>
        <div class="enter-or-register-box">
            <#if user??>
                <@userlink user const_true/>
                |
                <a href="/logout">Logout</a>
            <#else>
                <a href="/enter">Enter</a>
                |
                <a href="/register">Register</a>
            </#if>
        </div>
        <nav>
            <#assign items=[
            {"href": "/index", "name": "Home"},
            {"href": "/misc/help", "name": "Help"},
            {"href": "/contests", "name": "Contests"},
            {"href": "/users", "name": "Users"}
            ]/>
            <ul>
                <#list items as item>
                    <li>
                        <#if item["href"]?starts_with(uri)>
                            <a class="underlinedlink" href="${item.href}">${item.name}</a>
                        <#else>
                            <a href="${item.href}">${item.name}</a>
                        </#if>

                    </li>
                </#list>
            </ul>
        </nav>
    </header>
</#macro>

<#macro sidebar>
    <aside>
<#--        <@show_posts_by_user_id "-1"/>-->
        <@show_posts const_true const_sidebar_name/>
    </aside>
</#macro>

<#macro footer>
    <footer>
        2010-2020 &copy; by Mike Mirzayanov
    </footer>
</#macro>

<#function getColor u>
    <#list userColors as color>
        <#if color == u.color>
            <#return color.toCssClass()>
        </#if>
    </#list>
</#function>

<#macro userlink user nameOnly>
    <#if nameOnly == const_true>
        <a class="userLink"
           href="/user?handle=${user.handle}">${user.handle}</a>
    <#else>
        <a class="userLink userLink${getColor(user)}" href="/user?handle=${user.handle}">${user.handle}</a>
    </#if>
</#macro>

<#function findBy items key value>
    <#list items as item>
        <#if item[key] == value>
            <#return item/>
        </#if>
    </#list>
</#function>

<#macro userpage u>
    <#if u?has_content>
        <div class="userbox">
            <@userlink u const_false/>
            <#if u.handle == "Emgariko">
                <img class="userimg" src="../../img/me.jpg" alt="userpic" title="Emgariko">
            <#else>
                <img class="userimg" src="../../img/no-title.jpg" alt="no-title" title="No-title">
            </#if>
            <ul>
                <li>id: ${u.id}</li>
                <li>handle: ${u.handle}</li>
                <li>name: ${u.name}</li>
            </ul>
        </div>
        <div class="usernav">
            <#list users as cur>
                <#if cur.id == u.id>
                    <#if !(cur?is_first)>
                        <a href="/user?handle=${users[cur_index - 1].handle}">&larr;</a>
                    <#else>
                        &larr;
                    </#if>
                    <#if cur?has_next>
                        <a href="/user?handle=${users[cur_index + 1].handle}">&rarr;</a>
                    <#else>
                        &rarr;
                    </#if>
                    <#break>
                </#if>
            </#list>
        </div>
        <div class="user_posts">
            <a href="/posts?user_id=${u.id}">Posts</a>
            by this user: <@user_posts_count u/>
        </div>
    <#else>
        No such user.
    </#if>
</#macro>

<#macro show_post_text post preview>
    <#if preview == const_true>
        ${post.text[0..*250]}
        <#if 250 < post.text?length>
            ...
        </#if>
    <#else>
        ${post.text}
    </#if>
</#macro>

<#macro sidebar_post_markup p preview>
    <section>
        <div class="header">Post #${p.id}</div>
        <div class="body">
            <@show_post_text p preview/>
        </div>
        <div class="footer">
            <#--                    <a href="#">View all</a>-->
            <a href="/post?post_id=${p.id}">View all</a>
        </div>
    </section>
</#macro>

<#macro middle_post_markup p preview>
    <article>
    <#assign pi = findBy(users, "id", p.userId)!/>
    <div class="title">${p.title}</div>
    <div class="information">by ${pi.name}</div>
    <div class="body">
        <@show_post_text p preview/>
    </div>
    <ul class="attachment">
        <li>Codeforces post attachment</li>
    </ul>
    <div class="footer">
        <div class="left">
            <img src="../../img/voteup.png" title="Vote Up" alt="Vote Up"/>
            <span class="positive-score">+173</span>
            <img src="../../img/votedown.png" title="Vote Down" alt="Vote Down"/>
        </div>
        <div class="right">
            <img src="../../img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
            1 days ago
            <img src="../../img/comments_16x16.png" title="Comments" alt="Comments"/>
            <a href="#">68</a>
        </div>
    </div>
    </article>
</#macro>

<#macro show_post p preview place>
    <#assign pi = findBy(users, "id", p.userId)!/>
    <#if place == const_sidebar_name>
        <@sidebar_post_markup p preview/>
    <#elseif place == const_middle_name>
        <@middle_post_markup p preview/>
    </#if>
</#macro>

<#macro show_posts preview place>
    <#list posts?size-1..0 as i>
        <@show_post posts[i] preview place/>
    </#list>
</#macro>

<#macro user_posts_count u>
    <#assign count = 0>
    <#list posts as post>
        <#if post.userId == u.id>
            <#assign count++>
        </#if>
    </#list>
    ${count}
</#macro>

<#macro show_posts_by_user_id uid preview place>
    <#assign count = 0>
    <#list posts?size-1..0 as i>
        <#if posts[i].userId == uid>
            <@show_post posts[i] preview place/>
            <#assign count++>
        </#if>
    </#list>
    <#if count == 0>
        There are no posts by this user :(
    </#if>
</#macro>
