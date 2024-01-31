import axios from "axios"

export default function LoginRequest(){
    axios.get("http://localhost:8080/insecure").then((response) => {
        console.log(response)
    })
}