<template>
  <div>
    <input type="text" placeholder="Name" v-model="newCategory.name" />
    <input type="text" placeholder="Description" v-model="newCategory.description" />

    <button @click="createCategory" :disabled="!isCategoryValid()">Create Category</button>
    <button @click="clearInputs">Clear</button>
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
      newCategory: {
        name: "",
        description: "",
      },
    };
  },
  methods: {
    async createCategory() {
      try {
        const response = await axiosClient.post("/api/categories", this.newCategory);
        console.log("Category created:", response.data);
        this.$router.push({ name: "CategoryList" });
      } catch (e) {
        console.error("Error creating category:", e);
      }
    },
    clearInputs() {
      this.newCategory = {
        name: "",
        description: "",
      };
    },
    isCategoryValid() {
      return (
          this.newCategory.name &&
          this.newCategory.description
      );
    },
  },
};
</script>

<style>
select {
  padding: 5px 10px;
  font-size: 16px;
  border: 1px solid #ddd;
  border-radius: 5px;
}

label {
  margin-right: 10px;
  font-weight: bold;
}
</style>