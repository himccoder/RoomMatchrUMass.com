import { useState, useEffect } from 'react'
import { useAuth } from '../context/AuthContext'
import { profileAPI } from '../services/api'
import RoommateCard from '../components/RoommateCard'
import FilterPanel from '../components/FilterPanel'
import './Browse.css'

function Browse() {
  const { user } = useAuth()
  const [profiles, setProfiles] = useState([])
  const [filteredProfiles, setFilteredProfiles] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const [filters, setFilters] = useState({
    gender: '',
    major: '',
    minAge: '',
    maxAge: '',
    graduationYear: '',
    sleepSchedule: '',
    cleanlinessLevel: '',
    noisePreference: '',
    smokingAllowed: '',
    petsAllowed: '',
    guestsAllowed: '',
    budgetMin: '',
    budgetMax: '',
    minMatchPercent: '',
  })

  useEffect(() => {
    async function fetchProfiles() {
      if (!user?.id) return
      
      try {
        setLoading(true)
        const data = await profileAPI.browseProfiles(user.id)
        setProfiles(data)
        setFilteredProfiles(data)
      } catch (err) {
        setError('Failed to load profiles. Make sure the backend is running.')
        console.error(err)
      } finally {
        setLoading(false)
      }
    }
    fetchProfiles()
  }, [user?.id])

  // Apply filters whenever filters or profiles change
  useEffect(() => {
    let result = [...profiles]

    // Basic filters
    if (filters.gender) {
      result = result.filter(p => p.gender?.toLowerCase() === filters.gender.toLowerCase())
    }
    if (filters.major) {
      result = result.filter(p => 
        p.major?.toLowerCase().includes(filters.major.toLowerCase())
      )
    }
    if (filters.minAge) {
      result = result.filter(p => p.age >= parseInt(filters.minAge))
    }
    if (filters.maxAge) {
      result = result.filter(p => p.age <= parseInt(filters.maxAge))
    }
    if (filters.graduationYear) {
      result = result.filter(p => p.graduationYear === filters.graduationYear)
    }

    // Lifestyle filters
    if (filters.sleepSchedule) {
      result = result.filter(p => 
        p.sleepSchedule === filters.sleepSchedule || 
        p.sleepSchedule === 'flexible' || 
        filters.sleepSchedule === 'flexible'
      )
    }
    if (filters.cleanlinessLevel) {
      result = result.filter(p => p.cleanlinessLevel === filters.cleanlinessLevel)
    }
    if (filters.noisePreference) {
      result = result.filter(p => p.noisePreference === filters.noisePreference)
    }

    // Boolean preference filters (smoking, pets, guests)
    if (filters.smokingAllowed === 'true') {
      result = result.filter(p => p.smokingAllowed === true)
    } else if (filters.smokingAllowed === 'false') {
      result = result.filter(p => p.smokingAllowed === false || p.smokingAllowed == null)
    }

    if (filters.petsAllowed === 'true') {
      result = result.filter(p => p.petsAllowed === true)
    } else if (filters.petsAllowed === 'false') {
      result = result.filter(p => p.petsAllowed === false || p.petsAllowed == null)
    }

    if (filters.guestsAllowed === 'true') {
      result = result.filter(p => p.guestsAllowed === true)
    } else if (filters.guestsAllowed === 'false') {
      result = result.filter(p => p.guestsAllowed === false || p.guestsAllowed == null)
    }

    // Budget overlap filter
    if (filters.budgetMin || filters.budgetMax) {
      result = result.filter(p => {
        const filterMin = filters.budgetMin ? parseFloat(filters.budgetMin) : 0
        const filterMax = filters.budgetMax ? parseFloat(filters.budgetMax) : Infinity
        const profileMin = p.budgetMin || 0
        const profileMax = p.budgetMax || Infinity
        // Check for overlap
        return profileMin <= filterMax && filterMin <= profileMax
      })
    }

    // Match percentage filter
    if (filters.minMatchPercent) {
      result = result.filter(p => p.matchPercent >= parseInt(filters.minMatchPercent))
    }

    setFilteredProfiles(result)
  }, [filters, profiles])

  const handleFilterChange = (newFilters) => {
    setFilters(prev => ({ ...prev, ...newFilters }))
  }

  const clearFilters = () => {
    setFilters({
      gender: '',
      major: '',
      minAge: '',
      maxAge: '',
      graduationYear: '',
      sleepSchedule: '',
      cleanlinessLevel: '',
      noisePreference: '',
      smokingAllowed: '',
      petsAllowed: '',
      guestsAllowed: '',
      budgetMin: '',
      budgetMax: '',
      minMatchPercent: '',
    })
  }

  if (loading) {
    return <div className="browse-page loading-state">Loading profiles...</div>
  }

  if (error) {
    return (
      <div className="browse-page error-state">
        <p>{error}</p>
        <button onClick={() => window.location.reload()}>Retry</button>
      </div>
    )
  }

  return (
    <div className="browse-page">
      <div className="browse-container">
        {/* Left - Filters */}
        <aside className="browse-sidebar">
          <FilterPanel 
            filters={filters} 
            onFilterChange={handleFilterChange}
            onClearFilters={clearFilters}
          />
        </aside>

        {/* Right - Cards Grid */}
        <main className="browse-content">
          <div className="browse-header">
            <h1>Browse Roommates</h1>
            <p className="results-count">
              Showing {filteredProfiles.length} of {profiles.length} results
            </p>
          </div>

          {filteredProfiles.length === 0 ? (
            <div className="empty-state">
              {profiles.length === 0 ? (
                <p>No roommates found yet. Be the first to complete your profile!</p>
              ) : (
                <>
                  <p>No profiles match your filters.</p>
                  <button onClick={clearFilters} className="btn-secondary">
                    Clear Filters
                  </button>
                </>
              )}
            </div>
          ) : (
            <div className="browse-cards-grid">
              {filteredProfiles.map(roommate => (
                <RoommateCard key={roommate.id} roommate={roommate} />
              ))}
            </div>
          )}
        </main>
      </div>
    </div>
  )
}

export default Browse
