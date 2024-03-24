function register() {
    let data = {
        "categoryId": $('#categoryId').val(),
        "name": $('#name').val(),
        "productNo": $('#productNo').val(),
        "color": $('#color').val(),
        "ratedVoltage": $('#ratedVoltage').val(),
        "size": $('#size').val()
    };

    if (isNotValid(data)) {
        return;
    }

    console.log(data);

    $.ajax({
        type: 'POST',
        url: "/admin/products",
        dataType: 'json',
        data: JSON.stringify(data),
        contentType: 'application/json; charset=utf-8'
    }).done(function (response) {
        showCreatedSerialNo(response);
    }).fail(function (error) {
        let responseJson = error.responseJSON;
        alert(responseJson.message);
    });
}

function isNotValid(data) {
    if (data.categoryId == 0) {
        alert("Category is required.");
        return true;
    }

    if (data.name.length == 0) {
        alert("Name is required.");
        return true;
    }

    if (data.productNo.length == 0) {
        alert("Product No is required.");
        return true;
    }

    if (data.color.length == 0) {
        alert("Color is required.");
        return true;
    }

    if (data.ratedVoltage.length == 0) {
        alert("Rated Voltage is required.");
        return true;
    }

    if (data.size < 1) {
        alert("Size must be at least greater than 1.");
        return true;
    }

    return false;
}

function showCreatedSerialNo(response) {

}