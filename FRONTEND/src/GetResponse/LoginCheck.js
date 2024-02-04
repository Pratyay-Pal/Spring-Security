import axios from "axios";

export default async function LoginCheck(username, password) {
  const apiEndpoint = "http://localhost:8080/loginSuccess";

  const response = await axios
    .post(apiEndpoint, { username: username, password: password })
    .catch((error) => {
      console.log(error.message);
    });
  if (response != null && response.status === 200) return response.data;
  else return false;
}
