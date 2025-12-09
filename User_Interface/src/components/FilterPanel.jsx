import "./FilterPanel.css";

function FilterPanel({ filters = {}, onChange }) {
  function handleChange(field, value, e) {
    const updatedFilters = { ...filters };

    if (e.target.checked) {
      updatedFilters[field] = value;
    } else {
      updatedFilters[field] = null;
    }

    onChange(updatedFilters);
  }

  return (
    <div className="filter-panel">
      <div className="filter-instructions">Filter roommates</div>

      <div className="filter-group">
        <h3 className="filter-title">SLEEP SCHEDULE</h3>
        <div className="filter-options">
          <label className="filter-option">
            <input
              type="checkbox"
              checked={filters.sleepSchedule === "EARLY_BIRD"}
              onChange={handleChange("sleepSchedule", "EARLY_BIRD")}
            />
            <span>Early bird</span>
          </label>

          <label className="filter-option">
            <input
              type="checkbox"
              checked={filters.sleepSchedule === "NIGHT_OWL"}
              onChange={handleChange("sleepSchedule", "NIGHT_OWL")}
            />
            <span>Night owl</span>
          </label>

          <label className="filter-option">
            <input
              type="checkbox"
              checked={filters.sleepSchedule === "FLEXIBLE"}
              onChange={handleChange("sleepSchedule", "FLEXIBLE")}
            />
            <span>Flexible</span>
          </label>
        </div>
      </div>

      <div className="filter-group">
        <h3 className="filter-title">FOOD TYPE</h3>
        <div className="filter-options">
          <label className="filter-option">
            <input
              type="checkbox"
              checked={filters.foodType === "VEGETARIAN"}
              onChange={handleChange("foodType", "VEGETARIAN")}
            />
            <span>Vegetarian</span>
          </label>

          <label className="filter-option">
            <input
              type="checkbox"
              checked={filters.foodType === "NON_VEGETARIAN"}
              onChange={handleChange("foodType", "NON_VEGETARIAN")}
            />
            <span>Non-vegetarian</span>
          </label>

          <label className="filter-option">
            <input
              type="checkbox"
              checked={filters.foodType === "FLEXIBLE"}
              onChange={handleChange("foodType", "FLEXIBLE")}
            />
            <span>Flexible</span>
          </label>
        </div>
      </div>

      <div className="filter-group">
        <h3 className="filter-title">PERSONALITY</h3>
        <div className="filter-options">
          <label className="filter-option">
            <input
              type="checkbox"
              checked={filters.personalityType === "INTROVERT"}
              onChange={handleChange("personalityType", "INTROVERT")}
            />
            <span>Introvert</span>
          </label>

          <label className="filter-option">
            <input
              type="checkbox"
              checked={filters.personalityType === "EXTROVERT"}
              onChange={handleChange("personalityType", "EXTROVERT")}
            />
            <span>Extrovert</span>
          </label>

          <label className="filter-option">
            <input
              type="checkbox"
              checked={filters.personalityType === "AMBIVERT"}
              onChange={handleChange("personalityType", "AMBIVERT")}
            />
            <span>Ambivert</span>
          </label>
        </div>
      </div>

      <div className="filter-group">
        <h3 className="filter-title">SMOKING</h3>
        <div className="filter-options">
          <label className="filter-option">
            <input
              type="checkbox"
              checked={filters.smokingPreference === "NO_PREFERENCE"}
              onChange={handleChange("smokingPreference", "NO_PREFERENCE")}
            />
            <span>No preference</span>
          </label>

          <label className="filter-option">
            <input
              type="checkbox"
              checked={filters.smokingPreference === "PREFER_NON_SMOKER_ONLY"}
              onChange={handleChange(
                "smokingPreference",
                "PREFER_NON_SMOKER_ONLY"
              )}
            />
            <span>Prefer non-smoker only</span>
          </label>
        </div>
      </div>

      <div className="filter-group">
        <h3 className="filter-title">PETS PREFERENCE</h3>
        <div className="filter-options">
          <label className="filter-option">
            <input
              type="checkbox"
              checked={filters.petsPreference === "NO_PETS"}
              onChange={handleChange("petsPreference", "NO_PETS")}
            />
            <span>No pets</span>
          </label>

          <label className="filter-option">
            <input
              type="checkbox"
              checked={filters.petsPreference === "OKAY_WITH_PETS"}
              onChange={handleChange("petsPreference", "OKAY_WITH_PETS")}
            />
            <span>Okay with pets</span>
          </label>
        </div>
      </div>
    </div>
  );
}

export default FilterPanel;
