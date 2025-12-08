import { createContext, useContext, useState, useEffect } from 'react';
import { authAPI } from '../services/api';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  // Check for existing session on mount
  useEffect(() => {
    const savedUser = localStorage.getItem('user');
    const savedToken = localStorage.getItem('token');
    
    if (savedUser && savedToken) {
      setUser(JSON.parse(savedUser));
    } else {
      // Clear both if one is missing
      localStorage.removeItem('user');
      localStorage.removeItem('token');
    }
    setLoading(false);
  }, []);

  // Login function
  const login = async (email, password) => {
    try {
      const response = await authAPI.login(email, password);
      if (response.success) {
        const userData = {
          id: response.userId,
          name: response.name,
          email: response.email,
        };
        setUser(userData);
        localStorage.setItem('user', JSON.stringify(userData));
        // Token is already stored in api.js login function
        return { success: true };
      }
      return { success: false, message: response.message };
    } catch (error) {
      return { success: false, message: error.message || 'Login failed' };
    }
  };

  // Register function
  const register = async (name, email, password) => {
    try {
      const response = await authAPI.register(name, email, password);
      if (response.success) {
        // Auto-login after registration
        return login(email, password);
      }
      return { success: false, message: response.message };
    } catch (error) {
      return { success: false, message: error.message || 'Registration failed' };
    }
  };

  // Logout function
  const logout = () => {
    setUser(null);
    authAPI.logout(); // This clears token and user from localStorage
  };

  // Update user data (after profile update)
  const updateUser = (userData) => {
    const updatedUser = { ...user, ...userData };
    setUser(updatedUser);
    localStorage.setItem('user', JSON.stringify(updatedUser));
  };

  const value = {
    user,
    loading,
    login,
    register,
    logout,
    updateUser,
    isAuthenticated: !!user && !!localStorage.getItem('token'),
  };

  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  );
}

// Custom hook to use auth context
export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
}

export default AuthContext;
