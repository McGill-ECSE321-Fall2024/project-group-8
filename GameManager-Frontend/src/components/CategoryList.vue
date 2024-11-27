<template>
  <div>
    <h1>Category List</h1>
    <!-- Table to display categories -->
    <table>
      <thead>
      <tr>
        <th>Name</th>
        <th>Description</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="category in categories" :key="category.categoryId">
        <td>{{ category.name }}</td>
        <td>{{ category.description }}</td>
        <td>
          <button @click="editCategory(category.categoryId)">Edit</button>
          <button @click="deleteCategory(category.categoryId)">Delete</button>
        </td>
      </tr>
      </tbody>
    </table>

    <!-- Button to navigate to create category -->
    <button @click="createCategory">Create New Category</button>
  </div>
</template>

<script>
import axios from "axios";

const axiosClient = axios.create({
  baseURL: "http://localhost:8080", // Backend API base URL
});

export default {
  name: "CategoryList",
  data() {
    return {
      categories: [], // Array to store categories fetched from backend
    };
  },

  async created() {
    try {
      // fetch categories
      const response = await axiosClient.get("/api/categories");
      this.categories = response.data;
    } catch (e) {
      console.error("Error fetching categories:", e); // Log any errors
    }
  },
  methods: {
    createCategory() {
      this.$router.push({ name: "CreateCategory" });
    },
    editCategory(categoryId) {
      this.$router.push({ name: "UpdateCategory", params: { id: categoryId } });
    },
    async deleteCategory(categoryId) {
      if (!confirm("Are you sure you want to delete this category?")) {
        return;
      }
      try {
        await axiosClient.delete(`/api/categories/${categoryId}`);
        this.categories = this.categories.filter((category) => category.categoryId !== categoryId);
        alert("Category deleted successfully!");
      } catch (error) {
        console.error("Error deleting category:", error);
        alert("Failed to delete category.");
      }
    },
  },
};

</script>

<style>
/* Add some basic styling */
table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 20px;
}

table th,
table td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

table th {
  background-color: #f2f2f2;
}

button {
  margin: 5px;
  padding: 10px;
  cursor: pointer;
}
</style>
