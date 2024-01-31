import Card from "../UI/Card";
import { endpoints } from "./AllEndpointsList";
import classes from "./AccessibleEndpoints.module.css";
import { useState } from "react";
import GetResponse from "../GetResponse/GetResponse";

export default function AccessibleEndpoint() {
  const [cardValue, setCardValue] = useState();
  const accessEndpoint = (endpoint) => {
    console.log(GetResponse(endpoint))
  };

  return (
    <>
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
    </>
  );
}
