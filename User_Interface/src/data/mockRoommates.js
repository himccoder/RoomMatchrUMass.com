// Mock roommate data
export const mockRoommates = [
  {
    id: 1,
    name: "User 1",
    age: 21,
    major: "Computer Science",
    matchPercent: 95,
    description: "User introductions go here",
    avatar: "U1"
  },
  {
    id: 2,
    name: "User 2",
    age: 22,
    major: "Biology",
    matchPercent: 88,
    description: "User introductions go here",
    avatar: "U2"
  }
]

// Get recommended roommates
export const getRecommendedRoommates = (count = 2) => {
  return [...mockRoommates]
    .sort((a, b) => b.matchPercent - a.matchPercent)
    .slice(0, count)
}

// Get roommates for browse
export const getBrowseRoommates = (count = 2) => {
  return mockRoommates.slice(0, count)
}
