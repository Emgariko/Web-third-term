<#-- @ftlvariable name="article.hidden" type="java.lang.Boolean" -->
<#-- @ftlvariable name="userArticles" type="ru.itmo.wp.model.domain.Article[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="articles datatable">
        <div class="caption">My articles</div>
        <table>
            <thead>
            <tr>
                <th>Title</th>
                <th>Text</th>
                <th>Creation Time</th>
                <th>Visibility Control</th>
            </tr>
            </thead>
            <tbody>
            <#list userArticles as article>
                <tr>
                    <td>${article.title[0..*250]}</td>
                    <td>${article.text[0..*250]}</td>
                    <td>${article.creationTime}</td>
                    <td><input id="${article.id}" class="showHideButton" type="button" value="${article.hidden?then("Show", "Hide")}"></td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
    <script>
        $(function() {
            const $button = $(".articles table tbody tr td .showHideButton");
            $button.click(function() {
                $curbutton = $(this);
                const buttonValue = $curbutton.val();
                ajax($(this), null, null, null,
                    {
                        action: "setVisibility",
                        id: $(this).attr("id"),
                        buttonValue: buttonValue
                    },
                    function(response) {
                        $curbutton.val(response["buttonValue"]);
                    }
                );
            });
        })
    </script>
</@c.page>