```vue
<template>
  <div class="auth-container">
    <!-- Animated Background -->
    <div class="background-animations">
      <div class="circle circle-1"></div>
      <div class="circle circle-2"></div>
      <div class="circle circle-3"></div>
      <div class="square square-1"></div>
      <div class="square square-2"></div>
    </div>

    <div class="auth-card">
      <!-- Decorative Elements -->
      <div class="decorative-dots dots-left">
        <div v-for="i in 5" :key="`left-${i}`" class="dot"></div>
      </div>
      <div class="decorative-dots dots-right">
        <div v-for="i in 5" :key="`right-${i}`" class="dot"></div>
      </div>

      <!-- Login Form Container -->
      <div class="login-container">
        <!-- Logo and Header -->
        <div class="form-header">
          <div class="logo-wrapper">
            <div class="logo-bg-circle"></div>
            <div class="logo">
              <i class="fas fa-gamepad"></i>
            </div>
          </div>
          <h1>Welcome Back!</h1>
          <p class="subtitle">Sign in to continue your gaming journey</p>
        </div>

        <!-- Login Form -->
        <form @submit.prevent="handleLogin" class="login-form">
          <div class="form-group">
            <label for="email">
              <i class="fas fa-envelope"></i>
              Email
            </label>
            <div class="input-wrapper">
              <input
                  type="email"
                  id="email"
                  v-model="loginForm.email"
                  :class="{ 'error': loginErrors.email }"
                  placeholder="Enter your email"
                  autocomplete="email"
              />
              <span class="input-focus"></span>
            </div>
            <span class="error-message" v-if="loginErrors.email">
              <i class="fas fa-exclamation-circle"></i>
              {{ loginErrors.email }}
            </span>
          </div>

          <div class="form-group">
            <div class="password-label">
              <label for="password">
                <i class="fas fa-lock"></i>
                Password
              </label>
              <a href="#" @click.prevent="forgotPassword" class="forgot-password">
                <i class="fas fa-key"></i>
                Forgot Password?
              </a>
            </div>
            <div class="input-wrapper">
              <input
                  :type="showPassword ? 'text' : 'password'"
                  id="password"
                  v-model="loginForm.password"
                  :class="{ 'error': loginErrors.password }"
                  placeholder="Enter your password"
                  autocomplete="current-password"
              />
              <span class="input-focus"></span>
              <button
                  type="button"
                  class="toggle-password"
                  @click="showPassword = !showPassword"
              >
                <i :class="showPassword ? 'fas fa-eye-slash' : 'fas fa-eye'"></i>
              </button>
            </div>
            <span class="error-message" v-if="loginErrors.password">
              <i class="fas fa-exclamation-circle"></i>
              {{ loginErrors.password }}
            </span>
          </div>

          <div class="form-options">
            <label class="checkbox-label">
              <input type="checkbox" v-model="loginForm.rememberMe" />
              <span class="custom-checkbox">
                <i class="fas fa-check"></i>
              </span>
              Remember me
            </label>
          </div>

          <button type="submit" class="btn-primary" :disabled="isLoggingIn">
            <div class="btn-content">
              <span v-if="!isLoggingIn">
                Sign In
                <i class="fas fa-sign-in-alt"></i>
              </span>
              <div class="spinner" v-else></div>
            </div>
            <div class="btn-glow"></div>
          </button>

          <div class="divider">
            <span>or continue with</span>
          </div>

          <!-- Social Login Buttons -->
          <div class="social-buttons">
            <button type="button" class="social-btn google">
              <i class="fab fa-google"></i>
            </button>
            <button type="button" class="social-btn facebook">
              <i class="fab fa-facebook-f"></i>
            </button>
            <button type="button" class="social-btn twitter">
              <i class="fab fa-twitter"></i>
            </button>
          </div>

          <div class="signup-prompt">
            <div class="divider">
              <span>New to our platform?</span>
            </div>
            <button type="button" @click="goToSignup" class="btn-secondary">
              <div class="btn-content">
                Create Account
                <i class="fas fa-user-plus"></i>
              </div>
            </button>
          </div>
        </form>
      </div>

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
      showPassword: false,
      showErrorModal: false,
      errorMessage: ''
    }
  },
  methods: {
    async handleLogin() {
      this.loginErrors = {};

      // Validate form
      if (!this.loginForm.email) {
        this.loginErrors.email = 'Email is required';
        return;
      }
      if (!this.loginForm.password) {
        this.loginErrors.password = 'Password is required';
        return;
      }

      this.isLoggingIn = true;

      try {
        // Here you would make your API call
        const response = await this.loginUser(this.loginForm);

        if (response.success) {
          localStorage.setItem('token', response.token);
          this.$router.push('/dashboard');
        } else {
          this.showError('Invalid email or password');
        }
      } catch (error) {
        console.error('Login error:', error);
        this.showError('An error occurred during login. Please try again.');
      } finally {
        this.isLoggingIn = false;
      }
    },

    async loginUser(credentials) {
      // Implement your login API call here
      return new Promise((resolve) => {
        setTimeout(() => {
          resolve({
            success: true,
            token: 'dummy-token'
          });
        }, 1000);
      });
    },

    forgotPassword() {
      // Implement forgot password flow
      console.log('Forgot password clicked');
    },

    goToSignup() {
      this.$router.push('/signup');
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

/* Background Animations */
.background-animations {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  overflow: hidden;
  z-index: 0;
  pointer-events: none;
}

.circle, .square {
  position: absolute;
  opacity: 0.1;
}

.circle-1 {
  width: 300px;
  height: 300px;
  background: linear-gradient(45deg, #6b46c1, #805ad5);
  border-radius: 50%;
  top: -150px;
  right: -150px;
  animation: float 8s infinite ease-in-out;
}

.circle-2 {
  width: 200px;
  height: 200px;
  background: linear-gradient(45deg, #805ad5, #6b46c1);
  border-radius: 50%;
  bottom: -100px;
  left: -100px;
  animation: float 6s infinite ease-in-out;
}

.circle-3 {
  width: 150px;
  height: 150px;
  background: linear-gradient(45deg, #6b46c1, #805ad5);
  border-radius: 50%;
  top: 50%;
  right: -75px;
  animation: float 7s infinite ease-in-out;
}

.square {
  border-radius: 20px;
}

.square-1 {
  width: 100px;
  height: 100px;
  background: linear-gradient(45deg, #6b46c1, #805ad5);
  top: 20%;
  left: -50px;
  animation: rotate 10s infinite linear;
}

.square-2 {
  width: 80px;
  height: 80px;
  background: linear-gradient(45deg, #805ad5, #6b46c1);
  bottom: 20%;
  right: -40px;
  animation: rotate 8s infinite linear reverse;
}

.auth-card {
  background: white;
  border-radius: 20px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px;
  padding: 40px;
  position: relative;
  z-index: 1;
  animation: slideUp 0.5s ease-out;
}

.logo-wrapper {
  position: relative;
  width: 100px;
  height: 100px;
  margin: 0 auto 20px;
}

.logo {
  width: 80px;
  height: 80px;
  background: linear-gradient(135deg, #6b46c1, #805ad5);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 2.5rem;
  position: relative;
  z-index: 1;
  margin: 0 auto;
}

.logo-bg-circle {
  position: absolute;
  top: 10px;
  left: 10px;
  right: 10px;
  bottom: 10px;
  background: linear-gradient(135deg, #6b46c1, #805ad5);
  border-radius: 50%;
  opacity: 0.3;
  animation: pulse 2s infinite;
}

.form-header {
  text-align: center;
  margin-bottom: 30px;
}

h1 {
  color: #2d3748;
  font-size: 2rem;
  margin-bottom: 10px;
}

.subtitle {
  color: #718096;
  font-size: 1.1rem;
}

.form-group {
  margin-bottom: 25px;
}

.password-label {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
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
  outline: none;
  box-shadow: 0 0 0 3px rgba(107, 70, 193, 0.1);
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

.form-options {
  margin-bottom: 20px;
}

.checkbox-label {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
}

.custom-checkbox {
  width: 18px;
  height: 18px;
  border: 2px solid #e2e8f0;
  border-radius: 4px;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease;
}

input[type="checkbox"] {
  display: none;
}

input[type="checkbox"]:checked + .custom-checkbox {
  background-color: #6b46c1;
  border-color: #6b46c1;
}

input[type="checkbox"]:checked + .custom-checkbox i {
  color: white;
  font-size: 12px;
}

.btn-primary, .btn-secondary {
  width: 100%;
  padding: 12px;
  border-radius: 10px;
  font-size: 1rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 8px;
  position: relative;
  overflow: hidden;
}

.btn-primary {
  background: linear-gradient(to right, #6b46c1, #805ad5);
  color: white;
  border: none;
}

.btn-primary:hover {
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(107, 70, 193, 0.2);
}

.btn-secondary {
  background: white;
  color: #6b46c1;
  border: 2px solid #6b46c1;
  margin-top: 10px;
}

.btn-secondary:hover {
  background: #f7fafc;
}

.divider {
  text-align: center;
  position: relative;
  margin: 20px 0;
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

/* Social Buttons */
.social-buttons {
  display: flex;
  justify-content: center;
  gap: 15px;
  margin: 20px 0;
}

.social-btn {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  border: none;
  background: #f7fafc;
  color: #4a5568;
  font-size: 1.2rem;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
}

.social-btn:hover {
  transform: translateY(-2px);
}

.social-btn.google:hover {
  background: #DB4437;
  color: white;
}

.social-btn.facebook:hover {
  background: #4267B2;
  color: white;
}

.social-btn.twitter:hover {
  background: #1DA1F2;
  color: white;
}

/* Error Messages */
.error-message {
  color: #ff4444;
  font-size: 0.875rem;
  margin-top: 5px;
  display: flex;
  align-items: center;
  gap: 4px;
}

/* Modal Styles */
.modal {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal-content {
  background: white;
  padding: 30px;
  border-radius: 15px;
  max-width: 400px;
  width: 90%;
  text-align: center;
}

.modal-icon {
  font-size: 3rem;
  margin-bottom: 20px;
}

.modal-icon.error {
  color: #f56565;
}

/* Loading Spinner */
.spinner {
  width: 20px;
  height: 20px;
  border: 2px solid white;
  border-top-color: transparent;
  border-radius: 50%;
  animation: spin 0.8s linear infinite;
}

/* Toggle Password Button */
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

/* Animations */
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

@keyframes float {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-20px);
  }
}

@keyframes rotate {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}

@keyframes pulse {
  0% {
    transform: scale(1);
    opacity: 0.3;
  }
  50% {
    transform: scale(1.1);
    opacity: 0.1;
  }
  100% {
    transform: scale(1);
    opacity: 0.3;
  }
}

@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}

/* Button Glow Effect */
.btn-content {
  position: relative;
  z-index: 1;
}

.btn-glow {
  position: absolute;
  top: -50%;
  left: -50%;
  width: 200%;
  height: 200%;
  background: radial-gradient(circle, rgba(255,255,255,0.3) 0%, rgba(255,255,255,0) 70%);
  transform: scale(0);
  transition: transform 0.5s ease;
}

.btn-primary:hover .btn-glow {
  transform: scale(1);
}

/* Responsive Design */
@media (max-width: 768px) {
  .auth-card {
    padding: 20px;
  }

  .decorative-dots {
    display: none;
  }

  h1 {
    font-size: 1.5rem;
  }

  .subtitle {
    font-size: 1rem;
  }

  .social-buttons {
    gap: 10px;
  }

  .social-btn {
    width: 35px;
    height: 35px;
    font-size: 1rem;
  }
}

@media (max-width: 480px) {
  .auth-card {
    padding: 15px;
  }

  .logo {
    width: 60px;
    height: 60px;
    font-size: 2rem;
  }

  .form-group {
    margin-bottom: 20px;
  }

  input {
    padding: 10px 14px;
    font-size: 0.9rem;
  }
}
</style>