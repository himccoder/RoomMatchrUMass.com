import RoommateCard from '../components/RoommateCard'
import FilterPanel from '../components/FilterPanel'
import { mockRoommates } from '../data/mockRoommates'
import './Browse.css'

function Browse() {
  return (
    <div className="browse-page">
      <div className="browse-container">
        {/* Left - Filters */}
        <aside className="browse-sidebar">
          <FilterPanel />
        </aside>

        {/* Right - Cards Grid */}
        <main className="browse-content">
          <div className="browse-header">
            <h1>Browse Roommates</h1>
            <p className="results-count">Showing {mockRoommates.length} results</p>
          </div>

          <div className="browse-cards-grid">
            {mockRoommates.map(roommate => (
              <RoommateCard key={roommate.id} roommate={roommate} />
            ))}
          </div>
        </main>
      </div>
    </div>
  )
}

export default Browse
