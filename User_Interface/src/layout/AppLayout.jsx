import { Link, Outlet, useNavigate } from 'react-router-dom'
import { useState, useEffect } from 'react'
import { useAuth } from '../context/AuthContext'
import { messageAPI } from '../services/api'
import './AppLayout.css'

function AppLayout() {
  const [showUserMenu, setShowUserMenu] = useState(false)
  const [unreadCount, setUnreadCount] = useState(0)
  const navigate = useNavigate()
  const { user, logout } = useAuth()

  // Fetch unread message count
  useEffect(() => {
    async function fetchUnreadCount() {
      if (user?.id) {
        try {
          const count = await messageAPI.getUnreadCount(user.id)
          setUnreadCount(count)
        } catch (error) {
          console.error('Failed to fetch unread count:', error)
        }
      }
    }
    fetchUnreadCount()
    // Poll every 30 seconds for new messages
    const interval = setInterval(fetchUnreadCount, 30000)
    return () => clearInterval(interval)
  }, [user?.id])

  const handleSignOut = () => {
    logout()
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
          <Link to="/chat" className="nav-link">
            Chat
            {unreadCount > 0 && <span className="unread-badge">{unreadCount}</span>}
          </Link>
        </nav>

        <div className="header-right">
          <div className="user-menu-container">
            <button 
              className="user-menu-button"
              onClick={() => setShowUserMenu(!showUserMenu)}
            >
              <span className="user-icon">👤</span>
              <span className="user-name">{user?.name || 'User'}</span>
              <span className="dropdown-arrow">▼</span>
            </button>
            
            {showUserMenu && (
              <div className="user-dropdown">
                <div className="dropdown-header">
                  <strong>{user?.name}</strong>
                  <small>{user?.email}</small>
                </div>
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
                  className="dropdown-item logout-btn"
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
