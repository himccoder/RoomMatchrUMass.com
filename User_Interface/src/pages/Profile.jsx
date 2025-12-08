import { useState, useEffect } from 'react'
import { useAuth } from '../context/AuthContext'
import { profileAPI } from '../services/api'
import './Profile.css'

function Profile() {
  const { user, updateUser } = useAuth()
  const [loading, setLoading] = useState(true)
  const [saving, setSaving] = useState(false)
  const [message, setMessage] = useState({ type: '', text: '' })
  const [activeSection, setActiveSection] = useState('personal')
  
  const [profile, setProfile] = useState({
    name: '',
    age: '',
    gender: '',
    major: '',
    bio: '',
    graduationYear: '',
    sleepSchedule: '',
    cleanlinessLevel: '',
    noisePreference: '',
    smokingAllowed: false,
    petsAllowed: false,
    guestsAllowed: false,
    budgetMin: '',
    budgetMax: '',
    preferredLocation: '',
    moveInDate: '',
    lookingForRoommate: true,
  })

  // Load profile on mount
  useEffect(() => {
    async function loadProfile() {
      if (!user?.id) return
      
      try {
        const data = await profileAPI.getProfile(user.id)
        setProfile({
          name: data.name || '',
          age: data.age || '',
          gender: data.gender || '',
          major: data.major || '',
          bio: data.bio || '',
          graduationYear: data.graduationYear || '',
          sleepSchedule: data.sleepSchedule || '',
          cleanlinessLevel: data.cleanlinessLevel || '',
          noisePreference: data.noisePreference || '',
          smokingAllowed: data.smokingAllowed || false,
          petsAllowed: data.petsAllowed || false,
          guestsAllowed: data.guestsAllowed || false,
          budgetMin: data.budgetMin || '',
          budgetMax: data.budgetMax || '',
          preferredLocation: data.preferredLocation || '',
          moveInDate: data.moveInDate || '',
          lookingForRoommate: data.lookingForRoommate !== false,
        })
      } catch (err) {
        console.error('Failed to load profile:', err)
      } finally {
        setLoading(false)
      }
    }
    loadProfile()
  }, [user?.id])

  const handleChange = (field, value) => {
    setProfile(prev => ({ ...prev, [field]: value }))
  }

  const handleSave = async () => {
    try {
      setSaving(true)
      setMessage({ type: '', text: '' })
      
      await profileAPI.updateProfile(user.id, profile)
      updateUser({ name: profile.name })
      
      setMessage({ type: 'success', text: 'Profile saved successfully!' })
    } catch (err) {
      setMessage({ type: 'error', text: 'Failed to save profile. Please try again.' })
    } finally {
      setSaving(false)
    }
  }

  if (loading) {
    return <div className="profile-page loading">Loading profile...</div>
  }

  return (
    <div className="profile-page">
      <div className="profile-container">
        {/* Left Sidebar Navigation */}
        <aside className="profile-sidebar">
          <nav className="profile-nav">
            <button 
              className={`nav-item ${activeSection === 'personal' ? 'active' : ''}`}
              onClick={() => setActiveSection('personal')}
            >
              Personal
            </button>
            <button 
              className={`nav-item ${activeSection === 'education' ? 'active' : ''}`}
              onClick={() => setActiveSection('education')}
            >
              Education
            </button>
            <button 
              className={`nav-item ${activeSection === 'lifestyle' ? 'active' : ''}`}
              onClick={() => setActiveSection('lifestyle')}
            >
              Lifestyle
            </button>
            <button 
              className={`nav-item ${activeSection === 'housing' ? 'active' : ''}`}
              onClick={() => setActiveSection('housing')}
            >
              Housing
            </button>
          </nav>
        </aside>

        {/* Right Content Area */}
        <main className="profile-content">
          <div className="profile-header">
            <h1>My Roommate Profile</h1>
            <button 
              className="btn-save" 
              onClick={handleSave}
              disabled={saving}
            >
              {saving ? 'Saving...' : 'Save Profile'}
            </button>
          </div>
          
          {message.text && (
            <div className={`message ${message.type}`}>{message.text}</div>
          )}

          {/* Personal Section */}
          {activeSection === 'personal' && (
            <div className="profile-section">
              <h2>About Me</h2>
              
              <div className="form-group">
                <label htmlFor="name">Name *</label>
                <input 
                  type="text" 
                  id="name" 
                  value={profile.name}
                  onChange={(e) => handleChange('name', e.target.value)}
                  placeholder="Your name"
                />
              </div>

              <div className="form-row">
                <div className="form-group">
                  <label htmlFor="age">Age *</label>
                  <input 
                    type="number" 
                    id="age" 
                    value={profile.age}
                    onChange={(e) => handleChange('age', e.target.value)}
                    placeholder="Enter your age"
                    min="17"
                    max="99"
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="gender">Gender Identity *</label>
                  <select
                    id="gender"
                    value={profile.gender}
                    onChange={(e) => handleChange('gender', e.target.value)}
                  >
                    <option value="">Select...</option>
                    <option value="male">Male</option>
                    <option value="female">Female</option>
                    <option value="other">Other</option>
                  </select>
                </div>
              </div>

              <div className="form-group">
                <label htmlFor="bio">Describe yourself</label>
                <textarea 
                  id="bio" 
                  rows="4"
                  value={profile.bio}
                  onChange={(e) => handleChange('bio', e.target.value)}
                  placeholder="Tell potential roommates about yourself, your hobbies, interests..."
                />
              </div>

              <div className="form-group">
                <label className="checkbox-label">
                  <input 
                    type="checkbox" 
                    checked={profile.lookingForRoommate}
                    onChange={(e) => handleChange('lookingForRoommate', e.target.checked)}
                  />
                  <span>I'm actively looking for a roommate</span>
                </label>
              </div>
            </div>
          )}

          {/* Education Section */}
          {activeSection === 'education' && (
            <div className="profile-section">
              <h2>Education</h2>
              
              <div className="form-group">
                <label htmlFor="major">Major *</label>
                <input 
                  type="text" 
                  id="major" 
                  value={profile.major}
                  onChange={(e) => handleChange('major', e.target.value)}
                  placeholder="e.g., Computer Science"
                />
              </div>

              <div className="form-group">
                <label htmlFor="graduationYear">Expected Graduation Year</label>
                <select
                  id="graduationYear"
                  value={profile.graduationYear}
                  onChange={(e) => handleChange('graduationYear', e.target.value)}
                >
                  <option value="">Select...</option>
                  <option value="2025">2025</option>
                  <option value="2026">2026</option>
                  <option value="2027">2027</option>
                  <option value="2028">2028</option>
                  <option value="2029">2029</option>
                </select>
              </div>
            </div>
          )}

          {/* Lifestyle Section */}
          {activeSection === 'lifestyle' && (
            <div className="profile-section">
              <h2>Lifestyle Preferences</h2>
              
              <div className="form-row">
                <div className="form-group">
                  <label htmlFor="sleepSchedule">Sleep Schedule</label>
                  <select
                    id="sleepSchedule"
                    value={profile.sleepSchedule}
                    onChange={(e) => handleChange('sleepSchedule', e.target.value)}
                  >
                    <option value="">Select...</option>
                    <option value="early_bird">Early Bird (before 10pm)</option>
                    <option value="night_owl">Night Owl (after midnight)</option>
                    <option value="flexible">Flexible</option>
                  </select>
                </div>

                <div className="form-group">
                  <label htmlFor="cleanlinessLevel">Cleanliness Level</label>
                  <select
                    id="cleanlinessLevel"
                    value={profile.cleanlinessLevel}
                    onChange={(e) => handleChange('cleanlinessLevel', e.target.value)}
                  >
                    <option value="">Select...</option>
                    <option value="very_clean">Very Clean</option>
                    <option value="moderate">Moderate</option>
                    <option value="relaxed">Relaxed</option>
                  </select>
                </div>
              </div>

              <div className="form-group">
                <label htmlFor="noisePreference">Noise Preference</label>
                <select
                  id="noisePreference"
                  value={profile.noisePreference}
                  onChange={(e) => handleChange('noisePreference', e.target.value)}
                >
                  <option value="">Select...</option>
                  <option value="quiet">Quiet - I need silence to study</option>
                  <option value="moderate">Moderate - Some noise is okay</option>
                  <option value="social">Social - I like an active environment</option>
                </select>
              </div>

              <div className="form-group">
                <label>Preferences</label>
                <div className="checkbox-group">
                  <label className="checkbox-label">
                    <input 
                      type="checkbox" 
                      checked={profile.smokingAllowed}
                      onChange={(e) => handleChange('smokingAllowed', e.target.checked)}
                    />
                    <span>Smoking allowed</span>
                  </label>
                  <label className="checkbox-label">
                    <input 
                      type="checkbox" 
                      checked={profile.petsAllowed}
                      onChange={(e) => handleChange('petsAllowed', e.target.checked)}
                    />
                    <span>Pets allowed</span>
                  </label>
                  <label className="checkbox-label">
                    <input 
                      type="checkbox" 
                      checked={profile.guestsAllowed}
                      onChange={(e) => handleChange('guestsAllowed', e.target.checked)}
                    />
                    <span>Guests allowed</span>
                  </label>
                </div>
              </div>
            </div>
          )}

          {/* Housing Section */}
          {activeSection === 'housing' && (
            <div className="profile-section">
              <h2>Housing Preferences</h2>
              
              <div className="form-row">
                <div className="form-group">
                  <label htmlFor="budgetMin">Budget Min ($)</label>
                  <input 
                    type="number" 
                    id="budgetMin" 
                    value={profile.budgetMin}
                    onChange={(e) => handleChange('budgetMin', e.target.value)}
                    placeholder="e.g., 500"
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="budgetMax">Budget Max ($)</label>
                  <input 
                    type="number" 
                    id="budgetMax" 
                    value={profile.budgetMax}
                    onChange={(e) => handleChange('budgetMax', e.target.value)}
                    placeholder="e.g., 1000"
                  />
                </div>
              </div>

              <div className="form-group">
                <label htmlFor="preferredLocation">Preferred Location</label>
                <input 
                  type="text" 
                  id="preferredLocation" 
                  value={profile.preferredLocation}
                  onChange={(e) => handleChange('preferredLocation', e.target.value)}
                  placeholder="e.g., Near campus, Amherst center"
                />
              </div>

              <div className="form-group">
                <label htmlFor="moveInDate">Preferred Move-in Date</label>
                <input 
                  type="text" 
                  id="moveInDate" 
                  value={profile.moveInDate}
                  onChange={(e) => handleChange('moveInDate', e.target.value)}
                  placeholder="e.g., Fall 2025, ASAP"
                />
              </div>
            </div>
          )}
        </main>
      </div>
    </div>
  )
}

export default Profile
