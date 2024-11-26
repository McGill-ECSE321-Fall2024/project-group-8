<template>
  <div class="user-profile">
    <h1>User Profile</h1>

    <!-- Logout Button -->
    <button class="logout-button" @click="logout">Log Out</button>

    <!-- User Information Section -->
    <section class="user-info">
      <h2>Profile Information</h2>
      <div class="info-row">
        <label for="username">Username:</label>
        <span>{{ user.username }}</span>
        <button @click="updateUsername">Update Username</button>
      </div>
      <div class="info-row">
        <label for="email">Email:</label>
        <span>{{ user.email }}</span>
      </div>
      <div class="info-row">
        <label for="password">Password:</label>
        <button @click="updatePassword">Update Password</button>
      </div>
    </section>

    <!-- Review History Section -->
    <section class="review-history">
      <h2>Review History</h2>
      <div v-if="displayedReviews.length > 0">
        <ul>
          <li v-for="review in displayedReviews" :key="review.reviewId">
            <p><strong>{{ review.gameTitle }}</strong> ({{ review.rating }}/5)</p>
            <p>{{ review.description }}</p>
            <p><em>Created on: {{ review.creationDate }}</em></p>
          </li>
        </ul>
        <button v-if="reviews.length > displayedReviews.length" @click="showMoreReviews">Show More</button>
      </div>
      <p v-else>No reviews yet.</p>
    </section>

    <!-- Orders Section -->
    <section class="orders">
      <h2>Orders</h2>
      <div v-if="displayedOrders.length > 0">
        <ul>
          <li v-for="order in displayedOrders" :key="order.orderId">
            <strong>Status:</strong> {{ order.orderStatus }} <br />
            <strong>Date:</strong> {{ order.date }} <br />
            <strong>Price:</strong> ${{ order.price }}
          </li>
        </ul>
        <button v-if="orders.length > displayedOrders.length" @click="showMoreOrders">Show More</button>
      </div>
      <p v-else>No orders found.</p>
    </section>
  </div>
</template>

