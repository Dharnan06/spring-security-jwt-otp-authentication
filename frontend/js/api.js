const BASE_URL = "http://localhost:8080";

async function postRequest(endpoint, body) {

    const response = await fetch(BASE_URL + endpoint, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(body)
    });

    const contentType = response.headers.get("content-type");

    let data;

    if (contentType && contentType.includes("application/json")) {
        data = await response.json();
    } else {
        data = await response.text();
    }

    return {
        ok: response.ok,
        status: response.status,
        data: data
    };
}