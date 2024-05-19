function searchRecordList(page) {
    if (page == undefined) {
        page = 0;
    }

    $("#page").val(page);
    $("#form").submit();
}

function search() {
    $("#page").val(0);
    $("#form").submit();
}

function resetFilter() {
    document.getElementById('title').value = null;
    document.getElementById('startDate').value = null;
    document.getElementById('endDate').value = null;
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

function remove(recordId, memberId) {
    if (!confirm("Are you sure you want to delete?")) {
        return;
    }

    const url = "/records/" + recordId;
    const data = {
        "memberId": memberId
    };

    $.ajax({
        type: 'DELETE',
        url: url,
        dataType: 'json',
        data: JSON.stringify(data),
        contentType: 'application/json; charset=utf-8'
    }).done(function (response) {
        if (response.success) {
            alert("Deleted successfully.");
            location.href = "/record";
            return;
        }
        alert("Failed to delete. Please try again.");
    }).fail(function (error) {
        let responseJson = error.responseJSON;
        alert("Failed to delete. Please contact the administrator.\n(" + responseJson.message + ")");
    });
}