import RoommateCard from "../components/RoommateCard";
import FilterPanel from "../components/FilterPanel";
import { mockRoommates } from "../data/mockRoommates";
import "./Browse.css";
import { useState, useEffect } from "react";

function Browse() {
  const [users, setUsers] = useState([]);

  const [filters, setFilters] = useState({
    sleepSchedule: null,
    foodType: null,
    personalityType: null,
    smokingPreference: null,
    petsPreference: null,
  });

  const hasActiveFilters = Object.values(filters).some(Boolean);

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        if (hasActiveFilters) {
          const res = await fetch(
            "http://localhost:8080/api/users/getFilteredUsers",
            {
              method: "POST",
              headers: {
                "Content-Type": "application/json",
              },
              body: JSON.stringify(filters),
            }
          );
          if (!res.ok) {
            throw new Error(
              `Error: Could not get an ok response back. Status: ${res.status}`
            );
          }
          const data = await res.json();
          setUsers(data);
        } else {
          const res = await fetch(
            "http://localhost:8080/api/users/getAllUsers",
            {
              method: "POST",
            }
          );

          if (!res.ok) {
            throw new Error(
              `Error: Could not get an ok response back. Status: ${res.status}`
            );
          }
          const data = await res.json();
          setUsers(data);
        }
      } catch (err) {
        console.error(err);
      }
    };

    fetchUsers();
  }, [hasActiveFilters, filters]);

  return (
    <div className="browse-page">
      <div className="browse-container">
        <aside className="browse-sidebar">
          <FilterPanel filters={filters} onChange={setFilters} />
        </aside>

        <main className="browse-content">
          <div className="browse-header">
            <h1>Browse Roommates</h1>
            <p className="results-count">Showing {users.length} results</p>
          </div>

          <div className="browse-cards-grid">
            {users.map((roommate) => (
              <RoommateCard key={roommate.id} roommate={roommate} />
            ))}
          </div>
        </main>
      </div>
    </div>
  );
}

export default Browse;