<script>
import axios from "axios";
const axiosClient = axios.create({
  // NOTE: it's baseURL, not baseUrl
  baseURL: "http://localhost:8080"
});
export default {
  name: "UserProfile",
  data() {
    return {
      user: {
        username: "",
        email: "",
        password: "",
      },
      reviews: [
        {
          reviewId: 0,       // Placeholder for review ID
          gameTitle: "",        // Placeholder for game title
          rating: 0,         // Placeholder for rating
          description: "",      // Placeholder for review description
          creationDate: "",     // Placeholder for creation date
        },
      ], // Full list of reviews
      displayedReviews: [], // Displayed subset of reviews
      orders: [
        {
          orderId: 0,
          orderStatus: "",
          date: "",
          price: 0.0,
        }
      ], // Full list of orders
      displayedOrders: [], // Displayed subset of orders
      itemsToShow: 3, // Number of items to show initially
      personRole:""

    };
  },
  methods: {
    fetchPersonRole(){
      const isOwner = sessionStorage.getItem('owner') !== null;
      const isEmployee = sessionStorage.getItem('employee') !== null;
      const isCustomer = sessionStorage.getItem('customer') !== null;
      if (isOwner) {
        this.personRole = "Owner";
      } else if(isEmployee) {
        this.personRole = "Employee";
      } else if(isCustomer) {
        this.personRole = "Customer";
      }
    },
    fetchUserData() {

      if (this.personRole === "Owner") {
        this.user = JSON.parse(sessionStorage.getItem("Owner"));
      } else if (this.personRole === "Employee") {
        this.user = JSON.parse(sessionStorage.getItem("Employee"));
      } else if (this.personRole === "Customer") {
        this.user = JSON.parse(sessionStorage.getItem("Customer"));
      }
    },
    async fetchReviewHistory() {
      if (this.personRole === "Customer") {
        try {
          const response = await axiosClient.get(`/customers/${this.user.email}/reviews`);
          this.reviews = response.data; // Assume backend returns reviews
          this.displayedReviews = this.reviews.slice(0, this.itemsToShow);
        } catch (error) {
          console.error("Error fetching reviews:", error);
        }
      }
      /*
      this.reviews = [
       // sample data
      { id: 1, gameTitle: "Game A", comment: "Loved it!", rating: 5 },
      { id: 2, gameTitle: "Game B", comment: "Pretty good.", rating: 4 },
      { id: 3, gameTitle: "Game C", comment: "Not bad.", rating: 3 },
      { id: 4, gameTitle: "Game D", comment: "Could be better.", rating: 2 },
    ];
       */
      // TODO: Fetch all reviews from backend API and update 'reviews'

      //this.displayedReviews = this.reviews.slice(0, this.itemsToShow);
    },
    async fetchOrders() {
      try {
        const response = await axiosClient.get(`/customers/allOrder/${this.user.email}`);
        this.orders = response.data; // Assume backend returns orders
        this.displayedOrders = this.orders.slice(0, this.itemsToShow);
      } catch (error) {
        console.error("Error fetching orders:", error);
      }
      // TODO: Fetch all orders from backend API and update 'orders'
      /*
      this.orders = [
        // Sample data
        { id: 1, date: "2024-11-01", total: 59.99 },
        { id: 2, date: "2024-11-10", total: 39.99 },
        { id: 3, date: "2024-11-15", total: 19.99 },
        { id: 4, date: "2024-11-20", total: 99.99 },
      ];
       */
      //this.displayedOrders = this.orders.slice(0, this.itemsToShow);
    },
    showMoreReviews() {
      const nextItems = this.displayedReviews.length + this.itemsToShow;
      this.displayedReviews = this.reviews.slice(0, nextItems);
    },
    showMoreOrders() {
      const nextItems = this.displayedOrders.length + this.itemsToShow;
      this.displayedOrders = this.orders.slice(0, nextItems);
    },
    async updateUsername() {
      try{
        if (this.personRole === "Owner") {
          await axiosClient.put(`/api/owners/${this.user.email}`, this.user);
        } else if (this.personRole === "Employee") {
          await axiosClient.put(`/api/employees/${this.user.email}`, this.user);
        } else if (this.personRole === "Customer") {
          await axiosClient.put(`/customers/${this.user.email}`, this.user);
        }
      } catch (error) {
        console.error("Error updating username:", error);
      }

      // TODO: In html, add an another input box to take username to be updated.
      console.log("Update username clicked");
    },
    async updatePassword() {
      try {
        if (this.personRole === "Owner") {
          await axiosClient.put(`/api/owners/${this.user.email}`, this.user);
        } else if (this.personRole === "Employee") {
          await axiosClient.put(`/api/employees/${this.user.email}`, this.user);
        } else if (this.personRole === "Customer") {
          await axiosClient.put(`/customers/${this.user.email}`, this.user);
        }
      } catch (error) {
        console.error("Error updating password:", error);
      }
      // TODO: In html, add an another input box to take password to be updated.
      console.log("Update password clicked");
    },
    logout() {
      sessionStorage.clear();
      console.log("Log out user");
      window.location.href = "/login"; // Update this path as per your routing setup
    },
  },
  mounted() {
    this.fetchPersonRole();
    this.fetchUserData();
    this.fetchReviewHistory();
    this.fetchOrders();
  },
};
</script>

<style scoped>
.user-profile {
  font-family: Arial, sans-serif;
  padding: 20px;
  max-width: 800px;
  margin: auto;
}

h1, h2 {
  color: #333;
}

.logout-button {
  float: right;
  margin: 10px;
  padding: 10px 20px;
  background-color: #2196f3;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.logout-button:hover {
  background-color: #1976d2;
}

.info-row {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

label {
  font-weight: bold;
  margin-right: 10px;
}

button {
  margin-left: 10px;
  padding: 5px 10px;
  background-color: #4caf50;
  color: white;
  border: none;
  border-radius: 3px;
  cursor: pointer;
}

button:hover {
  background-color: #388e3c;
}

section {
  margin-bottom: 30px;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  margin: 5px 0;
}
</style>
