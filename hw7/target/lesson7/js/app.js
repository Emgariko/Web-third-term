window.notify = function (message, messageType = "success") {
    if ((messageType !== "success" && messageType !== "error")
        || (messageType === undefined)) {
        messageType = "success";
    }
    $.notify(message, {
        position: "right bottom",
        className: messageType
    });
}

const ajax = function (obj, type = "POST", url="", dataType="json", data, success) {
    const error = obj.find(".error");
    const _defaultSuccess = function (response) {
        if (response["error"]) {
            error.text(response["error"]);
        } else {
            location.href = response["redirect"];
        }
    }

    const defaultValueSet = function (defaultValue) {
        return x => (x !== undefined && x !== null && x) ? x : defaultValue;
    }
    type = defaultValueSet("POST")(type);
    url = defaultValueSet("")(url);
    dataType = defaultValueSet("json")(dataType);
    if (success === null || success === undefined) {
        success = _defaultSuccess;
    }

    ajaxObject = {
        type: type,
        url: url,
        dataType: dataType,
        data: data,
        success: success
    }
    $.ajax(
        ajaxObject
    );
}