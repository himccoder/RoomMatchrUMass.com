// API Service Layer - Connects React frontend to Spring Boot backend

const API_BASE_URL = 'http://localhost:8080/api';

// Get token from localStorage
const getToken = () => localStorage.getItem('token');

// Helper function for making API calls
async function apiCall(endpoint, options = {}) {
  const url = `${API_BASE_URL}${endpoint}`;
  const token = getToken();
  
  const defaultOptions = {
    headers: {
      'Content-Type': 'application/json',
      ...(token && { 'Authorization': `Bearer ${token}` }), // Include JWT if available
    },
  };

  const response = await fetch(url, { 
    ...defaultOptions, 
    ...options,
    headers: {
      ...defaultOptions.headers,
      ...options.headers,
    }
  });
  
  // Handle 401 Unauthorized - token expired or invalid
  if (response.status === 401) {
    // Clear stored auth data
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    // Optionally redirect to login
    if (window.location.pathname !== '/login' && window.location.pathname !== '/signup') {
      window.location.href = '/login';
    }
    throw new Error('Session expired. Please login again.');
  }
  
  // Handle non-JSON responses
  const contentType = response.headers.get('content-type');
  if (contentType && contentType.includes('application/json')) {
    const data = await response.json();
    if (!response.ok) {
      throw new Error(data.message || 'API request failed');
    }
    return data;
  }
  
  if (!response.ok) {
    throw new Error('API request failed');
  }
  
  return response;
}

// ============ AUTH APIs ============

export const authAPI = {
  register: async (name, email, password) => {
    return apiCall('/auth/register', {
      method: 'POST',
      body: JSON.stringify({ name, email, password }),
    });
  },

  login: async (email, password) => {
    const response = await apiCall('/auth/login', {
      method: 'POST',
      body: JSON.stringify({ email, password }),
    });
    
    // Store token if login successful
    if (response.success && response.token) {
      localStorage.setItem('token', response.token);
    }
    
    return response;
  },
  
  logout: () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
  },
};

// ============ PROFILE APIs ============

export const profileAPI = {
  getProfile: async (userId) => {
    return apiCall(`/profiles/${userId}`);
  },

  updateProfile: async (userId, profileData) => {
    return apiCall(`/profiles/${userId}`, {
      method: 'PUT',
      body: JSON.stringify(profileData),
    });
  },

  browseProfiles: async (currentUserId) => {
    return apiCall(`/profiles/browse/${currentUserId}`);
  },
};

// ============ ROOMMATE REQUEST APIs ============

export const requestAPI = {
  sendRequest: async (senderId, receiverId, message = '') => {
    return apiCall(`/requests/send/${senderId}`, {
      method: 'POST',
      body: JSON.stringify({ receiverId, message }),
    });
  },

  getReceivedRequests: async (userId) => {
    return apiCall(`/requests/received/${userId}`);
  },

  getSentRequests: async (userId) => {
    return apiCall(`/requests/sent/${userId}`);
  },

  acceptRequest: async (requestId, userId) => {
    return apiCall(`/requests/accept/${requestId}/${userId}`, {
      method: 'POST',
    });
  },

  rejectRequest: async (requestId, userId) => {
    return apiCall(`/requests/reject/${requestId}/${userId}`, {
      method: 'POST',
    });
  },

  cancelRequest: async (requestId, userId) => {
    return apiCall(`/requests/cancel/${requestId}/${userId}`, {
      method: 'DELETE',
    });
  },
};

// ============ MESSAGE APIs ============

export const messageAPI = {
  sendMessage: async (senderId, receiverId, content) => {
    return apiCall(`/messages/send/${senderId}`, {
      method: 'POST',
      body: JSON.stringify({ receiverId, content }),
    });
  },

  getConversation: async (userId, otherUserId) => {
    return apiCall(`/messages/conversation/${userId}/${otherUserId}`);
  },

  getConversations: async (userId) => {
    return apiCall(`/messages/conversations/${userId}`);
  },

  markAsRead: async (userId, senderId) => {
    return apiCall(`/messages/read/${userId}/${senderId}`, {
      method: 'POST',
    });
  },

  getUnreadCount: async (userId) => {
    return apiCall(`/messages/unread/${userId}`);
  },
};

export default {
  auth: authAPI,
  profile: profileAPI,
  request: requestAPI,
  message: messageAPI,
};
