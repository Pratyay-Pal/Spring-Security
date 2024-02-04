import LoginCheck from "../GetResponse/LoginCheck";
import "./Login.css";

export default function Login({ setUserDetails,setCardValue }) {
  async function loginToApp() {
    if (
      await LoginCheck(
        document.getElementById("emailid").value,
        document.getElementById("pwd").value
      )
    ) {
      setUserDetails(() => {
        return {
          user_name: document.getElementById("emailid").value,
          password: document.getElementById("pwd").value,
          login: "SUCCESS",
        };
      });
      setCardValue(null);
    } else {
      setUserDetails(() => {
        return {
          login: "INCORRECT CREDENTIALS",
        };
      });
      document.getElementById("emailid").value = ""
      document.getElementById("pwd").value = ""
    }
  }

  return (
    <>
      <div className="block">
        <form className="form">
          <p className="form-title">Sign in to your account</p>
          <div className="input-container">
            <input type="email" id="emailid" placeholder="Enter email" />
            <span></span>
          </div>
          <div className="input-container">
            <input type="password" id="pwd" placeholder="Enter password" />
          </div>
          <button type="button" className="submit" onClick={loginToApp}>
            Sign in
          </button>
        </form>
      </div>
    </>
  );
}
