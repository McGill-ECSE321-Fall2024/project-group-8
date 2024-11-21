<template>
  <div>
    <div v-if="loading" class="login-spinner">
    </div>
    <div v-else class="login">
      <div class="login-form">
        <h1>{{ signState }}</h1>
        <form @submit.prevent="userAuth">
          <input
              v-if="signState === 'Sign Up'"
              v-model="name"
              type="text"
              placeholder="Your name"
          />
          <input
              v-model="email"
              type="email"
              placeholder="Email"
          />
          <input
              v-model="password"
              type="password"
              placeholder="Password"
          />
          <button type="submit">{{ signState }}</button>
          <div class="form-help">
            <div class="remember">
              <input type="checkbox" id="remember-me" />
              <label for="remember-me">Remember Me</label>
            </div>
            <p>Need Help?</p>
          </div>
        </form>
        <div class="form-switch">
          <p v-if="signState === 'Sign In'">
            New here?
            <span @click="setSignState('Sign Up')">Sign Up Now</span>
          </p>
          <p v-else>
            Already have an account?
            <span @click="setSignState('Sign In')">Sign In Now</span>
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue';
import axios from "axios";

export default {
  name: 'Login',
  setup() {
    const signState = ref('Sign In');
    const name = ref('');
    const email = ref('');
    const password = ref('');
    const loading = ref(false);

    const logo = ref('/Login');
    const spinner = ref('/SignIn');

    const setSignState = (state) => {
      signState.value = state;
    };

    const userAuth = async () => {
      loading.value = true;
      try {
        if (signState.value === 'Sign In') {
          console.log('Logging in:', email.value, password.value);
        } else {
          console.log('Signing up:', name.value, email.value, password.value);
        }
      } catch (error) {
        console.error('Authentication error:', error);
      } finally {
        loading.value = false;
      }
    };

    return {
      signState,
      name,
      email,
      password,
      loading,
      setSignState,
      userAuth,
      logo,
      spinner,
    };
  },
};
</script>

<style>
.login{
  height: 100vh;
  padding: 20px 8%;
}

.login-form{
  width: 100%;
  max-width: 450px;
  background: rgba(0,0,0,0.75);
  border-radius: 4px;
  padding: 60px;
  margin: auto;
}
.login-form h1{
  font-size: 32px;
  font-weight: 500;
  margin-bottom: 28px;
}
.login-form input{
  width: 100%;
  height: 50px;
  background: #333;
  color:white;
  margin: 12px 0;
  border: 0;
  outline: 0;
  border-radius: 4px;
  padding: 16px 20px;
  font-size: 16px;
  font-weight: 500;
}
.login-form input::placeholder{
  font-size: 16px;
  font-weight: 500;
}
.login-form button{
  width: 100%;
  border: 0;
  outline: 0;
  padding: 16px;
  background: #3EB489;
  color: white;
  border-radius: 4px;
  font-size: 16px;
  font-weight: 500;
  margin-top: 20px;
  cursor: pointer;
}
.form-help{
  display: flex;
  align-items: center;
  justify-content: space-between;
  color: #3EB489;
  font-size: 13px;
}
.remember{
  display: flex;
  align-items: center;
  gap: 5px;
}
.remember input{
  width: 18px;
  height: 18px;
}
.form-switch{
  margin-top: 40px;
  color: #3EB489
}
.form-switch span{
  margin-left: 6px;
  color: #fff;
  font-weight: 500;
  cursor: pointer;
}
.login-spinner{
  width: 100%;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
}
.login-spinner img{
  width: 60px;
}
@media (max-width: 500px) {
  .login{
    padding: 15px 5%;
  }
  .login-form{
    padding: 20px;
    margin-top: 30px;
  }
}
</style>
