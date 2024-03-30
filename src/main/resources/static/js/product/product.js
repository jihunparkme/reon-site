function searchProductList(page) {
    if (page == undefined) {
        page = 0;
    }

    $("#page").val(page);
    $("#list-form").submit();
}

function create() {
    const data = {
        "manufacturedDt": $('#manufacturedDt').val(),
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

    $.ajax({
        type: 'POST',
        url: "/admin/products",
        dataType: 'json',
        data: JSON.stringify(data),
        contentType: 'application/json; charset=utf-8'
    }).done(function (response) {
        showCreatedSerialNo(response);
    }).fail(function (error) {
        const responseJson = error.responseJSON;
        alert("Failed to create serial no. Please contact the administrator.\n(" + responseJson.message + ")");
    });
}

function isNotValid(data) {
    if (data.manufacturedDt.length == 0) {
        alert("Manufactured Date is required.");
        return true;
    }

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
    const serialNos = response.data.map(item => item.no);
    const serialNosString = serialNos.join('\n');

    document.getElementById('serial-nos').value = serialNosString;
    document.getElementById('serial-nos-area').style.display = 'block';
    document.getElementById('create-btn').style.display = 'none';
}

function register() {
    const serialNos = $('#register-serial-nos').val();
    if (serialNos.length == 0) {
        alert("serial no is required.");
        return true;
    }

    const data = {
        "serialNos": serialNos.split('\n').map(line => line.trim()),
    };

    $.ajax({
        type: 'PUT',
        url: "/admin/products/register/serial-no",
        dataType: 'json',
        data: JSON.stringify(data),
        contentType: 'application/json; charset=utf-8'
    }).done(function (response) {
        console.log(response);
        if (response.success) {
            console.log(response.data.registerResult);
            return;
        }
        alert('Failed to activate S/N. Please try again.');
    }).fail(function (error) {
        const responseJson = error.responseJSON;
        alert("Failed to register serial no. Please contact the administrator.\n(" + responseJson.message + ")");
    });
}