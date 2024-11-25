<template>
  <div class="cart-page">
    <h1>My Cart</h1>
    <div v-for="game in cart" :key="game.gameId" class="game-box">
      <div class="game-details">
        <h2>{{ game.title }}</h2>
        <p>Price: ${{ game.price }}</p>
        <div class="quantity-controls">
          <button @click="decreaseQuantity(game)">-</button>
          <span>{{ game.quantity }}</span>
          <button @click="increaseQuantity(game)">+</button>
        </div>
        <p>${{ (game.quantity * game.price).toFixed(2) }}</p>
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
import Payment from "@/components/Payment.vue";

export default {
  name: "cart",
  data() {
    return {
      customer: {
        name:"",
        email:"",
        password:"",
      },
      cart: [],
    };
  },
  async created() {
    try {
      const routes = [
        { path: '/payment', component: Payment },
      ]
      this.customer = JSON.parse(sessionStorage.getItem("customer"));
      console.log(this.customer.email);
      // axios.post()
      axios.get(`/customers/${this.customer.email}/cartAll`) // API call to the Controller
      .then(response => {this.cart = response.data;});
    } catch (error) {
      console.error("Error fetching cart games:", error);
    }
  },
  computed: {
    totalCartPrice() {
      return this.cart.reduce((total, game) => total + game.price * game.quantity, 0).toFixed(2);
    },
  },
  methods: {
    increaseQuantity(game) {
      try {
        game.quantity++;
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
        if (game.quantity > 1) game.quantity--;
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
      // Get payment details from localStorage
      const paymentDetails = JSON.parse(localStorage.getItem("paymentDetails"));

      // Check if payment details exist
      if (paymentDetails && paymentDetails.customer === this.customer.email) {
        if (this.totalCartPrice > 0) {
          alert("Proceeding to checkout with total: $" + this.totalCartPrice);
        } else {
          alert("The price is zero. Please add items to your cart.");
        }
      } else {
        this.$router.push("/payment");
      }
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
