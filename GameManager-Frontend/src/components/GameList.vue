<template>
  <div>
    <h1>Game List</h1>
    <!-- Table to display games -->
    <table>
      <thead>
      <tr>
        <th>Title</th>
        <th>Description</th>
        <th>Genre</th>
        <th>Price</th>
        <th>Stock</th>
        <th>Actions</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="game in games" :key="game.gameId">
        <td>{{ game.title }}</td>
        <td>{{ game.description }}</td>
        <td>{{ game.genre }}</td>
        <td>{{ game.price }}</td>
        <td>{{ game.stock }}</td>
        <td>
          <button @click="editGame(game.gameId)">Edit</button>
          <button @click="deleteGame(game.gameId)">Delete</button>
        </td>
      </tr>
      </tbody>
    </table>

    <!-- Button to navigate to create game -->
    <button @click="createGame">Create New Game</button>
  </div>
</template>

<script>
import axios from "axios";

const axiosClient = axios.create({
  baseURL: "http://localhost:8080", // Backend API base URL
});

export default {
  name: "GameList",
  data() {
    return {
      games: [], // Array to store games fetched from backend
    };
  },

  async created() {
    try {
      // Fetch games when the component is created
      const response = await axiosClient.get("/api/games");
      this.games = response.data; // Populate the games array with the response data
    } catch (e) {
      console.error("Error fetching games:", e); // Log any errors
    }
  },
  methods: {
    createGame() {
      this.$router.push({ name: "CreateGame" });
    },
    editGame(gameId) {
      this.$router.push({ name: "UpdateGame", params: { id: gameId } });
    },
    async deleteGame(gameId) {
      if (!confirm("Are you sure you want to delete this game?")) {
        return;
      }
      try {
        await axiosClient.delete(`/api/games/${gameId}`); // Corrected template literal syntax
        this.games = this.games.filter((game) => game.gameId !== gameId);
        alert("Game deleted successfully!");
      } catch (error) {
        console.error("Error deleting game:", error);
        alert("Failed to delete game.");
      }
    },
  },
};

</script>

<style>
/* Add some basic styling */
table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 20px;
}

table th,
table td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

table th {
  background-color: #f2f2f2;
}

button {
  margin: 5px;
  padding: 10px;
  cursor: pointer;
}
</style>
