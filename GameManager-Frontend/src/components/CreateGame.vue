<template>
    <div>
      <input
        type="text"
        placeholder="Title"
        v-model="newGame.title"
        style="color: black;"
      />
      <input
        type="text"
        placeholder="Description"
        v-model="newGame.description"
        style="color: black;"
      />
      <input
        type="text"
        placeholder="Genre"
        v-model="newGame.genre"
        style="color: black;"
      />
      <input
        type="number"
        placeholder="Price"
        v-model="newGame.price"
        style="color: black;"
      />
      <input
        type="number"
        placeholder="Stock"
        v-model="newGame.stock"
        style="color: black;"
      />
      <button
        @click="createGame"
        :disabled="!isGameValid()"
        style="color: black;"
      >
        Create Game
      </button>
      <button @click="clearInputs" style="color: black;">
        Clear
      </button>
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
