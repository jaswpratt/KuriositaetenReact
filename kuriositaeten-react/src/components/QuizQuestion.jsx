import { useState } from 'react'

function shuffle(array) {
  return [...array].sort(() => Math.random() - 0.5)
}

function QuizQuestion({ q, index, onAnswered }) {
  const [options] = useState(() => shuffle([q.correctAnswer, ...q.incorrectAnswers]))
  const [selected, setSelected] = useState(null)

  function handleSelect(option) {
    if (selected) return // already answered, lock it in
    setSelected(option)
    onAnswered(option === q.correctAnswer)
  }

  return (
    <div style={{ marginBottom: '1.5rem' }}>
      <p><strong>{index + 1}. {q.question}</strong></p>
      <p style={{ fontSize: '0.85rem', color: '#666' }}>
        {q.category} — {q.difficulty} — {q.type}
      </p>
      <ul style={{ listStyle: 'none', padding: 0 }}>
        {options.map((opt, i) => {
          let style = { cursor: selected ? 'default' : 'pointer', padding: '4px 8px' }
          if (selected) {
            if (opt === q.correctAnswer) style.color = 'green'
            else if (opt === selected) style.color = 'red'
          }
          return (
            <li key={i} onClick={() => handleSelect(opt)} style={style}>
              {opt}
              {selected && opt === q.correctAnswer && ' ✓'}
              {selected && opt === selected && opt !== q.correctAnswer && ' ✗'}
            </li>
          )
        })}
      </ul>
    </div>
  )
}

export default QuizQuestion