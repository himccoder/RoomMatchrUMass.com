import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import "./Auth.css";

function Login() {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [msg, setMsg] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const res = await fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password }),
      });
      {/*added this  small logic to get userId on login
        othwerwise a user could see himself in teh recommened users section*/}
      {/*this helps in recommendations and survey*/}
      const data = await res.json()
      setMsg(data.message)
      if (data.success) {
        const userRes = await fetch('http://localhost:8080/api/users/getAllUsers', { method: 'POST' })
        const users = await userRes.json()
        {/* Find the logged-in user by email */}
        const user = users.find(u => u.email === email)
        if (user) localStorage.setItem('userId', user.id)
        setTimeout(() => navigate('/dashboard'), 500)
      }
    } catch {
      setMsg("Sorry. Unable to process request");
    }
  };

  return (
    <div className="auth-page">
      <div className="auth-container">
        <div className="auth-box">
          {/* Landing Page Title */}
          <h2>UMass Roommate Matcher</h2>
          <h3>Login</h3>
          {msg && (
            <p
              style={{
                color: msg.includes("Login Successful") ? "green" : "red",
              }}
            >
              {msg}
            </p>
          )}

          <form onSubmit={handleSubmit}>
            {/* Email */}
            <div className="form-group">
              <label htmlFor="email">Email</label>
              <input
                type="email"
                id="email"
                placeholder="Enter your UMass email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />
            </div>
            {/* Password */}
            <div className="form-group">
              <label htmlFor="password">Password</label>
              <input
                type="password"
                id="password"
                placeholder="Enter your password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
            </div>

            <button type="submit" className="auth-btn">
              Login
            </button>
          </form>

          <div className="auth-footer">
            <p>
              Don't have an account? Create one here{" "}
              <Link to="/signup">Sign up</Link>
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Login;
