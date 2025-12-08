import './FilterPanel.css'

function FilterPanel({ filters, onFilterChange, onClearFilters }) {
  const handleChange = (field, value) => {
    onFilterChange({ [field]: value })
  }

  const handleCheckbox = (field) => {
    // Toggle between: '' (any) -> 'true' (yes) -> 'false' (no) -> '' (any)
    const current = filters[field]
    let next = ''
    if (current === '') next = 'true'
    else if (current === 'true') next = 'false'
    else next = ''
    onFilterChange({ [field]: next })
  }

  const getCheckboxState = (field) => {
    if (filters[field] === 'true') return '✓ Yes'
    if (filters[field] === 'false') return '✗ No'
    return 'Any'
  }

  return (
    <div className="filter-panel">
      <div className="filter-header">
        <h3>Filter Roommates</h3>
        <button className="clear-btn" onClick={onClearFilters}>
          Clear All
        </button>
      </div>

      {/* Gender Filter */}
      <div className="filter-group">
        <h4 className="filter-title">Gender</h4>
        <select 
          value={filters.gender} 
          onChange={(e) => handleChange('gender', e.target.value)}
          className="filter-select"
        >
          <option value="">All</option>
          <option value="male">Male</option>
          <option value="female">Female</option>
          <option value="other">Other</option>
        </select>
      </div>

      {/* Major Filter */}
      <div className="filter-group">
        <h4 className="filter-title">Major</h4>
        <input
          type="text"
          placeholder="Search major..."
          value={filters.major}
          onChange={(e) => handleChange('major', e.target.value)}
          className="filter-input"
        />
      </div>

      {/* Age Range Filter */}
      <div className="filter-group">
        <h4 className="filter-title">Age Range</h4>
        <div className="age-range">
          <input
            type="number"
            placeholder="Min"
            value={filters.minAge}
            onChange={(e) => handleChange('minAge', e.target.value)}
            className="filter-input small"
            min="17"
            max="99"
          />
          <span>to</span>
          <input
            type="number"
            placeholder="Max"
            value={filters.maxAge}
            onChange={(e) => handleChange('maxAge', e.target.value)}
            className="filter-input small"
            min="17"
            max="99"
          />
        </div>
      </div>

      {/* Graduation Year Filter */}
      <div className="filter-group">
        <h4 className="filter-title">Graduation Year</h4>
        <select 
          value={filters.graduationYear} 
          onChange={(e) => handleChange('graduationYear', e.target.value)}
          className="filter-select"
        >
          <option value="">Any</option>
          <option value="2025">2025</option>
          <option value="2026">2026</option>
          <option value="2027">2027</option>
          <option value="2028">2028</option>
          <option value="2029">2029</option>
        </select>
      </div>

      <div className="filter-divider">
        <span>Lifestyle Preferences</span>
      </div>

      {/* Sleep Schedule Filter */}
      <div className="filter-group">
        <h4 className="filter-title">Sleep Schedule</h4>
        <select 
          value={filters.sleepSchedule} 
          onChange={(e) => handleChange('sleepSchedule', e.target.value)}
          className="filter-select"
        >
          <option value="">Any</option>
          <option value="early_bird">Early Bird</option>
          <option value="night_owl">Night Owl</option>
          <option value="flexible">Flexible</option>
        </select>
      </div>

      {/* Cleanliness Filter */}
      <div className="filter-group">
        <h4 className="filter-title">Cleanliness Level</h4>
        <select 
          value={filters.cleanlinessLevel} 
          onChange={(e) => handleChange('cleanlinessLevel', e.target.value)}
          className="filter-select"
        >
          <option value="">Any</option>
          <option value="very_clean">Very Clean</option>
          <option value="moderate">Moderate</option>
          <option value="relaxed">Relaxed</option>
        </select>
      </div>

      {/* Noise Preference Filter */}
      <div className="filter-group">
        <h4 className="filter-title">Noise Preference</h4>
        <select 
          value={filters.noisePreference} 
          onChange={(e) => handleChange('noisePreference', e.target.value)}
          className="filter-select"
        >
          <option value="">Any</option>
          <option value="quiet">Quiet</option>
          <option value="moderate">Moderate</option>
          <option value="social">Social</option>
        </select>
      </div>

      {/* Toggle Filters */}
      <div className="filter-group">
        <h4 className="filter-title">Preferences</h4>
        <div className="toggle-filters">
          <button 
            className={`toggle-btn ${filters.smokingAllowed ? 'active' : ''}`}
            onClick={() => handleCheckbox('smokingAllowed')}
          >
            🚬 Smoking: {getCheckboxState('smokingAllowed')}
          </button>
          <button 
            className={`toggle-btn ${filters.petsAllowed ? 'active' : ''}`}
            onClick={() => handleCheckbox('petsAllowed')}
          >
            🐾 Pets: {getCheckboxState('petsAllowed')}
          </button>
          <button 
            className={`toggle-btn ${filters.guestsAllowed ? 'active' : ''}`}
            onClick={() => handleCheckbox('guestsAllowed')}
          >
            👥 Guests: {getCheckboxState('guestsAllowed')}
          </button>
        </div>
      </div>

      <div className="filter-divider">
        <span>Budget</span>
      </div>

      {/* Budget Range Filter */}
      <div className="filter-group">
        <h4 className="filter-title">Monthly Budget ($)</h4>
        <div className="budget-range">
          <input
            type="number"
            placeholder="Min"
            value={filters.budgetMin}
            onChange={(e) => handleChange('budgetMin', e.target.value)}
            className="filter-input small"
            min="0"
            step="50"
          />
          <span>to</span>
          <input
            type="number"
            placeholder="Max"
            value={filters.budgetMax}
            onChange={(e) => handleChange('budgetMax', e.target.value)}
            className="filter-input small"
            min="0"
            step="50"
          />
        </div>
      </div>

      {/* Match Score Filter */}
      <div className="filter-group">
        <h4 className="filter-title">Minimum Match %</h4>
        <select 
          value={filters.minMatchPercent} 
          onChange={(e) => handleChange('minMatchPercent', e.target.value)}
          className="filter-select"
        >
          <option value="">Show All</option>
          <option value="90">90%+ (Great Match)</option>
          <option value="75">75%+ (Good Match)</option>
          <option value="50">50%+ (Compatible)</option>
        </select>
      </div>
    </div>
  )
}

export default FilterPanel
