import axios from "axios"

export default async function GetResponse(endpoint){
    console.log(endpoint)
    await axios.get("http://localhost:8080"+endpoint).then((response) => {
        return(response.data)
    })
}