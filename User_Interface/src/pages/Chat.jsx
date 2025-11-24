import './Chat.css'

function Chat() {
  return (
    <div className="chat-page">
      <div className="chat-container">
        <aside className="chat-sidebar">
          <div className="chat-sidebar-header">
            <h2>Messages</h2>
          </div>
          <div className="conversations-list">
            <p className="empty-state">No conversations yet</p>
          </div>
        </aside>

        <main className="chat-main">
          <div className="chat-header">
            <h3>Select a conversation</h3>
          </div>

          <div className="messages-area">
            <p className="empty-state">Messages will appear here</p>
          </div>

          <div className="message-input-container">
            <form className="message-input-form">
              <input type="text" placeholder="Type a message..." className="message-input" />
              <button type="submit" className="send-btn">Send</button>
            </form>
          </div>
        </main>
      </div>
    </div>
  )
}

export default Chat
