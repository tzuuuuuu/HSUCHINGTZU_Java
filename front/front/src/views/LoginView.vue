<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

const router = useRouter()

const phoneNumber = ref('')
const password = ref('')
const message = ref('')

// 發送登入請求
const handleLogin = async () => {
  if (!phoneNumber.value || !password.value) {
    message.value = '請輸入手機號碼與密碼！'
    return
  }

  try {
    message.value = '正在連線中央資料庫...'
    
    // 🎯 1. 改用 axios.post 發送請求
    // 🎯 2. 網址改成後端剛寫好的正式路徑 /api/users/login
    const response = await axios.post('http://localhost:8050/api/users/login', {
      // 🎯 3. 不用 params 包裹，直接把資料倒進去，欄位名稱要精準對齊 User.java
      phoneNumber: phoneNumber.value,
      password: password.value
    })
    
    // 顯示後端回傳的真實訊息（包含「歡迎回來，XXX」）
    message.value = response.data;
    
    // 如果後端回應包含「成功」，代表登入成功
    // 🎯 修正接收方式：因為後端改回傳物件，改用 response.data.status 判斷
    if (response.data.status === 'success') {
      // 完美的把後端傳來的真實 userId 刻進瀏覽器裡！User 3 也能完美自證身份！
      localStorage.setItem('currentUserId', response.data.userId);
      localStorage.setItem('welcomeMessage', response.data.message);
      
      message.value = response.data.message;
      
      setTimeout(() => {
        router.push('/books')
      }, 1200)
    }
  } catch (error) {
    // 處理密碼錯誤的 401 狀態
    if (error.response && error.response.data) {
      message.value = error.response.data.message;
    } else {
      message.value = '伺服器連線失敗！';
    }
  }
}

</script>

<template>
  <div class="login-container">
    <h2>📚 圖書管理系統 - 會員登入</h2>

    <div class="form-group">
      <label>手機號碼：</label>
      <input v-model="phoneNumber" type="text" placeholder="請輸入註冊的手機號碼" />
    </div>

    <div class="form-group">
      <label>密碼：</label>
      <input v-model="password" type="password" placeholder="請輸入密碼" />
    </div>

    <button @click="handleLogin">登入</button>

    <p v-if="message" class="msg">{{ message }}</p>

    <hr />
    <router-link to="/register">還沒有帳號？前往註冊</router-link>
  </div>
</template>

<style scoped>
.login-container {
  max-width: 400px;
  margin: 50px auto;
  padding: 30px;
  border: 1px solid #ccc;
  border-radius: 8px;
  font-family: sans-serif;
}
.form-group {
  margin-bottom: 15px;
}
label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}
input {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
  border: 1px solid #ccc;
  border-radius: 4px;
}
button {
  width: 100%;
  padding: 10px;
  background-color: #35495e;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
}
button:hover {
  background-color: #42b983;
}
.msg {
  margin-top: 15px;
  color: #d9534f;
  font-weight: bold;
}
</style>