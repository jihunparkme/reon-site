function searchMemberList(page) {
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
    document.getElementById('name').value = null;
    document.getElementById('email').value = null;
    document.getElementById('serialNo').value = null;
}