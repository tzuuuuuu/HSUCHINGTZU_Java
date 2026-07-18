<template>
  <div class="book-container">
    <h2>📚 圖書借閱大廳</h2>
    <!-- 為了測試方便，我們這裡先寫死目前登入的使用者為 User_id = 2 (即 test 帳號) -->
    <p class="welcome-msg">歡迎回來！目前登入代號：User 2 👤</p>
    
    <table class="book-table">
      <thead>
        <tr>
          <th>庫存編號</th>
          <th>書名</th>
          <th>作者</th>
          <th>目前狀態</th>
          <th>操作選項</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in inventoryList" :key="item.inventoryId">
          <td>{{ item.inventoryId }}</td>
          <!-- 透過 item.book 取得關聯的書籍欄位 -->
          <td class="book-name">{{ item.book?.name || '未知書名' }}</td>
          <td>{{ item.book?.author || '未知作者' }}</td>
          <td>
            <span :class="['status-badge', item.status === '在庫' ? 'status-in' : 'status-out']">
              {{ item.status }}
            </span>
          </td>
          <td>
            <!-- 借書按鈕：只有在「在庫」時可以按 -->
            <button 
              v-if="item.status === '在庫'" 
              @click="handleBorrow(item.inventoryId)" 
              class="btn btn-borrow"
            >
              點我借閱
            </button>
            
            <!-- 還書按鈕：只有在「出借中」時可以按 -->
            <button 
              v-else-if="item.status === '出借中'" 
              @click="handleReturn(item.inventoryId)" 
              class="btn btn-return"
            >
              歸還書籍
            </button>
            
            <span v-else class="text-muted">不可操作</span>
          </td>
        </tr>
        <tr v-if="inventoryList.length === 0">
          <td colspan="5" class="no-data">系統無庫存資料，請確認資料庫 inventory 表。</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const inventoryList = ref([])
const currentUserId = 2 // 預設當前操作者為 test 用戶

// 撈取庫存明細
const fetchInventory = async () => {
  try {
    const response = await axios.get('http://localhost:8050/api/books/inventory')
    inventoryList.value = response.data
  } catch (error) {
    console.error('撈取庫存失敗：', error)
  }
}

// 執行借書
const handleBorrow = async (inventoryId) => {
  try {
    const response = await axios.post(`http://localhost:8050/api/books/borrow?userId=${currentUserId}&inventoryId=${inventoryId}`)
    alert(response.data)
    fetchInventory() // 重新整理網頁狀態
  } catch (error) {
    alert('借書連線失敗！')
  }
}

// 執行還書
const handleReturn = async (inventoryId) => {
  try {
    const response = await axios.post(`http://localhost:8050/api/books/return?inventoryId=${inventoryId}`)
    alert(response.data)
    fetchInventory() // 重新整理網頁狀態
  } catch (error) {
    alert('還書連線失敗！')
  }
}

onMounted(() => {
  fetchInventory()
})
</script>

<style scoped>
.book-container { max-width: 1000px; margin: 40px auto; padding: 20px; font-family: sans-serif; }
h2 { color: #333; border-bottom: 2px solid #409eff; padding-bottom: 10px; }
.welcome-msg { color: #666; margin-bottom: 20px; }
.book-table { width: 100%; border-collapse: collapse; box-shadow: 0 2px 8px rgba(0,0,0,0.1); }
th, td { padding: 12px 15px; text-align: left; border-bottom: 1px solid #ddd; }
th { background-color: #409eff; color: white; }
tr:hover { background-color: #f5f5f5; }
.book-name { font-weight: bold; }
.status-badge { padding: 4px 8px; border-radius: 4px; font-size: 0.85em; font-weight: bold; }
.status-in { background-color: #e1f3d8; color: #67c23a; }
.status-out { background-color: #fef0f0; color: #f56c6c; }
.btn { padding: 6px 12px; border: none; border-radius: 4px; cursor: pointer; font-weight: bold; }
.btn-borrow { background-color: #67c23a; color: white; }
.btn-borrow:hover { background-color: #5daf34; }
.btn-return { background-color: #e6a23c; color: white; }
.btn-return:hover { background-color: #cf9236; }
.no-data { text-align: center; color: #999; padding: 30px; }
.text-muted { color: #999; }
</style>