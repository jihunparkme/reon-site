function searchMemberList(page) {
    if (page == undefined) {
        page = 0;
    }

    $("#page").val(page);
    $("#form").submit();
}
