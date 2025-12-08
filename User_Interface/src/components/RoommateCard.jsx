import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'
import { requestAPI } from '../services/api'
import './RoommateCard.css'

function RoommateCard({ roommate }) {
  const navigate = useNavigate()
  const { user } = useAuth()
  const [requestSent, setRequestSent] = useState(false)
  const [loading, setLoading] = useState(false)

  const handleMessage = () => {
    navigate(`/chat/${roommate.id}`)
  }

  const handleRequest = async () => {
    if (!user?.id || loading) return
    
    try {
      setLoading(true)
      await requestAPI.sendRequest(user.id, roommate.id, '')
      setRequestSent(true)
    } catch (err) {
      // Check if request already exists
      if (err.message?.includes('already exists')) {
        setRequestSent(true)
      } else {
        console.error('Failed to send request:', err)
        alert('Failed to send request. Please try again.')
      }
    } finally {
      setLoading(false)
    }
  }

  // Generate initials for avatar
  const getInitials = (name) => {
    if (!name) return '?'
    return name.split(' ').map(n => n[0]).join('').toUpperCase().slice(0, 2)
  }

  return (
    <div className="roommate-card">
      <div className="card-avatar">
        <div className="avatar-placeholder">
          {roommate.avatar || getInitials(roommate.name)}
        </div>
      </div>
      
      <div className="card-content">
        <div className="card-header">
          <h3 className="card-name">{roommate.name || 'Anonymous'}</h3>
        </div>
        
        <div className="card-info">
          {roommate.age && <span className="info-item">{roommate.age} years old</span>}
          {roommate.age && roommate.major && <span className="info-separator">•</span>}
          {roommate.major && <span className="info-item">{roommate.major}</span>}
        </div>
        
        {roommate.matchPercent !== undefined && (
          <div className="match-percent">
            <div className="match-bar">
              <div 
                className="match-fill" 
                style={{ width: `${roommate.matchPercent}%` }}
              ></div>
            </div>
            <span className="match-text">{roommate.matchPercent}% Match</span>
          </div>
        )}
        
        {roommate.bio && (
          <p className="card-description">
            {roommate.bio.length > 100 ? `${roommate.bio.slice(0, 100)}...` : roommate.bio}
          </p>
        )}

        {/* Preferences Tags */}
        <div className="card-tags">
          {roommate.sleepSchedule && (
            <span className="tag">
              {roommate.sleepSchedule === 'early_bird' ? '🌅 Early Bird' : 
               roommate.sleepSchedule === 'night_owl' ? '🦉 Night Owl' : '⏰ Flexible'}
            </span>
          )}
          {roommate.cleanlinessLevel && (
            <span className="tag">
              {roommate.cleanlinessLevel === 'very_clean' ? '✨ Very Clean' : 
               roommate.cleanlinessLevel === 'moderate' ? '🧹 Moderate' : '😌 Relaxed'}
            </span>
          )}
          {roommate.noisePreference && (
            <span className="tag">
              {roommate.noisePreference === 'quiet' ? '🤫 Quiet' : 
               roommate.noisePreference === 'moderate' ? '🔊 Moderate' : '🎉 Social'}
            </span>
          )}
        </div>
        
        {/* Secondary Tags - Deal Breakers */}
        <div className="card-tags secondary">
          {roommate.smokingAllowed === true && <span className="tag warning">🚬 Smoker OK</span>}
          {roommate.smokingAllowed === false && <span className="tag good">🚭 No Smoking</span>}
          {roommate.petsAllowed === true && <span className="tag">🐾 Pet Friendly</span>}
          {roommate.guestsAllowed === true && <span className="tag">👥 Guests OK</span>}
        </div>

        {/* Budget & Graduation */}
        {(roommate.budgetMin || roommate.budgetMax || roommate.graduationYear) && (
          <div className="card-details">
            {(roommate.budgetMin || roommate.budgetMax) && (
              <span className="detail-item">
                💰 ${roommate.budgetMin || '?'} - ${roommate.budgetMax || '?'}/mo
              </span>
            )}
            {roommate.graduationYear && (
              <span className="detail-item">🎓 Class of {roommate.graduationYear}</span>
            )}
          </div>
        )}
        
        <div className="card-actions">
          <button 
            className={`request-btn ${requestSent ? 'sent' : ''}`}
            onClick={handleRequest}
            disabled={requestSent || loading}
          >
            {loading ? 'Sending...' : requestSent ? '✓ Request Sent' : '🤝 Request'}
          </button>
          <button className="message-btn" onClick={handleMessage}>
            💬 Chat
          </button>
        </div>
      </div>
    </div>
  )
}

export default RoommateCard
