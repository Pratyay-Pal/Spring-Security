import axios from "axios";

export default async function GetResponse(endpoint, setCardValue, userDetails) {
  const username = userDetails.user_name;
  const password = userDetails.password;

  const credentials = btoa(`${username}:${password}`);

  const apiEndpoint = "http://localhost:8080" + endpoint;

  console.log(endpoint);
  const response = await axios
    .get(apiEndpoint, {
      headers: {
        Authorization: `Basic ${credentials}`,
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin":  apiEndpoint
      },
    })
    .catch((error) => {
      console.log(error.message);
    });
  if (response != null && response.status === 200) setCardValue(response.data);
  else setCardValue("LOGIN REQUIRED");
}
