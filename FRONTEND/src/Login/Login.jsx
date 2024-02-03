import './Login.css'

export default function Login({setUserDetails}) {   

  const loginToApp = () => {
    setUserDetails(() => {
      return { user_name: document.getElementById("emailid"), password: document.getElementById("pwd") };
    });
  };

  return (
    <>
      <div className="block">
      <form className="form" >
       <p className="form-title">Sign in to your account</p>
        <div className="input-container">
          <input type="email" id="emailid" placeholder="Enter email"/>
          <span>
          </span>
      </div>
      <div className="input-container">
          <input type="password" id="pwd" placeholder="Enter password"/>
        </div>
         <button type="button" className="submit" onClick={loginToApp}>
        Sign in
      </button>

      <p className="signup-link">
        No account?
        <button>Sign up</button>
      </p>
   </form>
      </div>
    </>
  );
}
