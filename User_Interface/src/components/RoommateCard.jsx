import { useNavigate } from 'react-router-dom'
import './RoommateCard.css'

function RoommateCard({ roommate }) {
  const navigate = useNavigate()

  const handleMessage = () => {
    navigate('/chat')
  }

  return (
    <div className="roommate-card">
      <div className="card-avatar">
        <div className="avatar-placeholder">{roommate.avatar}</div>
      </div>
      
      <div className="card-content">
        <div className="card-header">
          <h3 className="card-name">{roommate.name}</h3>
          <button className="favorite-btn">☆</button>
        </div>
        
        <div className="card-info">
          <span className="info-item">{roommate.age} years old</span>
          <span className="info-separator">•</span>
          <span className="info-item">{roommate.major}</span>
        </div>
        
        <div className="match-percent">
          <div className="match-bar">
            <div 
              className="match-fill" 
              style={{ width: `${roommate.matchPercent}%` }}
            ></div>
          </div>
          <span className="match-text">{roommate.matchPercent}% Match</span>
        </div>
        
        <p className="card-description">{roommate.description}</p>
        
        <div className="card-actions">
          <button className="view-profile-btn">View Profile</button>
          <button className="message-btn" onClick={handleMessage}>Message</button>
        </div>
      </div>
    </div>
  )
}

export default RoommateCard
