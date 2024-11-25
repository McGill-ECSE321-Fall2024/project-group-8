<template>
    <div>
      <h2>Search Games</h2>
  
      <!-- Search Form -->
      <div>
        <input
          type="text"
          v-model="searchKeyword"
          placeholder="Enter keyword to search"
        />
        <button @click="searchGames">Search</button>
      </div>
  
      <!-- Results Section -->
      <div v-if="games.length > 0">
        <h3>Search Results:</h3>
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Title</th>
              <th>Description</th>
              <th>Genre</th>
              <th>Price</th>
              <th>Stock</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="game in games" :key="game.id">
              <td>{{ game.id }}</td>
              <td>{{ game.title }}</td>
              <td>{{ game.description }}</td>
              <td>{{ game.genre }}</td>
              <td>{{ game.price }}</td>
              <td>{{ game.stock }}</td>
            </tr>
          </tbody>
        </table>
      </div>
  
      <!-- No Results Message -->
      <div v-else-if="searchPerformed">
        <p>No games found for "{{ searchKeyword }}".</p>
      </div>
    </div>
  </template>
  
  <script>
  import axios from "axios";
  
  const axiosClient = axios.create({
    baseURL: "http://localhost:8080",
  });
  
  export default {
    data() {
      return {
        searchKeyword: "", // Keyword for searching games
        games: [], // Array to hold search results
        searchPerformed: false, // Indicates if a search has been performed
      };
    },
    methods: {
      async searchGames() {
        if (!this.searchKeyword.trim()) {
          alert("Please enter a keyword to search.");
          return;
        }
  
        try {
          const response = await axiosClient.get("/api/games/search", {
            params: { keyword: this.searchKeyword },
          });
          this.games = response.data;
          this.searchPerformed = true;
        } catch (error) {
          console.error("Error searching games:", error);
          alert("An error occurred while searching for games.");
        }
      },
    },
  };
  </script>
  
  <style scoped>
  /* Add some basic styling */
  table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 20px;
  }
  
  th,
  td {
    border: 1px solid #ddd;
    padding: 8px;
    text-align: left;
  }
  
  th {
    background-color: #f4f4f4;
  }
  </style>
  