<template>
  <div v-if="game">
    <h1>{{ game.title }}</h1>
    <p>{{ game.description }}</p>
    <p>Genre: {{ game.genre }}</p>
    <p>Price: ${{ game.price }}</p>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import axios from 'axios';

export default {
  name: 'GameDetails',
  setup() {
    const route = useRoute();
    const game = ref(null);

    onMounted(async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/games/${route.params.id}`);
        game.value = response.data;
      } catch (error) {
        console.error('Error fetching game details:', error);
      }
    });

    return { game };
  },
};
</script>
