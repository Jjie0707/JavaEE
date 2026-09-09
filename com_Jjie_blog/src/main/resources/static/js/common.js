$.ajaxSetup({
    beforeSend: function(xhr) {
        var token = localStorage.getItem("token");

        if (token) {
            xhr.setRequestHeader("Authorization", "Bearer " + token);
        }
    }
});
