$.ajaxSetup({
    beforeSend: function(xhr) {
        var token = localStorage.getItem("token");

        if (token) {
            xhr.setRequestHeader("Authorization", "Bearer " + token);
        }
    }
});
function logout(){
    if (!window.confirm("确定要注销登录吗？")) {
        return false;
    }

    // Remove the credentials kept for the current session before leaving the
    // protected pages.  The user id is session data as well, so clear it too.
    localStorage.removeItem("token");
    localStorage.removeItem("id");

    // Return to the login page after the local session has been invalidated.
    window.location.assign("blog_login.html");
}
