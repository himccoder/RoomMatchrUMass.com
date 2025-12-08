import { useState, useEffect, useRef } from 'react'
import { useParams } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'
import { messageAPI, profileAPI } from '../services/api'
import './Chat.css'

function Chat() {
  const { partnerId } = useParams()
  const { user } = useAuth()
  const messagesEndRef = useRef(null)
  
  const [conversations, setConversations] = useState([])
  const [messages, setMessages] = useState([])
  const [selectedPartner, setSelectedPartner] = useState(null)
  const [newMessage, setNewMessage] = useState('')
  const [loading, setLoading] = useState(true)
  const [sending, setSending] = useState(false)

  // Load conversations list
  useEffect(() => {
    async function loadConversations() {
      if (!user?.id) return
      
      try {
        const data = await messageAPI.getConversations(user.id)
        setConversations(data)
      } catch (err) {
        console.error('Failed to load conversations:', err)
      } finally {
        setLoading(false)
      }
    }
    loadConversations()
  }, [user?.id])

  // Load messages when partner is selected
  useEffect(() => {
    async function loadMessages() {
      if (!user?.id || !selectedPartner?.id) return
      
      try {
        const data = await messageAPI.getConversation(user.id, selectedPartner.id)
        setMessages(data)
        // Mark as read
        messageAPI.markAsRead(user.id, selectedPartner.id)
      } catch (err) {
        console.error('Failed to load messages:', err)
      }
    }
    loadMessages()
    
    // Poll for new messages every 5 seconds
    const interval = setInterval(loadMessages, 5000)
    return () => clearInterval(interval)
  }, [user?.id, selectedPartner?.id])

  // Handle URL partner ID
  useEffect(() => {
    async function loadPartnerFromUrl() {
      if (partnerId && user?.id) {
        try {
          const profile = await profileAPI.getProfile(partnerId)
          setSelectedPartner({ id: profile.id, name: profile.name, major: profile.major })
        } catch (err) {
          console.error('Failed to load partner profile:', err)
        }
      }
    }
    loadPartnerFromUrl()
  }, [partnerId, user?.id])

  // Scroll to bottom when messages change
  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' })
  }, [messages])

  const handleSendMessage = async (e) => {
    e.preventDefault()
    if (!newMessage.trim() || !selectedPartner?.id || sending) return
    
    try {
      setSending(true)
      const sentMessage = await messageAPI.sendMessage(user.id, selectedPartner.id, newMessage.trim())
      setMessages(prev => [...prev, sentMessage])
      setNewMessage('')
      
      // Update conversation list
      setConversations(prev => {
        const existing = prev.find(c => c.partnerId === selectedPartner.id)
        if (existing) {
          return prev.map(c => 
            c.partnerId === selectedPartner.id 
              ? { ...c, lastMessage: newMessage.trim(), lastMessageTime: new Date().toISOString() }
              : c
          )
        }
        return [{ 
          partnerId: selectedPartner.id, 
          partnerName: selectedPartner.name,
          lastMessage: newMessage.trim(),
          lastMessageTime: new Date().toISOString()
        }, ...prev]
      })
    } catch (err) {
      console.error('Failed to send message:', err)
      alert('Failed to send message. Please try again.')
    } finally {
      setSending(false)
    }
  }

  const selectConversation = (conv) => {
    setSelectedPartner({ id: conv.partnerId, name: conv.partnerName, major: conv.partnerMajor })
  }

  const formatTime = (dateString) => {
    if (!dateString) return ''
    const date = new Date(dateString)
    const now = new Date()
    const diff = now - date
    
    if (diff < 60000) return 'Just now'
    if (diff < 3600000) return `${Math.floor(diff / 60000)}m ago`
    if (diff < 86400000) return date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })
    return date.toLocaleDateString()
  }

  if (loading) {
    return <div className="chat-page loading">Loading conversations...</div>
  }

  return (
    <div className="chat-page">
      <div className="chat-container">
        {/* Conversations Sidebar */}
        <aside className="chat-sidebar">
          <div className="chat-sidebar-header">
            <h2>Messages</h2>
          </div>
          <div className="conversations-list">
            {conversations.length === 0 ? (
              <p className="empty-state">No conversations yet. Start chatting with someone from the Browse page!</p>
            ) : (
              conversations.map(conv => (
                <div 
                  key={conv.partnerId}
                  className={`conversation-item ${selectedPartner?.id === conv.partnerId ? 'active' : ''}`}
                  onClick={() => selectConversation(conv)}
                >
                  <div className="conv-avatar">
                    {conv.partnerName?.charAt(0).toUpperCase() || '?'}
                  </div>
                  <div className="conv-info">
                    <div className="conv-header">
                      <span className="conv-name">{conv.partnerName}</span>
                      <span className="conv-time">{formatTime(conv.lastMessageTime)}</span>
                    </div>
                    <p className="conv-preview">{conv.lastMessage || 'No messages yet'}</p>
                  </div>
                  {conv.unreadCount > 0 && (
                    <span className="unread-count">{conv.unreadCount}</span>
                  )}
                </div>
              ))
            )}
          </div>
        </aside>

        {/* Chat Main Area */}
        <main className="chat-main">
          {selectedPartner ? (
            <>
              <div className="chat-header">
                <div className="chat-partner-info">
                  <div className="partner-avatar">
                    {selectedPartner.name?.charAt(0).toUpperCase() || '?'}
                  </div>
                  <div>
                    <h3>{selectedPartner.name}</h3>
                    {selectedPartner.major && <span>{selectedPartner.major}</span>}
                  </div>
                </div>
              </div>

              <div className="messages-area">
                {messages.length === 0 ? (
                  <div className="empty-messages">
                    <p>No messages yet. Say hello! 👋</p>
                  </div>
                ) : (
                  messages.map(msg => (
                    <div 
                      key={msg.id} 
                      className={`message ${msg.senderId === user.id ? 'sent' : 'received'}`}
                    >
                      <div className="message-content">{msg.content}</div>
                      <div className="message-time">{formatTime(msg.createdAt)}</div>
                    </div>
                  ))
                )}
                <div ref={messagesEndRef} />
              </div>

              <div className="message-input-container">
                <form className="message-input-form" onSubmit={handleSendMessage}>
                  <input 
                    type="text" 
                    placeholder="Type a message..." 
                    className="message-input"
                    value={newMessage}
                    onChange={(e) => setNewMessage(e.target.value)}
                    disabled={sending}
                  />
                  <button type="submit" className="send-btn" disabled={!newMessage.trim() || sending}>
                    {sending ? 'Sending...' : 'Send'}
                  </button>
                </form>
              </div>
            </>
          ) : (
            <div className="no-chat-selected">
              <div className="no-chat-icon">💬</div>
              <h3>Select a conversation</h3>
              <p>Choose a conversation from the sidebar or start a new one from the Browse page</p>
            </div>
          )}
        </main>
      </div>
    </div>
  )
}

export default Chat
