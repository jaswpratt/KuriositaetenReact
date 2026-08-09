import { useState } from 'react'

const DIFFICULTIES = ['easy', 'medium', 'hard']
const TYPES = [
  { value: 'multiple', label: 'Multiple Choice' },
  { value: 'boolean', label: 'True / False' }
]

function QuizControls({ category, onStart }) {
  const [count, setCount] = useState(10)
  const [difficulty, setDifficulty] = useState('easy')
  const [type, setType] = useState('multiple')

  function handleSubmit(e) {
    e.preventDefault()
    if (!category) {
      alert('Please select a category first')
      return
    }
    onStart({ category, count, difficulty, type })
  }

  return (
    <form onSubmit={handleSubmit}>
      <label>
        Number of questions:
        <input
          type="number"
          min="1"
          max="50"
          value={count}
          onChange={(e) => setCount(Number(e.target.value))}
        />
      </label>

      <label>
        Difficulty:
        <select value={difficulty} onChange={(e) => setDifficulty(e.target.value)}>
          {DIFFICULTIES.map((d) => (
            <option key={d} value={d}>{d}</option>
          ))}
        </select>
      </label>

      <label>
        Type:
        <select value={type} onChange={(e) => setType(e.target.value)}>
          {TYPES.map((t) => (
            <option key={t.value} value={t.value}>{t.label}</option>
          ))}
        </select>
      </label>

      <button type="submit">Start Quiz</button>
    </form>
  )
}

export default QuizControls