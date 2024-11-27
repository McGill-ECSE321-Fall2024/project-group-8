<template>
  <div class="app" id="app">
    <nav class="rounded-navbar">
      <ul class="nav-menu" id="navMenu">
        <li
            v-for="(item, index) in menuItems"
            :key="index"
            :class="{ active: selectedItem === item }"
            @click="selectItem(item, index)"
            class="nav-item"
        >
          <a href="javascript:void(0);">{{ item }}</a>
        </li>
      </ul>
    </nav>
    <router-view />
  </div>
</template>

<script>
export default {
  name: "NavigationBar",
  data() {
    return {
      //menuItems:["Home","Search","Log in"],
      //menuItemRef: ['/','/search','/login'],
      // TODO: uncomment this when login function is confirmed
      selectedItem: "Home", // Default selected item

      // Navigation menu items

      menuItems: ["Home", "Create Game", "Create Category", "Category List", "Search", "Cart", "Log in"],
      menuItemRef: ['/', '/create-game', '/create-category', "/category-list", '/search', '/cart', '/login'],

      //menuItems: ["Home", "Create Game", "Categories", "Search", "Cart", "Log in"],
      //menuItemRef: ['/', '/create-game', '/category', '/search', '/cart', '/login'],

      // Track the selected item
    };
  },
  mounted() {
    this.updateMenuItems();
  },
  methods: {
    // Update the selected item
    selectItem(item, index) {
      this.selectedItem = item;
      this.$router.push(this.menuItemRef[index]);
    },
    // Update menu items based on session storage
    updateMenuItems() {
      const isOwner = sessionStorage.getItem('owner') === null;
      const isEmployee = sessionStorage.getItem('employee') === null;
      const isCustomer = sessionStorage.getItem('customer') === null;
      if (isOwner) {
        this.menuItems = ["Home", "Store Information","Profile", "Create Game", "Categories","Employees","Search", "Log out"];
        this.menuItemRef = ["/", "/Store-Information","/profile", "/create-game", "/category", "/employees","/", "/logout"];
      } else if(isEmployee) {
        this.menuItems = ["Home", "Create Game", "Customer","Search", "Log out"];
        this.menuItemRef = ["/", "/create-game", "/Customer","/", "/logout"];
      } else if(isCustomer) {
        this.menuItems = ["Home", "Profile", "Search", "Orders", "Cart", "WishList" ,"Log out"];
        this.menuItemRef = ["/", "/profile", "/search", "/order", "/cart" , "/Wish-list","/logout"];
      }
    },
  },
};
</script>

<style>
/* General reset */
body, html {
  margin: 0;
  padding: 0;
  font-family: Arial, sans-serif;
}
.app{
  display: flex;
  flex-direction: column;
}
/* Navigation bar container */
.rounded-navbar {
  position: absolute;
  top: 0;
  left: 100px;
  z-index: 1000;
  display: flex;
  justify-content: center;
  background-color: #f8f9fa; /* Light gray background */
  border-radius: 50px; /* Fully rounded edges */
  padding: 10px 20px; /* Space around the menu items */
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); /* Subtle shadow */
  max-width: 1000px; /* Optional: Limit width for better alignment */
  margin: 20px auto; /* Center the navbar on the page */
}

/* Navigation menu styling */
.nav-menu {
  list-style: none; /* Remove bullets */
  display: flex; /* Align items in a row */
  gap: 20px; /* Space between links */
  margin: 0;
  padding: 0;
}

/* Navigation items */
.nav-item a {
  text-decoration: none; /* Remove underline */
  color: #333; /* Dark text color */
  font-size: 16px;
  font-weight: bold;
  padding: 5px 10px;
  transition: color 0.3s ease;
}

/* Hover effect */
.nav-item a:hover {
  color: #007bff; /* Blue color on hover */
}

/* Active state with a dot indicator */
.nav-item.active a {
  position: relative;
  color: #000; /* Black for the active link */
}

.nav-item.active a::before {
  content: "•"; /* Dot before the active link */
  color: #000;
  position: absolute;
  left: -10px; /* Adjust the position of the dot */
  font-size: 20px;
}

@media (max-width: 600px) {
  .nav-menu {
    flex-direction: column;
    align-items: center;
    gap: 10px; /* Reduce gap between items */
  }
}

.nav-item a:hover {
  background-color: #e9ecef; /* Light gray background */
  border-radius: 5px;
}

.nav-item.active a::before {
  transition: transform 0.3s ease;
  transform: scale(1.2); /* Slightly enlarges the dot */
}
</style>
