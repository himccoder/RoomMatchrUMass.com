import './FilterPanel.css'

function FilterPanel() {
  return (
    <div className="filter-panel">
      <div className="filter-instructions">
        Filter roommates
      </div>

      {/* Room Filter */}
      <div className="filter-group">
        <h3 className="filter-title">ROOM</h3>
        <div className="filter-options">
          <label className="filter-option">
            <input type="checkbox" />
            <span>Have A Place</span>
          </label>
          <label className="filter-option">
            <input type="checkbox" />
            <span>Need A Place</span>
          </label>
        </div>
      </div>

      {/* When Filter */}
      <div className="filter-group">
        <h3 className="filter-title">WHEN?</h3>
        <div className="filter-options">
          <label className="filter-option">
            <input type="checkbox" />
            <span>Fall</span>
          </label>
          <label className="filter-option">
            <input type="checkbox" />
            <span>Spring</span>
          </label>
          <label className="filter-option">
            <input type="checkbox" />
            <span>Winter</span>
          </label>
          <label className="filter-option">
            <input type="checkbox" />
            <span>Summer</span>
          </label>
        </div>
      </div>

      {/* Gender Identity Filter */}
      <div className="filter-group">
        <h3 className="filter-title">GENDER IDENTITY</h3>
        <div className="filter-options">
          <label className="filter-option">
            <input type="checkbox" />
            <span>Male</span>
          </label>
          <label className="filter-option">
            <input type="checkbox" />
            <span>Female</span>
          </label>
          <label className="filter-option">
            <input type="checkbox" />
            <span>Other</span>
          </label>
        </div>
      </div>

      {/* Smoking Filter */}
      <div className="filter-group">
        <h3 className="filter-title">SMOKING</h3>
        <div className="filter-options">
          <label className="filter-option">
            <input type="checkbox" />
            <span>No</span>
          </label>
          <label className="filter-option">
            <input type="checkbox" />
            <span>Yes</span>
          </label>
        </div>
      </div>

      {/* Pets Filter */}
      <div className="filter-group">
        <h3 className="filter-title">PETS</h3>
        <div className="filter-options">
          <label className="filter-option">
            <input type="checkbox" />
            <span>No pets</span>
          </label>
          <label className="filter-option">
            <input type="checkbox" />
            <span>Cat</span>
          </label>
          <label className="filter-option">
            <input type="checkbox" />
            <span>Dog</span>
          </label>
          <label className="filter-option">
            <input type="checkbox" />
            <span>Other</span>
          </label>
        </div>
      </div>
    </div>
  )
}

export default FilterPanel
