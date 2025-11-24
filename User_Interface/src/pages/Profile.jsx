import './Profile.css'

function Profile() {
  return (
    <div className="profile-page">
      <div className="profile-container">
        {/* Left Sidebar Navigation */}
        <aside className="profile-sidebar">
          <nav className="profile-nav">
            <button className="nav-item active">Personal</button>
            <button className="nav-item">Education</button>
            <button className="nav-item">Interests</button>
            <button className="nav-item">Contact</button>
            <button className="nav-item">Roommate</button>
            <button className="nav-item">Housing</button>
          </nav>
        </aside>

        {/* Right Content Area */}
        <main className="profile-content">
          <h1>My Roommate Profile</h1>
          
          <div className="profile-section">
            <h2>About Me</h2>
            
            <div className="form-actions">
              <button className="btn-save">Save</button>
            </div>

            <div className="form-group">
                <label htmlFor="name">Name *</label>
                <input 
                  type="text" 
                  id="name" 
                  placeholder="Your name or online handle, like your first name and last initial."
                />
            </div>

            <div className="form-group">
              <label htmlFor="age">Age *</label>
                <input 
                  type="number" 
                  id="age" 
                  placeholder="Enter your age"
              />
            </div>

            <div className="form-group">
              <label>Gender Identity *</label>
                <div className="checkbox-list">
                  <label><input type="checkbox" /> Male</label>
                  <label><input type="checkbox" /> Female</label>
                  <label><input type="checkbox" /> Other</label>
                </div>
                <input 
                  type="text" 
                  placeholder="If other, please specify"
                  style={{ marginTop: '8px' }}
              />
            </div>

            <div className="form-group">
              <label htmlFor="description">Describe yourself</label>
                <textarea 
                  id="description" 
                  rows="6"
                  placeholder="Tell potential roommates about yourself..."
              ></textarea>
            </div>
          </div>
        </main>
      </div>
    </div>
  )
}

export default Profile
