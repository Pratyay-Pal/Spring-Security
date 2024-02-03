import Card from "../UI/Card";
import { endpoints } from "./AllEndpointsList";
import classes from "./AccessibleEndpoints.module.css";
import { useState } from "react";
import GetResponse from "../GetResponse/GetResponse";
import Login from "../Login/Login";

export default function AccessibleEndpoint() {
  const [userDetails, setUserDetails] = useState({});
  const [cardValue, setCardValue] = useState();
  const accessEndpoint = (endpoint) => {
    GetResponse(endpoint, setCardValue, userDetails);
  };

  return (
    <>
    {userDetails.user_name != null ? (
        <p
          style={{
            fontFamily: "monospace",
            fontSize: "large",
            marginLeft: "100px",
            fontStyle: "italic",
          }}
        >
          Logged in as : {userDetails.user_name}
        </p>
      ) : (
        <p
          style={{
            fontFamily: "monospace",
            fontSize: "large",
            marginLeft: "100px",
            fontStyle: "italic",
          }}
        >
          Not Logged in
        </p>
      )}
      {cardValue !== "LOGIN REQUIRED" ? (
        <div className={classes.block}>
          <Card>{cardValue}</Card>
          {endpoints.map((endpoint) => (
            <button
              key={endpoint.id}
              onClick={() => accessEndpoint(endpoint.value)}
            >
              {endpoint.value}
            </button>
          ))}
        </div>
      ) : (
        <Login setUserDetails={setUserDetails} />
      )}
    </>
  );
}
