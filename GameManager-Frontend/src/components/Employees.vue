<template>
  <div class="employee-management">
    <h1>Employee Management</h1>

    <!-- Add Employee Form -->
    <form @submit.prevent="addEmployee" class="employee-form">
      <h2>Add New Employee</h2>
      <label>
        Name:
        <input type="text" v-model="newEmployee.name" required />
      </label>
      <label>
        Email:
        <input type="email" v-model="newEmployee.email" required />
      </label>
      <label>
        Password:
        <input type="password" v-model="newEmployee.password" required />
      </label>
      <button type="submit">Add Employee</button>
    </form>

    <!-- Employee Table -->
    <table class="employee-table" v-if="employees.length">
      <thead>
      <tr>
        <th>Name</th>
        <th>Email</th>
        <th>Actions</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="employee in employees" :key="employee.email">
        <td>{{ employee.name }}</td>
        <td>{{ employee.email }}</td>
        <td>
          <button @click="editEmployee(employee)">Edit</button>
          <button @click="deleteEmployee(employee.email)">Delete</button>
        </td>
      </tr>
      </tbody>
    </table>
    <p v-else>No employees found.</p>

    <!-- Edit Employee Modal -->
    <div v-if="isEditing" class="modal">
      <div class="modal-content">
        <h2>Edit Employee</h2>
        <label>
          Name:
          <input type="text" v-model="currentEmployee.name" />
        </label>
        <label>
          Email:
          <input type="email" v-model="currentEmployee.email" />
        </label>
        <button @click="updateEmployee">Save</button>
        <button @click="cancelEdit">Cancel</button>
      </div>
    </div>
  </div>
</template>


<script>
import axios from "axios";

const axiosClient = axios.create({
  baseURL: "http://localhost:8080", // Replace with your backend API base URL
});

export default {
  name: "ManageEmployees",
  data() {
    return {
      employees: [], // List of employees
      newEmployee: {
        email: "",
        name: "",
        password: "",
      },
      currentEmployee: null, // Employee being edited
      isEditing: false, // Edit modal visibility
    };
  },
  methods: {
    // Fetch all employees
    async fetchEmployees() {
      try {
        const response = await axiosClient.get("/api/employees");
        this.employees = response.data;
      } catch (error) {
        console.error("Error fetching employees:", error);
      }
    },
    // Add a new employee
    async addEmployee() {
      try {
        await axiosClient.post("/api/employees", this.newEmployee);
        this.fetchEmployees(); // Refresh the employee list
        this.newEmployee = { name: "", email: "", position: "" }; // Clear form
      } catch (error) {
        console.error("Error adding employee:", error);
      }
    },
    // Edit an employee
    editEmployee(employee) {
      this.currentEmployee = { ...employee };
      this.isEditing = true;
    },
    // Update an employee
    async updateEmployee() {
      try {
        await axiosClient.put(`/api/employees/${this.currentEmployee.email}`, this.currentEmployee);
        this.isEditing = false;
        this.currentEmployee = null;
        this.fetchEmployees(); // Refresh the employee list
      } catch (error) {
        console.error("Error updating employee:", error);
      }
    },
    // Delete an employee
    async deleteEmployee(email) {
      try {
        await axiosClient.delete(`/api/employees/${email}`);
        this.fetchEmployees(); // Refresh the employee list
      } catch (error) {
        console.error("Error deleting employee:", error);
      }
    },
    // Cancel edit
    cancelEdit() {
      this.isEditing = false;
      this.currentEmployee = null;
    },
  },
  async created() {
    this.fetchEmployees(); // Fetch employees on component load
  },
};
</script>

<style scoped>
.employee-management {
  max-width: 800px;
  margin: 0 auto;
  font-family: Arial, sans-serif;
}

h1 {
  text-align: center;
  margin-bottom: 20px;
}

.employee-form {
  display: flex;
  flex-direction: column;
  gap: 10px;
  margin-bottom: 20px;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 5px;
  background: #f9f9f9;
}

.employee-form label {
  display: flex;
  flex-direction: column;
}

.employee-form button {
  padding: 10px;
  border: none;
  background: #007bff;
  color: white;
  border-radius: 5px;
  cursor: pointer;
}

.employee-form button:hover {
  background: #0056b3;
}

.employee-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

.employee-table th,
.employee-table td {
  border: 1px solid #ddd;
  padding: 10px;
  text-align: left;
}

.employee-table th {
  background: #f4f4f4;
}

.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 5px;
  width: 400px;
  text-align: left;
}
</style>
