<template>
  <div>
    <input type="text" placeholder="Title" v-model="newGame.title" />
    <input type="text" placeholder="Description" v-model="newGame.description" />
    <input type="text" placeholder="Genre" v-model="newGame.genre" />
    <input type="number" placeholder="Price" v-model="newGame.price" />
    <input type="number" placeholder="Stock" v-model="newGame.stock" />
    <!-- Dropdown Menu -->
    <label for="gameStatus">Request Status:</label>
    <select v-model="newGame.requestStatus" id="requestStatus">
      <option value="PendingApproval">Create Request</option>
<!--      <option value="Approved">Approved</option>-->
      <option value="PendingArchived">Archive Request</option>
<!--      <option value="Archived">Archived</option>-->
    </select>
    <!-- Display the Selected Value -->
    <p>Selected Request Type: {{ newGame.requestStatus }}</p>
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
        requestStatus: "PendingApproval", // Default selected value
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
        requestStatus: "PendingApproval"
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