<template>
  <div class="orders">
    <h1>Order Summary</h1>
    <table v-if="orders.length" class="order-table">
      <thead>
        <tr>
          <th>Order ID</th>
          <th>Price</th>
          <th>Date</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="order in orders" :key="order.id">
          <td>{{ order.id }}</td>
          <td>${{ order.totalPrice.toFixed(2) }}</td>
          <td>{{ formatDate(order.date) }}</td>
        </tr>
      </tbody>
    </table>
    <p v-else>No orders available.</p>
  </div>
</template>

<script>
import axios from "axios";
const axiosClient = axios.create({
  baseURL: "http://localhost:8080", // Backend API base URL
});

export default {
  name: "Orders",
  data() {
    return {
      orders: [], // Array to store fetched orders
    };
  },
  methods: {
    getAllOrders() {
      axiosClient
        .get("/orders")
        .then((response) => {
          this.orders = response.data;
        })
        .catch((error) => {
          console.error("Failed to fetch orders:", error);
        });
    },
  },
  created() {
    this.getAllOrders();
  },
};
</script>
