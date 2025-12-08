# RoomMatchrUMass.com
Finding Roommates Made Simple

## Overview
Our project is a secure roommate matching platform exclusively for the UMass community, accessible only with UMass credentials. It aims to provide the students of Umass Amherst a transparent, personalized and user-friendly roommate searching application. The need for 
this application arises from limitations of the current opaque SPIRE roommate matching system which lacks preference based matching. Instead our application would require the users to fill a larger questionnaire to sign up as a potential roommate.
SPIRE website lacks the search and filter features for finding a roommate. Our application would allow a user to use advanced filters to search the kind of roommate they need. We also plan to implement matching algorithms to come up with top “K” matches for any user. 
Our application would prioritize safety by providing secure authorization and authentication while hiding more sensitive questionnaire information. We improve accessibility and decision making by providing the above mentioned features pertaining to algorithmic matching and manual filtering. If time permits, we also plan to integrate LLM functionality such that it is easier to search for the closest matching roommates through prompts. 

## Details on Running Application and Backend

Please refer to the inner README.md file at https://github.com/himccoder/RoomMatchrUMass.com/blob/main/roommate-matcher/README.md



┌─────────────────────────────────────────────────────────────────────────┐
│                         FRONTEND (React)                                │
│                      http://localhost:5173                              │
├─────────────────────────────────────────────────────────────────────────┤
│  Login/Signup  │  Browse  │  Profile  │  Chat  │  Filters              │
│       ↓             ↓          ↓          ↓          ↓                  │
│              api.js (API Service Layer)                                 │
└──────────────────────────┬──────────────────────────────────────────────┘
                           │ HTTP REST API
                           ↓
┌─────────────────────────────────────────────────────────────────────────┐
│                      BACKEND (Spring Boot)                              │
│                      http://localhost:8080                              │
├─────────────────────────────────────────────────────────────────────────┤
│  AuthController  │  ProfileController  │  MessageController            │
│       ↓                   ↓                     ↓                       │
│  AuthService     │  ProfileService     │  MessageService               │
│       ↓                   ↓                     ↓                       │
│  UserRepository  │  UserRepository     │  MessageRepository            │
└──────────────────────────┬──────────────────────────────────────────────┘
                           │ JDBC (MySQL Connector)
                           ↓
┌─────────────────────────────────────────────────────────────────────────┐
│                      MySQL DATABASE                                     │
│                   172.31.57.159:3306                                    │
│                   roommate_matchr_db                                    │
├─────────────────────────────────────────────────────────────────────────┤
│  users  │  messages  │  roommate_requests                              │
└─────────────────────────────────────────────────────────────────────────┘
