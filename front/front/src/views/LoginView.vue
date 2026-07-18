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
    message.value = '正在驗證身份...'
    
    // 呼叫後端 Spring Boot 登入接口 test-login
    const response = await axios.get('http://localhost:8050/test-login', {
      params: {
        phone: phoneNumber.value,
        password: password.value
      }
    })
    
    message.value = '後端回應：' + response.data
    
    // 如果後端回傳包含「成功」，代表登入順利
    if (response.data.includes('成功')) {
      message.value = '🎉 登入成功！正在前往圖書大廳...'
      // 未來我們建好圖書大廳後，就可以用這行跳轉：
      // setTimeout(() => { router.push('/books') }, 1500)
    }
  } catch (error) {
    console.error(error)
    message.value = '❌ 連線後端失敗，請確保後端有啟動！'
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