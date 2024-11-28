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
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import axios from 'axios';

export default {
  name: 'GameDetails',
  setup() {
    const route = useRoute();
    const game = ref(null);
    const isCustomer = sessionStorage.getItem('customer') !== null;

    const fetchGameDetails = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/games/${route.params.id}/details`);
        // Map only the required fields to the game object
        const { title, description, genre, price, stock } = response.data;
        game.value = { title, description, genre, price, stock };
      } catch (error) {
        console.error('Error fetching game details:', error);
      }
    };

    const addThisToWishList = async () => {
      console.log('Adding to wishlist:', game.value);
      if(isCustomer){
        try{
          const user = sessionStorage.getItem('customer')
          await axios.put(`http://localhost:8080/api/games/addWishList/${game.value.id}`, user)
        }catch(err){
          console.error('Error adding wishlist:', err);
        }
      }
      // Add logic for adding the game to the wishlist
    };

    const addThisToCart = async () => {
      console.log('Adding to cart:', game.value);
      // Add logic for adding the game to the cart
      if(isCustomer){
        try{
          const user = sessionStorage.getItem('customer')
          await axios.put(`http://localhost:8080/api/games/addCart/${game.value.id}`, user)
        }catch(err){
          console.error('Error adding cart:', err);
        }
      }
    };

    // TODO: add addReview function in the game detail page

    onMounted(fetchGameDetails);

    return { game, addThisToWishList, addThisToCart };
  },
};
</script>