import Card from "../UI/Card";
import { endpoints } from "./AllEndpointsList";
import classes from "./AccessibleEndpoints.module.css";
import { useState } from "react";
import GetResponse from "../GetResponse/GetResponse";
import Login from "../Login/Login";

export default function AccessibleEndpoint() {
  const [userDetails, setUserDetails] = useState({ login: "Not Logged in" }); //LOGIN STATUS. BASICALLY IF THE USER HAS SUCCEEDED THE ENDPOINT IN LOGIN CHECK
  const [cardValue, setCardValue] = useState();
  const accessEndpoint = (endpoint, secure) => {
    GetResponse(endpoint, secure, setCardValue, userDetails);
  };
  return (
    <>
      {userDetails.user_name != null ? (
        <p
          style={{
            fontFamily: "monospace",
            fontSize: "xx-large",
            display: "flex",
            justifyContent : "center",
            fontStyle: "italic",
          }}
        >
          Logged in as : {userDetails.user_name}
        </p>
      ) : (
        <p
          style={{
            fontFamily: "monospace",
            fontSize: "xx-large",
            display: "flex",
            justifyContent : "center",
            fontStyle: "italic",
          }}
        >
          {userDetails.login}
        </p>
      )}
      {cardValue !== "LOGIN REQUIRED" ? ( // OPEN LOGIN PAGE IF GET RESPONSE RETURNS LOGIN REQUIRED, OTHERWISE CARD VALUE HAS RESPONSE DATA
        <div className={classes.block}>
          <Card>{cardValue}</Card>
          {endpoints.map((endpoint) => (
            <button
              key={endpoint.id}
              onClick={() => accessEndpoint(endpoint.value, endpoint.secure)}
            >
              {endpoint.value}
              <div className={classes.arrow_wrapper}>
                <div className={classes.arrow}></div>
              </div>
            </button>
          ))}
        </div>
      ) : (
        <Login setUserDetails={setUserDetails} setCardValue={setCardValue} />
      )}
    </>
  );
}
