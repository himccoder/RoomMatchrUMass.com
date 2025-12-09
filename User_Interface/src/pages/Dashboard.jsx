import { Link } from "react-router-dom";
import RoommateCard from "../components/RoommateCard";
import { getRecommendedRoommates } from "../data/mockRoommates";
import "./Dashboard.css";
import { useState, useEffect } from "react";

function Dashboard() {
  const recommendedRoommates = getRecommendedRoommates(2);
  const [users, setUsers] = useState([]);

  useEffect(() => {
    async function fetchAllUsers() {
      try {
        const res = await fetch("http://localhost:8080/api/users/getAllUsers", {
          method: "POST",
        });

        if (!res.ok) {
          throw new Error(
            `Error: Could not get an ok response back. Status: ${res.status}`
          );
        }
        const data = await res.json();
        setUsers(data);
      } catch (err) {
        console.error("Error: Could not load the users:", err);
      }
    }

    fetchAllUsers();
  }, []);

  return (
    <div className="dashboard">
      {/* Browse Section - Main, Top */}
      <section className="browse-section">
        <div className="section-header">
          <h2>Browse Roommates</h2>
          <Link to="/browse" className="view-all-link">
            View All →
          </Link>
        </div>
        <div className="browse-grid">
          {users.map((roommate) => (
            <RoommateCard key={roommate.id} roommate={roommate} />
          ))}
        </div>
      </section>

      {/* Recommendations Section - Smaller, Below */}
      <section className="recommendations-section">
        <div className="section-header">
          <h2>Recommended Roommates</h2>
        </div>
        <div className="recommendations-grid">
          {recommendedRoommates.map((roommate) => (
            <RoommateCard key={roommate.id} roommate={roommate} />
          ))}
        </div>
      </section>
    </div>
  );
}

export default Dashboard;
