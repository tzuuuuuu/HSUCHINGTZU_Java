<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

const router = useRouter()

// 綁定表單的變數
const userName = ref('')
const phoneNumber = ref('')
const password = ref('')
const message = ref('')

// 發送註冊請求到後端
const handleRegister = async () => {
  if (!userName.value || !phoneNumber.value || !password.value) {
    message.value = '請填寫所有欄位！'
    return
  }

  try {
    message.value = '正在連接後端資料庫...'
    
    const response = await axios.get('http://localhost:8050/test-register', {
      params: {
        name: userName.value,
        phone: phoneNumber.value,
        password: password.value
      }
    })
    
    // 🔍 聰明判斷：如果後端回應包含「失敗」二字，就不要慶祝了！
    if (response.data.includes('失敗')) {
      message.value = '❌ ' + response.data
    } else {
      message.value = '🎉 註冊成功！後端回應：' + response.data
      // 真正成功才幫他跳轉
      setTimeout(() => {
        router.push('/')
      }, 2000)
    }

  } catch (error) {
    console.error(error)
    message.value = '❌ 連線後端失敗！請確保後端有啟動且 CORS 已開通！'
  }
}
</script>

<template>
  <div class="register-container">
    <h2>📚 圖書管理系統 - 會員註冊</h2>
    
    <div class="form-group">
      <label>使用者名稱：</label>
      <input v-model="userName" type="text" placeholder="請輸入姓名" />
    </div>

    <div class="form-group">
      <label>手機號碼：</label>
      <!-- 改為 text 型態，完美對應我們的 String 欄位 -->
      <input v-model="phoneNumber" type="text" placeholder="請輸入手機號碼 (如 0912345678)" />
    </div>

    <div class="form-group">
      <label>密碼：</label>
      <input v-model="password" type="password" placeholder="請輸入密碼" />
    </div>

    <button @click="handleRegister">確認註冊</button>

    <p v-if="message" class="msg">{{ message }}</p>

    <hr />
    <router-link to="/">已有帳號？前往登入</router-link>
  </div>
</template>

<style scoped>
.register-container {
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
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
}
button:hover {
  background-color: #35495e;
}
.msg {
  margin-top: 15px;
  color: #d9534f;
  font-weight: bold;
}
</style>