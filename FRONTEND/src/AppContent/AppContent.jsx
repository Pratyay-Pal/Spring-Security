import Login from "../Login/Login";
import AccessibleEndpoint from "../AccessibleEndpoint/AccessibleEndpoint";
import { useState } from "react";
import LoginRequest from "../GetResponse/LoginRequest";

export default function AppContent() {
  const [userDetails, setUserDetails] = useState({});
  const newUserRegistration = () => {};
  
  const loginToApp = () => {
    setUserDetails(() => {
        LoginRequest()
      return { user_name: "logged in" };
    });
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
      {userDetails.user_name == null ? (
        <Login
          newUserRegistration={newUserRegistration}
          loginToApp={loginToApp}
        />
      ) : (
        <AccessibleEndpoint />
      )}
    </>
  );
}
