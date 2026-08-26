import { useState } from 'react'

const DIFFICULTIES = [
  { value: '', label: 'Any' },
  { value: 'easy', label: 'Easy' },
  { value: 'medium', label: 'Medium' },
  { value: 'hard', label: 'Hard' }
]
const TYPES = [
  { value: '', label: 'Any' },
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
    <form onSubmit={handleSubmit} className="quiz-controls">
      <div className="control-row">
        <label className="control-group">
          Number of questions:
          <input
            type="number"
            min="1"
            max="50"
            value={count}
            onChange={(e) => setCount(Number(e.target.value))}
          />
        </label>

        <label className="control-group">
          Difficulty:
          <select value={difficulty} onChange={(e) => setDifficulty(e.target.value)}>
            {DIFFICULTIES.map((d) => (
              <option key={d.value} value={d.value}>{d.label}</option>
            ))}
          </select>
        </label>
      </div>

      <div className="control-row">
        <label className="control-group">
          Type:
          <select value={type} onChange={(e) => setType(e.target.value)}>
            {TYPES.map((t) => (
              <option key={t.value} value={t.value}>{t.label}</option>
            ))}
          </select>
        </label>

        <button type="submit" className="start-quiz-btn">Start Quiz</button>
      </div>
    </form>
  )
}

export default QuizControls