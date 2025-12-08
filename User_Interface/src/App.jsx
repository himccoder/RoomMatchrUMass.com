import { Routes, Route, Navigate } from 'react-router-dom'
import { useAuth } from './context/AuthContext'
import AppLayout from './layout/AppLayout'
import Login from './pages/Login'
import Signup from './pages/Signup'
import Dashboard from './pages/Dashboard'
import Browse from './pages/Browse'
import Profile from './pages/Profile'
import AccountSettings from './pages/AccountSettings'
import Chat from './pages/Chat'

// Protected Route component
function ProtectedRoute({ children }) {
  const { isAuthenticated, loading } = useAuth()
  
  if (loading) {
    return <div className="loading">Loading...</div>
  }
  
  if (!isAuthenticated) {
    return <Navigate to="/login" replace />
  }
  
  return children
}

function App() {
  const { isAuthenticated } = useAuth()

  return (
    <Routes>
      {/* Redirect root based on auth status */}
      <Route 
        path="/" 
        element={<Navigate to={isAuthenticated ? "/dashboard" : "/login"} replace />} 
      />
      
      {/* Public routes (no layout) */}
      <Route path="/login" element={<Login />} />
      <Route path="/signup" element={<Signup />} />
      
      {/* Protected routes (with AppLayout) */}
      <Route element={
        <ProtectedRoute>
          <AppLayout />
        </ProtectedRoute>
      }>
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/browse" element={<Browse />} />
        <Route path="/profile" element={<Profile />} />
        <Route path="/account-settings" element={<AccountSettings />} />
        <Route path="/chat" element={<Chat />} />
        <Route path="/chat/:partnerId" element={<Chat />} />
      </Route>
    </Routes>
  )
}

export default App
