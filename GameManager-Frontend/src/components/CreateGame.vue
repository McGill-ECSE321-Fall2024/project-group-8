<template>
  <div>
    <input type="text" placeholder="Title" v-model="newGame.title" />
    <input type="text" placeholder="Description" v-model="newGame.description" />
    <input type="text" placeholder="Genre" v-model="newGame.genre" />
    <input type="number" placeholder="Price" v-model="newGame.price" />
    <input type="number" placeholder="Stock" v-model="newGame.stock" />
    <!-- Dropdown Menu -->
    <label for="gameStatus">Game Status:</label>
    <select v-model="gameStatus" id="gameStatus">
      <option value="Onsale">Onsale</option>
      <option value="Available">Available</option>
      <option value="Archived">Archived</option>
      <option value="PendingOrder">PendingOrder</option>
    </select>
    <!-- Display the Selected Value -->
    <p>Selected Game Status: {{ gameStatus }}</p>
    <button @click="createGame" :disabled="!isGameValid()">Create Game</button>
    <button @click="clearInputs">Clear</button>
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
      newGame: {
        title: "",
        description: "",
        genre: "",
        price: 0.0,
        stock: 0,
        gameStatus: "Available", // Default selected value
      },
    };
  },
  methods: {
    async createGame() {
      try {
        const response = await axiosClient.post("/api/games", this.newGame);
        console.log("Game created:", response.data);
        this.$router.push({ name: "GameList" });
      } catch (e) {
        console.error("Error creating game:", e);
      }
    },
    clearInputs() {
      this.newGame = {
        title: "",
        description: "",
        genre: "",
        price: 0.0,
        stock: 0,
      };
    },
    isGameValid() {
      return (
          this.newGame.title &&
          this.newGame.description &&
          this.newGame.genre &&
          this.newGame.price > 0 &&
          this.newGame.stock >= 0
      );
    },
  },
};
</script>

<style>
select {
  padding: 5px 10px;
  font-size: 16px;
  border: 1px solid #ddd;
  border-radius: 5px;
}

label {
  margin-right: 10px;
  font-weight: bold;
}
</style>