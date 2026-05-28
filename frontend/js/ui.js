async function loadPage(page){

    const response = await fetch("pages/" + page);

    const html = await response.text();

    document.getElementById("app").innerHTML = html;

    if(page === "register.html"){

        document.getElementById("finalRegisterPhone").value =
            localStorage.getItem("registerPhone");
    }
}

window.onload = () => {

    if(isLoggedIn()){
        loadPage("dashboard.html");
    } else {
        loadPage("login.html");
    }
};
async function loginUser(){

    const phoneNumber =
        document.getElementById("loginPhone").value;

    const password =
        document.getElementById("loginPassword").value;

    const message =
        document.getElementById("loginMessage");

    const forgotPassword =
        document.getElementById("forgotPasswordLink");

    const response = await postRequest(
        "/auth/login",
        {
            phoneNumber,
            password
        }
    );

    if(response.ok){

        saveToken(response.data.token);

        message.style.color = "green";

        message.innerText =
            "Login Successful";

        setTimeout(() => {
            loadPage("dashboard.html");
        }, 1000);

    } else {

        message.style.color = "red";

        message.innerText =
            "Invalid phone number or password";

        forgotPassword.classList.remove("hidden");
    }
}
async function sendRegisterOtp(){

    const phoneNumber =
        document.getElementById("registerPhone").value;

    const message =
        document.getElementById("registerPhoneMessage");

    const response = await postRequest(
        "/auth/send-otp",
        {
            phoneNumber: phoneNumber,
            purpose: "REGISTRATION"
        }
    );

    if(response.ok){

        localStorage.setItem("registerPhone", phoneNumber);

        message.style.color = "green";
        message.innerText = response.data;

        setTimeout(() => {
            loadPage("verify-register-otp.html");
        }, 1000);

    } else {
        message.style.color = "red";
        message.innerText = "Unable to send OTP";
    }
}

async function verifyRegisterOtp(){

    const phoneNumber = localStorage.getItem("registerPhone");
    const otp = document.getElementById("registerOtp").value;
    const message = document.getElementById("verifyOtpMessage");

    const response = await postRequest(
        "/auth/verify-otp",
        {
            phoneNumber: phoneNumber,
            otp: otp,
            purpose: "REGISTRATION"
        }
    );

    if(response.ok && response.data === "OTP verified successfully"){

        message.style.color = "green";
        message.innerText = "OTP Verified";

        setTimeout(() => {
            loadPage("register.html");
        }, 1000);

    } else {
        message.style.color = "red";
        message.innerText = response.data;
    }
}

async function registerUser(){

    const fullName =
        document.getElementById("fullName").value;

    const phoneNumber =
        localStorage.getItem("registerPhone");

    const password =
        document.getElementById("registerPassword").value;

    const role =
        document.getElementById("role").value;

    const message =
        document.getElementById("registerMessage");

    const response = await postRequest(
        "/auth/register",
        {
            fullName,
            phoneNumber,
            password,
            role
        }
    );

    if(response.ok){

        message.style.color = "green";

        message.innerText =
            "Registration Successful";

        localStorage.removeItem("registerPhone");

        setTimeout(() => {
            loadPage("login.html");
        }, 1500);

    } else {

        message.style.color = "red";

        message.innerText = response.data;
    }
}

async function sendForgotPasswordOtp(){

    const phoneNumber =
        document.getElementById("forgotPhone").value;

    const message =
        document.getElementById("forgotMessage");

    const response = await postRequest(
        "/auth/send-otp",
        {
            phoneNumber: phoneNumber,
            purpose: "FORGOT_PASSWORD"
        }
    );

    if(response.ok){

        localStorage.setItem("forgotPhone", phoneNumber);

        message.style.color = "green";
        message.innerText = response.data;

        setTimeout(() => {
            loadPage("verify-forgot-otp.html");
        }, 1000);

    } else {
        message.style.color = "red";
        message.innerText = "Unable to send OTP";
    }
}

async function verifyForgotOtp(){

    const phoneNumber =
        localStorage.getItem("forgotPhone");

    const otp =
        document.getElementById("forgotOtp").value;

    const message =
        document.getElementById("verifyForgotMessage");

    const response = await postRequest(
        "/auth/verify-otp",
        {
            phoneNumber: phoneNumber,
            otp: otp,
            purpose: "FORGOT_PASSWORD"
        }
    );

    if(response.ok &&
       response.data === "OTP verified successfully"){

        message.style.color = "green";
        message.innerText = "OTP Verified";

        setTimeout(() => {
            loadPage("reset-password.html");
        }, 1000);

    } else {

        message.style.color = "red";
        message.innerText = response.data;
    }
}

async function resetPassword(){

    const phoneNumber =
        localStorage.getItem("forgotPhone");

    const newPassword =
        document.getElementById("newPassword").value;

    const message =
        document.getElementById("resetPasswordMessage");

    const response = await postRequest(
        "/auth/reset-password",
        {
            phoneNumber,
            newPassword
        }
    );

    if(response.ok){

        message.style.color = "green";

        message.innerText =
            "Password Reset Successful";

        localStorage.removeItem("forgotPhone");

        setTimeout(() => {
            loadPage("login.html");
        }, 1500);

    } else {

        message.style.color = "red";

        message.innerText = response.data;
    }
}
function openProfileModal(){

    const modal =
        document.getElementById("profileModal");

    modal.classList.remove("hidden");
}

function closeProfileModal(){

    const modal =
        document.getElementById("profileModal");

    modal.classList.add("hidden");
}

function openPhotoOptions(){

    const modal =
        document.getElementById("photoOptionsModal");

    modal.classList.remove("hidden");
}

function closePhotoOptions(){

    const modal =
        document.getElementById("photoOptionsModal");

    modal.classList.add("hidden");
}

function chooseFromGallery(){
    document.getElementById("galleryInput").click();
}

function takePhoto(){
    document.getElementById("cameraInput").click();
}

function previewSelectedImage(event){

    const file = event.target.files[0];

    if(file){

        const imageUrl =
            URL.createObjectURL(file);

        document.getElementById("modalProfileImage").src =
            imageUrl;

        document.getElementById("profileImage").src =
            imageUrl;

        closePhotoOptions();
    }
}