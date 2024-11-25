<template>
  <div class="auth-container">
    <div class="auth-card">
      <!-- Logo Section -->
      <div class="logo-section">
        <div class="logo">
          <i class="fas fa-gamepad"></i>
        </div>
        <h1>Welcome Back!</h1>
        <p class="subtitle">Sign in to continue to your account</p>
      </div>

      <!-- Login Form -->
      <form @submit.prevent="handleLogin" class="login-form" v-if="!showSignup">
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

        <button type="button" @click="showSignup = true" class="btn-secondary">
          Create Account
          <i class="fas fa-user-plus"></i>
        </button>
      </form>

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
export default {
  name: 'LoginPage',
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
.auth-container {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20px;
}

.auth-card {
  background: white;
  border-radius: 20px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px;
  padding: 40px;
  animation: slideUp 0.5s ease-out;
}

.logo-section {
  text-align: center;
  margin-bottom: 30px;
}

.logo {
  width: 80px;
  height: 80px;
  margin: 0 auto 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #6b46c1, #805ad5);
  border-radius: 50%;
  color: white;
  font-size: 2.5rem;
  box-shadow: 0 4px 15px rgba(107, 70, 193, 0.2);
  transition: transform 0.3s ease;
}

.logo:hover {
  transform: scale(1.05);
}

h1 {
  color: #2d3748;
  font-size: 1.8rem;
  margin-bottom: 10px;
}

.subtitle {
  color: #718096;
  font-size: 1rem;
}

.form-group {
  margin-bottom: 20px;
}

.password-label {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
}

.forgot-password {
  color: #6b46c1;
  font-size: 0.875rem;
  text-decoration: none;
  transition: color 0.3s ease;
}

.forgot-password:hover {
  color: #553c9a;
}

label {
  display: block;
  color: #4a5568;
  margin-bottom: 8px;
  font-weight: 500;
}

label i {
  margin-right: 8px;
  color: #6b46c1;
}

.input-wrapper {
  position: relative;
}

input {
  width: 100%;
  padding: 12px 16px;
  border: 2px solid #e2e8f0;
  border-radius: 10px;
  font-size: 1rem;
  transition: all 0.3s ease;
}

input:focus {
  border-color: #6b46c1;
  box-shadow: 0 0 0 3px rgba(107, 70, 193, 0.1);
  outline: none;
}

input.error {
  border-color: #f56565;
}

.toggle-password {
  position: absolute;
  right: 12px;
  top: 50%;
  transform: translateY(-50%);
  background: none;
  border: none;
  color: #718096;
  cursor: pointer;
  padding: 0;
}

.remember-me {
  margin-bottom: 20px;
}

.checkbox-label {
  display: flex;
  align-items: center;
  cursor: pointer;
}

.custom-checkbox {
  width: 18px;
  height: 18px;
  border: 2px solid #e2e8f0;
  border-radius: 4px;
  margin-right: 8px;
  position: relative;
  transition: all 0.3s ease;
}

input[type="checkbox"] {
  display: none;
}

input[type="checkbox"]:checked + .custom-checkbox {
  background-color: #6b46c1;
  border-color: #6b46c1;
}

input[type="checkbox"]:checked + .custom-checkbox::after {
  content: '\f00c';
  font-family: 'Font Awesome 5 Free';
  font-weight: 900;
  color: white;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 12px;
}

.btn-primary {
  width: 100%;
  padding: 12px;
  background: linear-gradient(to right, #6b46c1, #805ad5);
  color: white;
  border: none;
  border-radius: 10px;
  font-size: 1rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 8px;
}

.btn-primary:hover {
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(107, 70, 193, 0.2);
}

.btn-primary:disabled {
  background: #a0aec0;
  cursor: not-allowed;
  transform: none;
}

.btn-secondary {
  width: 100%;
  padding: 12px;
  background: white;
  color: #6b46c1;
  border: 2px solid #6b46c1;
  border-radius: 10px;
  font-size: 1rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 8px;
}

.btn-secondary:hover {
  background: #f7fafc;
}

.divider {
  text-align: center;
  margin: 20px 0;
  position: relative;
}

.divider::before,
.divider::after {
  content: '';
  position: absolute;
  top: 50%;
  width: 45%;
  height: 1px;
  background-color: #e2e8f0;
}

.divider::before {
  left: 0;
}

.divider::after {
  right: 0;
}

.divider span {
  background: white;
  padding: 0 10px;
  color: #718096;
  font-size: 0.875rem;
}

.spinner {
  width: 20px;
  height: 20px;
  border: 2px solid white;
  border-top-color: transparent;
  border-radius: 50%;
  animation: spin 0.8s linear infinite;
}

@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}

.error-message {
  color: #f56565;
  font-size: 0.875rem;
  margin-top: 5px;
  display: flex;
  align-items: center;
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
  z-index: 1000;
}

.modal-content {
  background: white;
  padding: 30px;
  border-radius: 15px;
  width: 90%;
  max-width: 400px;
  text-align: center;
}

.modal-icon {
  font-size: 3rem;
  margin-bottom: 20px;
}

.modal-icon.error {
  color: #f56565;
}

@keyframes slideUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>