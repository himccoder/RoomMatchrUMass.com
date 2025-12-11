import { useNavigate } from "react-router-dom";
import "./RoommateCard.css";

function RoommateCard({ roommate }) {
  const navigate = useNavigate();

  const handleMessage = () => {
    navigate("/chat");
  };

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

        <p className="card-description">{roommate.description}</p>

        <div className="card-actions">
          <button className="view-profile-btn">View Profile</button>
          <button className="message-btn" onClick={handleMessage}>
            Message
          </button>
        </div>
      </div>
    </div>
  );
}

export default RoommateCard;
