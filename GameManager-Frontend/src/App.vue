<template>
  <div id="app">
    <nav>
      <router-link to="/">Home</router-link>
      <router-link to="/create-game">Create Game</router-link>
    </nav>
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App',
};
</script>

<style>
nav {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}
</style>
