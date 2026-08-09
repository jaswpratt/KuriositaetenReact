import { useState, useEffect } from 'react'
import { fetchCategories } from '../api/triviaApi'

function CategoryPicker({ onCategoryChange }) {
  const [categories, setCategories] = useState([])
  const [selectedCategory, setSelectedCategory] = useState('')
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    fetchCategories()
      .then((data) => {
        setCategories(data)
        setLoading(false)
      })
      .catch((err) => {
        setError(err.message)
        setLoading(false)
      })
  }, [])

  function handleChange(e) {
    const value = e.target.value
    setSelectedCategory(value)
    if (onCategoryChange) onCategoryChange(value)
  }

  if (loading) return <p>Loading categories...</p>
  if (error) return <p>Error loading categories: {error}</p>

  return (
    <select value={selectedCategory} onChange={handleChange}>
      <option value="">Select a category</option>
      {categories.map((cat) => (
        <option key={cat.id} value={cat.id}>
          {cat.category}
        </option>
      ))}
    </select>
  )
}

export default CategoryPicker