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
          <!-- Button to navigate to update game -->
          <button @click="editGame(game)">Edit</button>
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
    editGame(game) {
      // Navigate to the update game page with the game ID as a parameter
      this.$router.push({ name: "UpdateGame", params: { gameId: game.gameId } });
    },
    createGame() {
      // Navigate to the create game page
      this.$router.push({ name: "CreateGame" });
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
