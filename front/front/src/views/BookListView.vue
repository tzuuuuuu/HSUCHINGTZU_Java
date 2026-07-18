<template>
  <div class="book-container">
    <h2>📚 圖書借閱大廳</h2>
    <p class="welcome-msg">歡迎回來！請選擇您想借閱的書籍。</p>
    
    <!-- 書單表格 -->
    <table class="book-table">
      <thead>
        <tr>
          <th>ISBN</th>
          <th>書名</th>
          <th>作者</th>
          <th>導讀簡介</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="book in books" :key="book.isbn">
          <td>{{ book.isbn }}</td>
          <td class="book-name">{{ book.name }}</td>
          <td>{{ book.author }}</td>
          <td class="book-intro">{{ book.introduction }}</td>
        </tr>
        <tr v-if="books.length === 0">
          <td colspan="4" class="no-data">目前館內沒有藏書或資料加載中...</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const books = ref([])

// 頁面一打開，立刻去後端撈取真實書單
onMounted(async () => {
  try {
    const response = await axios.get('http://localhost:8050/books')
    books.value = response.data
  } catch (error) {
    console.error('撈取書單失敗：', error)
    alert('無法連線至圖書伺服器！')
  }
})
</script>

<style scoped>
.book-container {
  max-width: 1000px;
  margin: 40px auto;
  padding: 20px;
  font-family: Arial, sans-serif;
}
h2 {
  color: #333;
  border-bottom: 2px solid #67c23a;
  padding-bottom: 10px;
}
.welcome-msg {
  color: #666;
  margin-bottom: 20px;
}
.book-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 10px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}
th, td {
  padding: 12px 15px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}
th {
  background-color: #67c23a;
  color: white;
}
tr:hover {
  background-color: #f5f5f5;
}
.book-name {
  font-weight: bold;
  color: #2c3e50;
}
.book-intro {
  color: #555;
  font-size: 0.9em;
  max-width: 400px;
}
.no-data {
  text-align: center;
  color: #999;
  padding: 30px;
}
</style>