import { Routes, Route, Navigate } from 'react-router-dom'
import AppLayout from './layout/AppLayout'
import Login from './pages/Login'
import Signup from './pages/Signup'
import Dashboard from './pages/Dashboard'
import Browse from './pages/Browse'
import Profile from './pages/Profile'
import AccountSettings from './pages/AccountSettings'
import Chat from './pages/Chat'

function App() {
  return (
    <Routes>
      <Route path="/" element={<Navigate to="/login" replace />} />
      
      {/* Public routes (no layout) */}
      <Route path="/login" element={<Login />} />
      <Route path="/signup" element={<Signup />} />
      
      {/* Protected routes (with AppLayout) */}
      <Route element={<AppLayout />}>
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/browse" element={<Browse />} />
        <Route path="/profile" element={<Profile />} />
        <Route path="/account-settings" element={<AccountSettings />} />
        <Route path="/chat" element={<Chat />} />
      </Route>
    </Routes>
  )
}

export default App
