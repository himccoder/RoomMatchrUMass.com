import './AccountSettings.css'

function AccountSettings() {

  return (
    <div className="account-settings-page">
      <div className="settings-container">
        <h1>Account Settings</h1>
        <p className="page-subtitle">Manage your account preferences and settings</p>

        {/* Communication Preferences Section */}
        <section className="settings-section">
          <div className="section-header">
            <h2>Communication Preferences</h2>
            <p className="section-description">
              Choose how you want to receive notifications and updates
            </p>
          </div>

          <div className="settings-content">
            <div className="preference-item">
              <div className="preference-info">
                <h3>Email Notifications</h3>
              </div>
              <input type="checkbox" defaultChecked />
            </div>

            <div className="preference-item">
              <div className="preference-info">
                <h3>Message Notifications</h3>
              </div>
              <input type="checkbox" defaultChecked />
            </div>

            <div className="preference-item">
              <div className="preference-info">
                <h3>Match Notifications</h3>
              </div>
              <input type="checkbox" defaultChecked />
            </div>

            <button className="btn-save-preferences">
              Save Preferences
            </button>
          </div>
        </section>

        {/* Account Management Section */}
        <section className="settings-section danger-section">
          <div className="section-header">
            <h2>Account Management</h2>
            <p className="section-description">
              Delete your roommate profile or permanently delete your account
            </p>
          </div>

          <div className="settings-content">
            <div className="danger-item">
              <div className="danger-info">
                <h3>Disable Account</h3>
              </div>
              <button className="btn-disable">Disable Account</button>
            </div>

            <div className="danger-item danger-item-delete">
              <div className="danger-info">
                <h3>Delete Account</h3>
              </div>
              <button className="btn-delete">Delete Account Permanently</button>
            </div>
          </div>
        </section>
      </div>
    </div>
  )
}

export default AccountSettings
