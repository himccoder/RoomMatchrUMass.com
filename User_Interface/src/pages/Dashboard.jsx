import { Link } from 'react-router-dom'
import RoommateCard from '../components/RoommateCard'
import { getRecommendedRoommates, getBrowseRoommates } from '../data/mockRoommates'
import './Dashboard.css'

function Dashboard() {
  const recommendedRoommates = getRecommendedRoommates(2)
  const browseRoommates = getBrowseRoommates(2)

  return (
    <div className="dashboard">
      {/* Browse Section - Main, Top */}
      <section className="browse-section">
        <div className="section-header">
          <h2>Browse Roommates</h2>
          <Link to="/browse" className="view-all-link">View All →</Link>
        </div>
        <div className="browse-grid">
          {browseRoommates.map(roommate => (
            <RoommateCard key={roommate.id} roommate={roommate} />
          ))}
        </div>
      </section>

      {/* Recommendations Section - Smaller, Below */}
      <section className="recommendations-section">
        <div className="section-header">
          <h2>Recommended Roommates</h2>
        </div>
        <div className="recommendations-grid">
          {recommendedRoommates.map(roommate => (
            <RoommateCard key={roommate.id} roommate={roommate} />
          ))}
        </div>
      </section>
    </div>
  )
}

export default Dashboard
