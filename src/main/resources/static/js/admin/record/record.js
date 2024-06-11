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
    document.getElementById('serialNo').value = null;
    document.getElementById('email').value = null;
    document.getElementById('startDate').value = null;
    document.getElementById('endDate').value = null;
}