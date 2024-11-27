<template>
  <div v-if="game">
    <h1>{{ game.title }}</h1>
    <p>{{ game.description }}</p>
    <p>Genre: {{ game.genre }}</p>
    <p>Price: ${{ game.price }}</p>
    <p>Stock: {{ game.stock }}</p>
    <button @click="addThisToWishList">Add to Wishlist</button>
    <button @click="addThisToCart">Add to Cart</button>
  </div>
</template>

<script>
import {ref, onMounted} from 'vue';
import {useRoute} from 'vue-router';
import axios from 'axios';

export default {
  name: 'GameDetails',
  setup() {
    const route = useRoute();
    const game = ref(null);

    const fetchGameDetails = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/games/${route.params.id}/details`);
        game.value = response.data;
      } catch (error) {
        console.error('Error fetching game details:', error);
      }
    };

    const addThisToWishList = () => {
      console.log('Adding to wishlist:', game.value);
      // Add logic for adding the game to the wishlist
    };

    const addThisToCart = () => {
      console.log('Adding to cart:', game.value);
      // Add logic for adding the game to the cart
    };

    onMounted(fetchGameDetails);

    return {game, addThisToWishList, addThisToCart};
  },
};
</script>
