function saveToken(token){
    localStorage.setItem("token", token);
}

function getToken(){
    return localStorage.getItem("token");
}

function logout(){

    localStorage.removeItem("token");

    window.location.reload();
}

function isLoggedIn(){

    return getToken() !== null;
}