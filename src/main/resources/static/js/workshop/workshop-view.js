function remove(workshopId) {
    if (!confirm("Are you sure you want to delete?")) {
        return;
    }

    const url = "/workshop/" + workshopId;

    $.ajax({
        type: 'DELETE',
        url: url,
        dataType: 'json',
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

function subscribe(workshopId) {
    if (!confirm("Would you like to subscribe?")) {
        return;
    }

    const url = "/workshop/subscribe/" + workshopId;

    $.ajax({
        type: 'POST',
        url: url,
        dataType: 'json',
        contentType: 'application/json; charset=utf-8'
    }).done(function (response) {
        if (response.success) {
            alert("Subscribed successfully.");
            window.location.reload();
            return;
        }
        alert("Failed to subscribe. Please try again.");
    }).fail(function (error) {
        let responseJson = error.responseJSON;
        alert("Failed to subscribe. Please contact the administrator.\n(" + responseJson.message + ")");
    });
}

function alreadySubscribed() {
    alert("You are already subscribed.");
}