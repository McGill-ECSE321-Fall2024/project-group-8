<template>
  <div>
    <input type="text" placeholder="Title" v-model="game.title" />
    <input type="text" placeholder="Description" v-model="game.description" />
    <input type="text" placeholder="Genre" v-model="game.genre" />
    <input type="number" placeholder="Price" v-model="game.price" />
    <input type="number" placeholder="Stock" v-model="game.stock" />
    <button @click="updateGame">Update Game</button>
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
      game: {},
    };
  },
  async created() {
    const gameId = this.$route.params.gameId;
    try {
      const response = await axiosClient.get(`/api/games/${gameId}`);
      this.game = response.data;
    } catch (e) {
      console.error("Error fetching game:", e);
    }
  },
  methods: {
    async updateGame() {
      try {
        const response = await axiosClient.put(`/api/games/${this.game.gameId}`, this.game);
        console.log("Game updated:", response.data);
        this.$router.push({ name: "GameList" });
      } catch (e) {
        console.error("Error updating game:", e);
      }
    },
  },
};
</script>
