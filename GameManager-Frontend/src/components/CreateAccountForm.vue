<template>
  <div class="create-account-container">
    <h2>Create Account</h2>
    <form @submit.prevent="handleSubmit" class="account-form">
      <div class="form-group">
        <label for="email">Email Address*</label>
        <input
            type="email"
            id="email"
            v-model="formData.email"
            :class="{ 'error': errors.email }"
            @input="validateEmail"
        />
        <span class="error-message" v-if="errors.email">{{ errors.email }}</span>
      </div>

      <div class="form-group">
        <label for="password">Password*</label>
        <input
            type="password"
            id="password"
            v-model="formData.password"
            :class="{ 'error': errors.password }"
            @input="validatePassword"
        />
        <div class="password-strength" v-if="formData.password">
          <div>Password Strength:</div>
          <div :class="passwordStrengthClass">{{ passwordStrength }}</div>
        </div>
        <span class="error-message" v-if="errors.password">{{ errors.password }}</span>
      </div>

      <div class="form-group">
        <label for="username">Username*</label>
        <input
            type="text"
            id="username"
            v-model="formData.username"
            :class="{ 'error': errors.username }"
        />
        <span class="error-message" v-if="errors.username">{{ errors.username }}</span>
      </div>

      <div class="form-group">
        <label for="address">Delivery Address*</label>
        <textarea
            id="address"
            v-model="formData.address"
            :class="{ 'error': errors.address }"
        ></textarea>
        <span class="error-message" v-if="errors.address">{{ errors.address }}</span>
      </div>

      <div class="form-group">
        <label for="postcode">Postcode*</label>
        <input
            type="text"
            id="postcode"
            v-model="formData.postcode"
            :class="{ 'error': errors.postcode }"
        />
        <span class="error-message" v-if="errors.postcode">{{ errors.postcode }}</span>
      </div>

      <div class="form-group">
        <label for="phone">Phone Number*</label>
        <input
            type="tel"
            id="phone"
            v-model="formData.phone"
            :class="{ 'error': errors.phone }"
        />
        <span class="error-message" v-if="errors.phone">{{ errors.phone }}</span>
      </div>

      <div class="form-group">
        <label for="age">Age*</label>
        <input
            type="number"
            id="age"
            v-model.number="formData.age"
            :class="{ 'error': errors.age }"
            min="0"
            max="120"
        />
        <span class="error-message" v-if="errors.age">{{ errors.age }}</span>
      </div>

      <div class="form-group terms">
        <label class="checkbox-label">
          <input
              type="checkbox"
              v-model="formData.termsAccepted"
              :class="{ 'error': errors.termsAccepted }"
          />
          I accept the <a href="#" @click.prevent="showTerms">Terms and Conditions</a> and
          <a href="#" @click.prevent="showPrivacyPolicy">Privacy Policy</a>
        </label>
        <span class="error-message" v-if="errors.termsAccepted">{{ errors.termsAccepted }}</span>
      </div>

      <button type="submit" :disabled="isSubmitting">
        {{ isSubmitting ? 'Creating Account...' : 'Create Account' }}
      </button>
    </form>

    <!-- Verification Email Modal -->
    <div v-if="showVerificationModal" class="modal">
      <div class="modal-content">
        <h3>Verify Your Email</h3>
        <p>We've sent a verification email to {{ formData.email }}</p>
        <p>Didn't receive the email?</p>
        <button @click="resendVerificationEmail" :disabled="isResending">
          {{ isResending ? 'Sending...' : 'Resend Verification Email' }}
        </button>
        <button @click="closeVerificationModal">Close</button>
      </div>
    </div>

    <!-- Error Modal -->
    <div v-if="showErrorModal" class="modal">
      <div class="modal-content">
        <h3>Error</h3>
        <p>{{ errorMessage }}</p>
        <button @click="closeErrorModal">Close</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'CreateAccountForm',
  data() {
    return {
      formData: {
        email: '',
        password: '',
        username: '',
        address: '',
        postcode: '',
        phone: '',
        age: null,
        termsAccepted: false
      },
      errors: {},
      isSubmitting: false,
      isResending: false,
      showVerificationModal: false,
      showErrorModal: false,
      errorMessage: '',
      passwordStrength: '',
      passwordStrengthClass: ''
    }
  },
  methods: {
    validateEmail() {
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
      if (!this.formData.email) {
        this.errors.email = 'Email is required'
      } else if (!emailRegex.test(this.formData.email)) {
        this.errors.email = 'Please enter a valid email address'
      } else {
        delete this.errors.email
      }
    },

    validatePassword() {
      const password = this.formData.password
      if (!password) {
        this.errors.password = 'Password is required'
        return
      }

      const hasUpperCase = /[A-Z]/.test(password)
      const hasLowerCase = /[a-z]/.test(password)
      const hasNumbers = /\d/.test(password)
      const hasSpecialChars = /[!@#$%^&*(),.?":{}|<>]/.test(password)

      let strength = 0
      if (password.length >= 8) strength++
      if (hasUpperCase && hasLowerCase) strength++
      if (hasNumbers) strength++
      if (hasSpecialChars) strength++

      switch(strength) {
        case 0:
        case 1:
          this.passwordStrength = 'Weak'
          this.passwordStrengthClass = 'weak'
          this.errors.password = 'Password is too weak'
          break
        case 2:
          this.passwordStrength = 'Medium'
          this.passwordStrengthClass = 'medium'
          delete this.errors.password
          break
        case 3:
          this.passwordStrength = 'Strong'
          this.passwordStrengthClass = 'strong'
          delete this.errors.password
          break
        case 4:
          this.passwordStrength = 'Very Strong'
          this.passwordStrengthClass = 'very-strong'
          delete this.errors.password
          break
      }
    },

    validateForm() {
      this.errors = {}

      // Validate all fields
      this.validateEmail()
      this.validatePassword()

      if (!this.formData.username) {
        this.errors.username = 'Username is required'
      }

      if (!this.formData.address) {
        this.errors.address = 'Delivery address is required'
      }

      if (!this.formData.postcode) {
        this.errors.postcode = 'Postcode is required'
      }

      if (!this.formData.phone) {
        this.errors.phone = 'Phone number is required'
      }

      if (!this.formData.age) {
        this.errors.age = 'Age is required'
      } else if (this.formData.age < 13) {
        this.errors.age = 'You must be at least 13 years old'
      }

      if (!this.formData.termsAccepted) {
        this.errors.termsAccepted = 'You must accept the Terms and Conditions'
      }

      return Object.keys(this.errors).length === 0
    },

    async handleSubmit() {
      if (!this.validateForm()) {
        return
      }

      this.isSubmitting = true

      try {
        // Check if email exists
        const emailExists = await this.checkEmailExists(this.formData.email)
        if (emailExists) {
          this.showError('This email is already registered. Please use a different email or reset your password.')
          return
        }

        // Submit registration
        const response = await this.submitRegistration(this.formData)

        if (response.success) {
          this.showVerificationModal = true
        } else {
          this.showError('Registration failed. Please try again.')
        }
      } catch (error) {
        this.showError('An unexpected error occurred. Please try again later.')
        console.error('Registration error:', error)
      } finally {
        this.isSubmitting = false
      }
    },

    async checkEmailExists(email) {
      // Implement API call to check email existence
      return false // Placeholder
    },

    async submitRegistration(formData) {
      // Implement API call to submit registration
      return { success: true } // Placeholder
    },

    async resendVerificationEmail() {
      this.isResending = true
      try {
        // Implement resend verification email logic
        await new Promise(resolve => setTimeout(resolve, 1000)) // Simulated delay
        // Show success message
      } catch (error) {
        this.showError('Failed to resend verification email. Please try again.')
      } finally {
        this.isResending = false
      }
    },

    showError(message) {
      this.errorMessage = message
      this.showErrorModal = true
    },

    closeErrorModal() {
      this.showErrorModal = false
      this.errorMessage = ''
    },

    closeVerificationModal() {
      this.showVerificationModal = false
      // Redirect to login page or show success message
    },

    showTerms() {
      // Implement terms and conditions display logic
    },

    showPrivacyPolicy() {
      // Implement privacy policy display logic
    }
  }
}
</script>

<style scoped>
.create-account-container {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
}

.account-form {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

label {
  font-weight: bold;
}

input, textarea {
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

input.error, textarea.error {
  border-color: #ff4444;
}

.error-message {
  color: #ff4444;
  font-size: 0.875rem;
}

.terms {
  display: flex;
  align-items: flex-start;
  gap: 8px;
}

.checkbox-label {
  display: flex;
  align-items: flex-start;
  gap: 8px;
}

button {
  padding: 12px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 4px;
  max-width: 400px;
  width: 100%;
}

.password-strength {
  font-size: 0.875rem;
}

.weak { color: #ff4444; }
.medium { color: #ffa700; }
.strong { color: #2ecc71; }
.very-strong { color: #27ae60; }
</style>