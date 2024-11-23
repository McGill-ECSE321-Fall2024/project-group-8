<template>
  <div class="cart-page">
    <h1>My Cart</h1>
    <div v-for="game in cart" :key="game.gameId" class="game-box">
      <div class="game-details">
        <h2>{{ game.title }}</h2>
        <p>Price: ${{ game.price }}</p>
        <div class="quantity-controls">
          <button @click="decreaseQuantity(game)">-</button>
          <span>{{ getQuantity(game.gameId) }}</span>
          <button @click="increaseQuantity(game)">+</button>
        </div>
        <p>${{ getQuantity(game.gameId) * game.price }}</p>
      </div>
    </div>
    <div class="checkout-box">
      <h2>Checkout</h2>
      <p>Total Price: ${{ totalCartPrice }}</p>
      <button @click="checkout">Proceed to Checkout</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "cart",
  data() {
    return {
      customer: null,
      // cart: [],
      cart : [
        { gameId: "01", price: 0.1, title: "Chess" },
        { gameId: "02", price: 0.2, title: "Checkers" },
        { gameId: "01", price: 0.1, title: "Chess" },
      ],
    };
  },
  async created() {
    try {
      let user_name = sessionStorage.getItem("name");
      let user_email = sessionStorage.getItem("email");
      let user_psw = sessionStorage.getItem("password");
      this.customer = {
        name:user_name,
        email: user_email,
        password: user_psw
      }
      axios.get(`/customers/${user_email}/cartAll`) // API call to the Controller
      .then();//response => {this.cart = response.data;});
    } catch (error) {
      console.error("Error fetching cart games:", error);
    }
  },
  computed: {
    totalCartPrice() {
      return this.cart.reduce((total, game) => total + game.price * this.getQuantity(game.gameId), 0);
    },
  },
  methods: {
    getQuantity(gameId){
      let quantity = 0;
      for (const game of this.cart) {
        if (game.gameId === gameId) {
          quantity++; // Increment count if the gameId matches
        }
      }
      return quantity;
    },
    increaseQuantity(game) {
      try {
        const response = axios.put(`/customers/addCart/${game.gameId}`, this.customer, {
          headers: { 'Content-Type': 'application/json'}
        });
        return response.data;
      } catch (error) {
        console.error('Error removing item from cart:', error.response?.data || error.message);
        throw error; // Handle or rethrow the error
      }
    },
    decreaseQuantity(game) {
      try {
        const response = axios.put(`/customers/removeInCart/${game.gameId}`, this.customer, {
          headers: { 'Content-Type': 'application/json'}
        });
        return response.data;
      } catch (error) {
        console.error('Error removing item from cart:', error.response?.data || error.message);
        throw error; // Handle or rethrow the error
      }
    },
    checkout() {
      alert("Proceeding to checkout with total: $" + this.totalCartPrice);
    },
  },
};
</script>

<style scoped>
.cart-page {
  padding: 20px;
}

.game-box {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
  border: 1px solid #ccc;
  padding: 10px;
}

.game-details {
  flex-grow: 1;
}

.quantity-controls {
  display: flex;
  align-items: center;
  margin: 10px 0;
}

.quantity-controls button {
  margin: 0 5px;
  padding: 5px 10px;
}

.checkout-box {
  border-top: 2px solid #ccc;
  padding-top: 20px;
  margin-top: 20px;
}

.checkout-box button {
  padding: 10px 20px;
  background-color: #007bff;
  color: #fff;
  border: none;
  cursor: pointer;
}

.checkout-box button:hover {
  background-color: #0056b3;
}
</style>
