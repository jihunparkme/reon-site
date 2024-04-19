function searchRecordList(page) {
    if (page == undefined) {
        page = 0;
    }

    $("#page").val(page);
    $("#form").submit();
}

function share(id, pilot) {
    const url = "/records/" + id + "/pilot";
    const data = {
        "pilot": pilot
    };

    $.ajax({
        type: 'PUT',
        url: url,
        dataType: 'json',
        data: JSON.stringify(data),
        contentType: 'application/json; charset=utf-8'
    }).done(function (response) {
        if (response.success) {
            alert("Saved successfully.");
            location.replace(location.href);
            return;
        }
        alert("Failed to save. Please try again.");
    }).fail(function (error) {
        let responseJson = error.responseJSON;
        alert("Failed to save. Please contact the administrator.\n(" + responseJson.message + ")");
    });
}