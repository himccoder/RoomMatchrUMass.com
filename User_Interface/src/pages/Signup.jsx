import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import './Auth.css'

function Signup() {
  const navigate = useNavigate()
  const [name, setName] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [confirmPassword, setConfirmPassword] = useState('')
  const [msg, setMsg] = useState('')

  const handleSubmit = async (e) => {
    e.preventDefault()
    if (password !== confirmPassword) { setMsg('Passwords do not match'); return }
    try 
    {
      const res = await fetch('http://localhost:8080/api/auth/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name, email, password })
      })
      const data = await res.json()
      setMsg(data.message)
      if (data.success) setTimeout(() => navigate('/login'), 1500)
    } catch 
  { 
    setMsg('Unable to process request and create user') }
  }

  return (
    <div className="auth-page">
      <div className="auth-container">
        <div className="auth-box">
          {/* Landing Page Title */}
          <h2>Welcome to UMass Roommate Matcher</h2>
          <h3>Sign Up</h3>
          {msg && <p style={{ color: msg.includes('Sign Up Successful') ? 'green' : 'red' }}>{msg}</p>}
          
          {/* Signup Questionnaire*/}
          <form onSubmit={handleSubmit}>
            {/* Name */}
            <div className="form-group">
              <label htmlFor="name">Full Name</label>
              <input type="text" id="name" placeholder="Enter your full name" 
              value={name} onChange={e => setName(e.target.value)} required />
            </div>

            {/* Email */}
            <div className="form-group">
              <label htmlFor="email">Email</label>
              <input type="email" id="email" placeholder="Enter your UMass email" 
              value={email} onChange={e => setEmail(e.target.value)} required />
            </div>

            {/* Password */}
            <div className="form-group">
              <label htmlFor="password">Password</label>
              <input type="password" id="password" placeholder="Enter your password" 
              value={password} onChange={e => setPassword(e.target.value)} required />
            </div>

            {/* Confirm Password */}
            <div className="form-group">
              <label htmlFor="confirmPassword">Confirm Password</label>
              <input type="password" id="confirmPassword" placeholder="Confirm your password" 
              value={confirmPassword} onChange={e => setConfirmPassword(e.target.value)} required />
            </div>
            
            {/* Submit Button */}
            <button type="submit" className="auth-btn">Sign Up</button>
          </form>

          <div className="auth-footer">
            <p>Already have an account? <Link to="/login">Login</Link></p>
          </div>
        </div>
      </div>
    </div>
  )
}

export default Signup
