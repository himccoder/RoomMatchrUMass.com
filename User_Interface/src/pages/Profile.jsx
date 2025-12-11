import { useState } from 'react'
import './Profile.css'

{/* Profile Page with Roommate Preferences Survey */
  {/* This function Allows users to set and save their roommate preferences */}
}
function Profile() {
  const [sleepSchedule, setSleepSchedule] = useState('')
  const [foodType, setFoodType] = useState('')
  const [personalityType, setPersonalityType] = useState('')
  const [petsPreference, setPetsPreference] = useState('')
  const [smokingPreference, setSmokingPreference] = useState('')
  const [msg, setMsg] = useState('')

  {/* takes survey data and submits to backend */}
  const handleSubmit = async (e) => {
    e.preventDefault()
    try {
      const userId = localStorage.getItem('userId') || 1
      const res = await fetch('http://localhost:8080/api/survey/submit', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ userId, sleepSchedule, foodType, personalityType, petsPreference, smokingPreference })
      })
      const data = await res.json()
      setMsg(data.message || 'Survey saved!')
    } catch { setMsg('Error saving survey') }
  }

  return (
    <div className="profile-page">
      <div className="profile-container">
        <main className="profile-content">
          <h1>Roommate Preferences Survey</h1>
          
          {msg && <p style={{ color: msg.includes('success') ? 'green' : 'red' }}>{msg}</p>}
          
{/* Survey Form */}
          <form onSubmit={handleSubmit}>
            {/* Sleep Schedule */}
            <div className="profile-section">
              <div className="form-group">
                <label htmlFor="sleepSchedule">Sleep Schedule</label>
                <select id="sleepSchedule" value={sleepSchedule} onChange={e => setSleepSchedule(e.target.value)} required>
                  <option value="">Select Preference...</option>
                  <option value="EARLY_BIRD">Early Bird</option>
                  <option value="FLEXIBLE">Flexible</option>
                  <option value="NIGHT_OWL">Night Owl</option>
                </select>
              </div>

              {/* Food Preference */}
              <div className="form-group">
                <label htmlFor="foodType">Food Preference</label>
                <select id="foodType" value={foodType} onChange={e => setFoodType(e.target.value)} required>
                  <option value="">Select Preference...</option>
                  <option value="VEGETARIAN">Vegetarian</option>
                  <option value="FLEXIBLE">Flexible</option>
                  <option value="NON_VEGETARIAN">Non-Vegetarian</option>
                </select>
              </div>

{/* Personality Type */}
              <div className="form-group">
                <label htmlFor="personalityType">Personality Type</label>
                <select id="personalityType" value={personalityType} onChange={e => setPersonalityType(e.target.value)} required>
                  <option value="">Select Preference...</option>
                  <option value="INTROVERT">Introvert</option>
                  <option value="AMBIVERT">Ambivert</option>
                  <option value="EXTROVERT">Extrovert</option>
                </select>
              </div>

              <div className="form-group">
              {/* Pets Preference */}
                <label htmlFor="petsPreference">Pets Preference</label>
                <select id="petsPreference" value={petsPreference} onChange={e => setPetsPreference(e.target.value)} required>
                  <option value="">Select Preference...</option>
                  <option value="OKAY_WITH_PETS">Okay With Pets</option>
                  <option value="NO_PETS">No Pets</option>
                </select>
              </div>

              {/* Smoking Preference */}
              <div className="form-group">
                <label htmlFor="smokingPreference">Smoking Preference</label>
                <select id="smokingPreference" value={smokingPreference} onChange={e => setSmokingPreference(e.target.value)} required>
                  <option value="">Select Preference...</option>
                  <option value="PREFER_NON_SMOKER_ONLY">Prefer Non-Smoker Only</option>
                  <option value="NO_PREFERENCE">No Preference</option>
                </select>
              </div>

              <div className="form-actions">
                <button type="submit" className="btn-save">Save Survey</button>
              </div>
            </div>
          </form>
        </main>
      </div>
    </div>
  )
}

export default Profile
