import axios from "axios";

export default async function GetResponse(
  endpoint,
  secure,
  setCardValue,
  userDetails
) {
  const username = userDetails.user_name;
  const password = userDetails.password;

  const credentials = btoa(`${username}:${password}`);
  const apiEndpoint = "http://localhost:8080" + endpoint;
  let headers = {}

  // THIS IS NECESSARY SO THAT AUTH HEADERS ARE NOT SENT FOR INSECURE ENDPOINTS
  // IF THE HEADERS ARE SENT BACKEND WILL TRY TO VALIDATE THESE AUTH HEADERS EVEN FOR PERMIT ALL ENDPOINTS
  if (secure === "true") {
    headers = {
      Authorization: `Basic ${credentials}`,
      "Content-Type": "application/json",
    };
  }

  console.log(endpoint);
  const response = await axios
    .get(apiEndpoint, {
      headers: headers,
    })
    .catch((error) => {
      console.log(error.message);
    });
  if (response != null && response.status === 200) {
    console.log(response.data);
    setCardValue(response.data);
  } else setCardValue("LOGIN REQUIRED");
}
