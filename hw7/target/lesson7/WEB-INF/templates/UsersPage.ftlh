<#-- @ftlvariable name="user" type="ru.itmo.wp.model.domain.User" -->
<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <#if user?? && user.admin>
                    <th>Admin</th>
                </#if>
            </tr>
            </thead>
            <tbody>
            <template>
                <tr>
                    <td class="user_id"></td>
                    <td class="user_login"></td>
                    <#if user?? && user.admin>
                        <td class="user_admin">
                            <div class="value"></div>
                            <a class="ref"></a>
                        </td>
                    </#if>
                </tr>
            </template>
            <tr class="noData">
                <td colspan="3">
                    No data
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <script>
        const $noData = $(".users .noData");
        $noData.find("td").text("Loading...");
        ajax($(this), null, null, null, { action: "findAll"}, function(response) {
            response["users"].forEach(function(user) {
                $noData.hide();
                const $tr = $($(".users template").prop("content")).clone();
                $tr.find(".user_id").text(user["id"]);
                $tr.find(".user_login").text(user["login"]);
                $tr.find(".user_admin .value").text(user["admin"] ? "true" : "false");
                $tr.find("tr").attr("data-userId", user["id"]);
                $tr.find(".user_admin .ref").attr("href", "#");
                $tr.find(".user_admin .ref").text(user["admin"] ? "disable" : "enable");
                $tr.find(".user_admin .ref").attr("userId", user["id"]);
                $tr.find(".user_admin .ref").attr("value", user["admin"]);
                $(".users tbody").append($tr);
            });
            $(".users .user_admin").click(function () {
                const $ref = $(this).find(".ref");
                const $value = $(this).find(".value");
                const $adminValue = $(this).find(".ref .value");
                const $link = $(this).find(".ref");
                const id = $ref.attr("userId");
                const buttonValue = $ref.text();
                ajax($(this), null, null, null,
                    {
                        action: "setAdmin",
                        userId: id,
                        buttonValue: buttonValue
                    },
                    function (response) {
                        $value.text(response["buttonValue"] === "disable" ? "true" : "false");
                        $adminValue.attr("value", ($adminValue === "true" ? "false" : "true"));
                        $link.text(response["buttonValue"]);
                    }
                );
                return false;
            })
            $(".users tr").click(function() {
                ajax($(this), null, null, null,
                    {
                        action: "findUser",
                        userId: $(this).attr("data-userId")
                    },
                    function (response) {
                        notify(response["user"].creationTime);
                    }
                );
            });
        });
    </script>
</@c.page>
