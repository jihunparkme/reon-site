function remove(workshopId) {
    if (!confirm("Are you sure you want to delete?")) {
        return;
    }

    const url = "/workshop/" + workshopId;
    const data = {
        "workshopId": workshopId
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
            location.href = "/workshop";
            return;
        }
        alert("Failed to delete. Please try again.");
    }).fail(function (error) {
        let responseJson = error.responseJSON;
        alert("Failed to delete. Please contact the administrator.\n(" + responseJson.message + ")");
    });
}