import { Link } from "react-router-dom";
import RoommateCard from "../components/RoommateCard";
import "./Dashboard.css";
import { useState, useEffect } from "react";

function Dashboard() {
  const [users, setUsers] = useState([]);
  {/*to hold recommended users*/}
  const [recommended, setRecommended] = useState([]);

  useEffect(() => {
    async function fetchData() {
      try {
        const allRes = await fetch("http://localhost:8080/api/users/getAllUsers", {
          method: "POST",
        });
        if (allRes.ok) setUsers(await allRes.json());

        const userId = localStorage.getItem('userId') || 1
        const recRes = await fetch(`http://localhost:8080/api/users/getRecommendedUsers?userId=${userId}`, {
          method: "POST",
        });
        if (recRes.ok) setRecommended(await recRes.json());
      } catch (err) {
        console.error("Error loading data:", err);
      }
    }
{/* Fetch all users and recommended roommates on component mount */}
    fetchData();
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
          {/*display first 4 users as a preview*/}
          {users.slice(0, 4).map((roommate) => (
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
          {/*display recommended users*/}
          {recommended.map((roommate) => ( <RoommateCard key={roommate.id} roommate={roommate} />
          ))}
        </div>
      </section>
    </div>
  );
}

export default Dashboard;
