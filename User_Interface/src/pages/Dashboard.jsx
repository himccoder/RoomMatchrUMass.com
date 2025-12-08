import { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'
import { profileAPI, requestAPI } from '../services/api'
import RoommateCard from '../components/RoommateCard'
import './Dashboard.css'

function Dashboard() {
  const { user } = useAuth()
  const [profiles, setProfiles] = useState([])
  const [receivedRequests, setReceivedRequests] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  useEffect(() => {
    async function fetchData() {
      if (!user?.id) return
      
      try {
        setLoading(true)
        // Fetch browse profiles and received requests in parallel
        const [profilesData, requestsData] = await Promise.all([
          profileAPI.browseProfiles(user.id),
          requestAPI.getReceivedRequests(user.id)
        ])
        setProfiles(profilesData)
        setReceivedRequests(requestsData)
      } catch (err) {
        setError('Failed to load data. Make sure the backend is running.')
        console.error(err)
      } finally {
        setLoading(false)
      }
    }
    fetchData()
  }, [user?.id])

  const handleAcceptRequest = async (requestId) => {
    try {
      await requestAPI.acceptRequest(requestId, user.id)
      setReceivedRequests(prev => prev.filter(r => r.id !== requestId))
    } catch (err) {
      console.error('Failed to accept request:', err)
    }
  }

  const handleRejectRequest = async (requestId) => {
    try {
      await requestAPI.rejectRequest(requestId, user.id)
      setReceivedRequests(prev => prev.filter(r => r.id !== requestId))
    } catch (err) {
      console.error('Failed to reject request:', err)
    }
  }

  if (loading) {
    return <div className="dashboard loading-state">Loading...</div>
  }

  if (error) {
    return (
      <div className="dashboard error-state">
        <p>{error}</p>
        <button onClick={() => window.location.reload()}>Retry</button>
      </div>
    )
  }

  // Get top recommended (sorted by match %)
  const recommendedRoommates = [...profiles]
    .sort((a, b) => (b.matchPercent || 0) - (a.matchPercent || 0))
    .slice(0, 4)

  return (
    <div className="dashboard">
      {/* Pending Requests Section */}
      {receivedRequests.length > 0 && (
        <section className="requests-section">
          <div className="section-header">
            <h2>🔔 Pending Roommate Requests</h2>
          </div>
          <div className="requests-list">
            {receivedRequests.map(request => (
              <div key={request.id} className="request-card">
                <div className="request-info">
                  <strong>{request.senderName}</strong>
                  <span>{request.senderMajor || 'UMass Student'}</span>
                  {request.message && <p className="request-message">"{request.message}"</p>}
                </div>
                <div className="request-actions">
                  <button 
                    className="btn-accept"
                    onClick={() => handleAcceptRequest(request.id)}
                  >
                    Accept
                  </button>
                  <button 
                    className="btn-reject"
                    onClick={() => handleRejectRequest(request.id)}
                  >
                    Decline
                  </button>
                </div>
              </div>
            ))}
          </div>
        </section>
      )}

      {/* Browse Section - Main, Top */}
      <section className="browse-section">
        <div className="section-header">
          <h2>Browse Roommates</h2>
          <Link to="/browse" className="view-all-link">View All →</Link>
        </div>
        {profiles.length === 0 ? (
          <div className="empty-state">
            <p>No roommates found yet. Be the first to complete your profile!</p>
            <Link to="/profile" className="btn-primary">Complete Profile</Link>
          </div>
        ) : (
          <div className="browse-grid">
            {profiles.slice(0, 4).map(roommate => (
              <RoommateCard key={roommate.id} roommate={roommate} />
            ))}
          </div>
        )}
      </section>

      {/* Recommendations Section - Smaller, Below */}
      {recommendedRoommates.length > 0 && (
        <section className="recommendations-section">
          <div className="section-header">
            <h2>⭐ Top Matches For You</h2>
          </div>
          <div className="recommendations-grid">
            {recommendedRoommates.map(roommate => (
              <RoommateCard key={roommate.id} roommate={roommate} />
            ))}
          </div>
        </section>
      )}
    </div>
  )
}

export default Dashboard
