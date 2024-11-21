<template>
  <div id="app">
    <nav>
      <router-link to="/">Home</router-link>
      <router-link to="/create-game">Create Game</router-link>
      <router-link to="/login">Login</router-link>
    </nav>
    <router-view />
  </div>
</template>

<script>
export default {
  name: "App",
};
</script>

<style>
/* Basic styling for the navigation bar */
nav {
  display: flex;
  gap: 1rem;
  padding: 1rem;
  background-color: #f8f9fa;
}

a {
  text-decoration: none;
  color: #007bff;
}

a:hover {
  text-decoration: underline;
}
</style>
