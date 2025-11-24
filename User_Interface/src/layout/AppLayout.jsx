import { Link, Outlet, useNavigate } from 'react-router-dom'
import { useState } from 'react'
import './AppLayout.css'

function AppLayout() {
  const [showUserMenu, setShowUserMenu] = useState(false)
  const navigate = useNavigate()

  const handleSignOut = () => {
    // TODO: Handle sign out logic
    navigate('/login')
  }

  return (
    <div className="app-layout">
      {/* Red Top Toolbar */}
      <header className="app-header">
        <div className="header-left">
          <div className="logo">
            <Link to="/dashboard">UMass Roommate Matcher</Link>
          </div>
        </div>

        <nav className="header-nav">
          <Link to="/dashboard" className="nav-link">Dashboard</Link>
          <Link to="/browse" className="nav-link">Browse</Link>
          <Link to="/chat" className="nav-link">Chat</Link>
        </nav>

        <div className="header-right">
          <div className="user-menu-container">
            <button 
              className="user-menu-button"
              onClick={() => setShowUserMenu(!showUserMenu)}
            >
              <span className="user-icon">👤</span>
              <span className="dropdown-arrow">▼</span>
            </button>
            
            {showUserMenu && (
              <div className="user-dropdown">
                <Link 
                  to="/account-settings" 
                  className="dropdown-item"
                  onClick={() => setShowUserMenu(false)}
                >
                  Account Settings
                </Link>
                <Link 
                  to="/profile" 
                  className="dropdown-item"
                  onClick={() => setShowUserMenu(false)}
                >
                  Roommate Profile
                </Link>
                <button 
                  className="dropdown-item"
                  onClick={handleSignOut}
                >
                  Sign Out
                </button>
              </div>
            )}
          </div>
        </div>
      </header>

      {/* Main Content Area */}
      <main className="app-main">
        <Outlet />
      </main>
    </div>
  )
}

export default AppLayout
