import './Login.css'

export default function Login({newUserRegistration, loginToApp}) {   

  return (
    <>
      <div className="block">
      <form className="form" >
       <p className="form-title">Sign in to your account</p>
        <div className="input-container">
          <input type="email" placeholder="Enter email"/>
          <span>
          </span>
      </div>
      <div className="input-container">
          <input type="password" placeholder="Enter password"/>
        </div>
         <button type="button" className="submit" onClick={loginToApp}>
        Sign in
      </button>

      <p className="signup-link">
        No account?
        <button onClick={newUserRegistration}>Sign up</button>
      </p>
   </form>
      </div>
    </>
  );
}
