<template>
  <div class="auth-container">
    <div class="auth-card" :class="{ 'expanded': showSignup }">
      <!-- Show Login Form -->
      <div v-if="!showSignup" class="form-container">
        <div class="logo-section">
          <div class="logo">
            <i class="fas fa-gamepad"></i>
          </div>
          <h1>Welcome Back!</h1>
          <p class="subtitle">Sign in to continue to your account</p>
        </div>

        <!-- Login Form -->
        <form @submit.prevent="handleLogin" class="login-form">
          <div class="form-group">
            <label for="login-email">
              <i class="fas fa-envelope"></i>
              Email
            </label>
            <div class="input-wrapper">
              <input
                  type="email"
                  id="login-email"
                  v-model="loginForm.email"
                  :class="{ 'error': loginErrors.email }"
                  placeholder="Enter your email"
                  autocomplete="email"
              />
            </div>
            <span class="error-message" v-if="loginErrors.email">{{ loginErrors.email }}</span>
          </div>

          <div class="form-group">
            <div class="password-label">
              <label for="login-password">
                <i class="fas fa-lock"></i>
                Password
              </label>
              <a href="#" @click.prevent="forgotPassword" class="forgot-password">
                Forgot Password?
              </a>
            </div>
            <div class="input-wrapper">
              <input
                  :type="showLoginPassword ? 'text' : 'password'"
                  id="login-password"
                  v-model="loginForm.password"
                  :class="{ 'error': loginErrors.password }"
                  placeholder="Enter your password"
                  autocomplete="current-password"
              />
              <button
                  type="button"
                  class="toggle-password"
                  @click="showLoginPassword = !showLoginPassword"
              >
                <i :class="showLoginPassword ? 'fas fa-eye-slash' : 'fas fa-eye'"></i>
              </button>
            </div>
            <span class="error-message" v-if="loginErrors.password">{{ loginErrors.password }}</span>
          </div>

          <div class="remember-me">
            <label class="checkbox-label">
              <input type="checkbox" v-model="loginForm.rememberMe" />
              <span class="custom-checkbox"></span>
              Remember me
            </label>
          </div>

          <button type="submit" class="btn-primary" :disabled="isLoggingIn">
            <span v-if="!isLoggingIn">
              Sign In
              <i class="fas fa-sign-in-alt"></i>
            </span>
            <div class="spinner" v-else></div>
          </button>

          <div class="divider">
            <span>Don't have an account?</span>
          </div>

          <button type="button" @click="toggleSignup" class="btn-secondary">
            Create Account
            <i class="fas fa-user-plus"></i>
          </button>
        </form>
      </div>

      <!-- Show Create Account Form -->
      <transition name="fade">
        <div v-if="showSignup" class="form-container">
          <div class="back-button" @click="toggleSignup">
            <i class="fas fa-arrow-left"></i>
            Back to Login
          </div>
          <CreateAccountForm @registration-success="handleRegistrationSuccess" />
        </div>
      </transition>

      <!-- Error Modal -->
      <div v-if="showErrorModal" class="modal" @click.self="closeErrorModal">
        <div class="modal-content error-modal">
          <div class="modal-icon error">
            <i class="fas fa-exclamation-circle"></i>
          </div>
          <h3>Login Failed</h3>
          <p>{{ errorMessage }}</p>
          <button @click="closeErrorModal" class="btn-primary">
            <i class="fas fa-times"></i>
            Close
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import CreateAccountForm from './CreateAccountForm.vue'
import axios from "axios";

export default {
  name: 'LoginPage',
  components: {
    CreateAccountForm
  },
  data() {
    return {
      loginForm: {
        email: '',
        password: '',
        rememberMe: false
      },
      loginErrors: {},
      isLoggingIn: false,
      showLoginPassword: false,
      showErrorModal: false,
      errorMessage: '',
      showSignup: false
    }
  },
  methods: {
    toggleSignup() {
      this.showSignup = !this.showSignup;
      this.loginErrors = {};
      this.loginForm = {
        email: '',
        password: '',
        rememberMe: false
      };
    },

    handleRegistrationSuccess() {
      this.showSignup = false;
      // Optionally pre-fill the login form with the registered email
      this.loginForm.email = this.registeredEmail;
      // Show success message
      this.showSuccess('Account created successfully! Please sign in.');
    },

    async handleLogin() {
      this.loginErrors = {};

      // Validate form
      if (!this.loginForm.email) {
        this.loginErrors.email = 'Email is required';
      }
      if (!this.loginForm.password) {
        this.loginErrors.password = 'Password is required';
      }

      if (Object.keys(this.loginErrors).length > 0) {
        return;
      }

      this.isLoggingIn = true;

      try {
        // Implement your login API call here
        const response = await this.loginUser(this.loginForm);

        if (response.success) {
          // Handle successful login
          this.$router.push('/dashboard'); // or wherever you want to redirect
        } else {
          this.showError('Invalid email or password');
        }
      } catch (error) {
        this.showError('An error occurred during login. Please try again.');
        console.error('Login error:', error);
      } finally {
        this.isLoggingIn = false;
      }
    },

    async loginUser(credentials) {
      // Implement your login API call here



      return { success: true }; // Placeholder
    },



    forgotPassword() {
      // Implement forgot password flow
    },

    showError(message) {
      this.errorMessage = message;
      this.showErrorModal = true;
    },

    closeErrorModal() {
      this.showErrorModal = false;
      this.errorMessage = '';
    }
  }
}
</script>

<style scoped>
/* Previous styles remain the same */

/* Add these new styles */
.auth-card {
  transition: all 0.3s ease;
}

.auth-card.expanded {
  max-width: 600px; /* Wider card for the registration form */
}

.form-container {
  position: relative;
}

.back-button {
  position: absolute;
  top: -20px;
  left: 0;
  color: #6b46c1;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 8px;
  font-weight: 500;
  transition: color 0.3s ease;
}

.back-button:hover {
  color: #553c9a;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>